package com.example.roomjetpack.sections

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.example.roomjetpack.R
import com.example.roomjetpack.entities.Contacts
import com.example.roomjetpack.states.ContactEvent
import com.example.roomjetpack.ui.theme.selectAlpha

@Composable
fun ContactInfo(
    navController: NavHostController,
    contact: Contacts,
    onEvent: (ContactEvent) -> Unit,
) {

    var enableEditing by remember { mutableStateOf(false) }
    var editedFirstName by remember { mutableStateOf(contact.firstName) }
    var editedLastName by remember { mutableStateOf(contact.lastName) }
    var editedContact by remember {
        mutableStateOf(
            Contacts(
                firstName = contact.firstName,
                lastName = contact.lastName,
                phoneNumber = contact.phoneNumber,
                contactImage = contact.contactImage,
                id = contact.id
            )
        )
    }


    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .verticalScroll(rememberScrollState())
    ) {

        Text(
            text = "Contact Info & Editing",
            fontSize = MaterialTheme.typography.h4.fontSize,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.onBackground,
            modifier = Modifier
                .padding(horizontal = 5.dp, vertical = 5.dp)
                .align(CenterHorizontally)
        )

        val photoPicker =
            rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()) { uri ->
                if (uri != null) {
                    editedContact = editedContact.copy(contactImage = uri.toString())
                }
            }

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(
                    contact.contactImage
                        ?: if (isSystemInDarkTheme()) R.drawable.ic_contact_night else R.drawable.ic_contact
                )
                .crossfade(true)
                .transformations(CircleCropTransformation())
                .build(),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(300.dp)
                .clip(shape = CircleShape)
                .wrapContentHeight()
                .wrapContentWidth()
                .padding(15.dp)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colors.onBackground,
                    shape = CircleShape
                )
                .clickable {
                    if (enableEditing) {
                        photoPicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                    }
                },
            contentDescription = "contactInfos"
        )

        Spacer(modifier = Modifier.height(10.dp))


        OutlinedTextField(
            value = if (!enableEditing) "${editedFirstName.trim()} ${editedLastName}" else editedFirstName.trim(),
            onValueChange = {
                editedFirstName = it.trim()
                editedContact = editedContact.copy(firstName = it.trim())
            },
            label = {
                Text(text = "First Name")
            },
            enabled = enableEditing,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedLabelColor = selectAlpha(alpha = ContentAlpha.disabled),
                focusedLabelColor = selectAlpha(alpha = ContentAlpha.high),
                unfocusedBorderColor = selectAlpha(alpha = ContentAlpha.disabled),
                focusedBorderColor = selectAlpha(alpha = ContentAlpha.high),
                cursorColor = selectAlpha(alpha = ContentAlpha.high),
                backgroundColor = Color.Transparent,
                textColor = selectAlpha(alpha = ContentAlpha.high)
            ),
            maxLines = 1,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            modifier = Modifier
                .align(CenterHorizontally)
                .clickable {
                    enableEditing = true
                }
        )

        Spacer(modifier = Modifier.height(15.dp))


        //last name

        if (enableEditing) {
            OutlinedTextField(
                value = editedLastName,
                onValueChange = {
                    editedLastName = it
                    editedContact = editedContact.copy(lastName = it)
                },
                label = {
                    Text(text = "Last Name")
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedLabelColor = selectAlpha(alpha = ContentAlpha.disabled),
                    focusedLabelColor = selectAlpha(alpha = ContentAlpha.high),
                    unfocusedBorderColor = selectAlpha(alpha = ContentAlpha.disabled),
                    focusedBorderColor = selectAlpha(alpha = ContentAlpha.high),
                    cursorColor = selectAlpha(alpha = ContentAlpha.high),
                    backgroundColor = Color.Transparent,
                    textColor = selectAlpha(alpha = ContentAlpha.high)
                ),
                maxLines = 1,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                modifier = Modifier.align(CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(15.dp))
        }

        OutlinedTextField(
            value = editedContact.phoneNumber.trim(),  //just try it
            onValueChange = {
                editedContact = editedContact.copy(phoneNumber = it)
            },
            label = {
                Text(text = "Phone Number")
            },
            enabled = enableEditing,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedLabelColor = selectAlpha(alpha = ContentAlpha.disabled),
                focusedLabelColor = selectAlpha(alpha = ContentAlpha.high),
                unfocusedBorderColor = selectAlpha(alpha = ContentAlpha.disabled),
                focusedBorderColor = selectAlpha(alpha = ContentAlpha.high),
                cursorColor = selectAlpha(alpha = ContentAlpha.high),
                backgroundColor = Color.Transparent,
                textColor = selectAlpha(alpha = ContentAlpha.high)
            ),
            maxLines = 1,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            modifier = Modifier
                .align(CenterHorizontally)
                .clickable {
                    enableEditing = true
                }
        )
        if (enableEditing) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = MaterialTheme.colors.onBackground,
                    modifier = Modifier
                        .size(50.dp)
                        .clickable {
                            onEvent(ContactEvent.UpdateContact(editedContact))
                            enableEditing = false
                            navController.popBackStack()
                        })

                Spacer(modifier = Modifier.width(10.dp))

                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    tint = MaterialTheme.colors.onBackground,
                    modifier = Modifier
                        .size(50.dp)
                        .clickable {
                            navController.popBackStack()
                        })
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun ContactInfoPreview() {
    ContactInfo(
        navController = rememberNavController(),
        contact = Contacts("firstName", "lastName", "+90465465465", null)
    ) {

    }

}