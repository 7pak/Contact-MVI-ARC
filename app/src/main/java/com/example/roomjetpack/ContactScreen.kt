package com.example.roomjetpack

import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.example.roomjetpack.entities.Contacts
import com.example.roomjetpack.navgraph.CONTACT_INFO
import com.example.roomjetpack.sections.ConfirmDeletion
import com.example.roomjetpack.sections.ContactDialog
import com.example.roomjetpack.sections.ContactsList
import com.example.roomjetpack.sections.SortType
import com.example.roomjetpack.states.ContactEvent
import com.example.roomjetpack.states.ContactState

@Composable
fun ContentScreen(
    state: ContactState,
    onEvent: (ContactEvent) -> Unit,
    navController: NavHostController,
    onCurrentContact:(Contacts)->Unit
) {
    var showDeleteDialog by remember {
        mutableStateOf(false)
    }
    var currentContact by remember {
        mutableStateOf(Contacts("", "", "", null))
    }


    val onBackPressedCallback = remember {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                showDeleteDialog = false
            }
        }
    }
    val dispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    DisposableEffect(Unit) {
        onBackPressedCallback.isEnabled = showDeleteDialog

        dispatcher?.addCallback(onBackPressedCallback)

        onDispose {
            onBackPressedCallback.remove()
        }
    }


    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEvent(ContactEvent.ShowDialog) },
                backgroundColor = MaterialTheme.colors.onBackground
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Contact",
                    tint = MaterialTheme.colors.background,
                )
            }
        },
        modifier = Modifier.background(color = MaterialTheme.colors.background)
    ) { paddingValues ->
        if (state.isAddingContact) {
            ContactDialog(modifier = Modifier.padding(15.dp), state = state, onEvent = onEvent)
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = paddingValues,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                SortType(state = state, onEvent = onEvent)
            }
            items(state.contacts) { contact ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(horizontal = 5.dp)
                        .clickable {
                            onCurrentContact(contact)
                            navController.navigate(CONTACT_INFO)
                        }
                ) {

                    val contactImage = contact.contactImage ?: if (isSystemInDarkTheme())R.drawable.ic_contact_night else R.drawable.ic_contact
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(contactImage)
                            .crossfade(true)
                            .transformations(CircleCropTransformation())
                            .build(),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(60.dp)
                            .clip(shape = CircleShape),
                        contentDescription = "co_image",

                    )

                    ContactsList(contact = contact, currentContact = {
                        currentContact = it
                    }) {
                        showDeleteDialog = true
                    }
                }
            }
        }


        if (showDeleteDialog) {
            BackHandler(enabled = showDeleteDialog) {
                showDeleteDialog = false
            }


            val interactionSource = remember { MutableInteractionSource() }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f))
                    .clickable(
                        onClick = { showDeleteDialog = false }, indication = null,
                        interactionSource = interactionSource
                    ),
                contentAlignment = Alignment.BottomCenter

            ) {
                ConfirmDeletion(
                    modifier = Modifier.padding(16.dp),
                    showDialog = showDeleteDialog,
                    onEvent = onEvent,
                    contact = currentContact
                ) {
                    showDeleteDialog = false
                }
            }
        }
    }
}






