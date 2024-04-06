package com.example.roomjetpack.sections

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.roomjetpack.entities.Contacts
import com.example.roomjetpack.states.ContactEvent
import com.example.roomjetpack.states.ContactState
import com.example.roomjetpack.ui.theme.selectAlpha

@Composable
fun ContactDialog(
    modifier: Modifier = Modifier,
    state: ContactState,
    onEvent: (ContactEvent) -> Unit
) {

    AlertDialog(modifier = modifier.fillMaxWidth(), onDismissRequest = {
        onEvent(ContactEvent.HideDialog)
    },
        title = {
            Text(
                text = "Add Contact",
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = state.firstName.trim(),
                    onValueChange = { onEvent(ContactEvent.SetFirstName(it)) },
                    label = {
                        Text(text = "First Name")
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
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
                )

                OutlinedTextField(
                    value = state.lastName,
                    onValueChange = { onEvent(ContactEvent.SetLastName(it)) },
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
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
                )

                OutlinedTextField(
                    value = state.phoneNumber.trim(),
                    onValueChange = { onEvent(ContactEvent.SetPhoneNumber(it)) },
                    label = {
                        Text(text = "Phone Number")
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
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Phone
                    )
                )

                var hasAddedPic by remember {
                    mutableStateOf(false)
                }

                val photoPicker =
                    rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()) { uri ->
                        if (uri != null) {
                            onEvent(ContactEvent.SetContactImage(uri.toString()))
                            hasAddedPic = true
                        }
                    }
                Box(
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colors.onBackground,
                            shape = CircleShape
                        )
                        .clickable {
                            photoPicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                        }, contentAlignment = Alignment.Center) {

                    Column(horizontalAlignment = Alignment.CenterHorizontally,modifier = Modifier.padding(5.dp)) {

                        if (!hasAddedPic) {
                            Text(
                                text = "Add Contact Image",
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colors.onBackground,
                                modifier = Modifier.padding(horizontal = 10.dp)
                            )
                            Icon(
                                imageVector = Icons.Default.AddCircle,
                                contentDescription = null,
                                modifier = Modifier.size(30.dp),
                                tint = MaterialTheme.colors.onBackground
                            )
                        } else {
                            Text(
                                text = "Added",
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colors.onBackground,
                                modifier = Modifier.padding(horizontal = 10.dp)
                            )
                        }
                    }
                }
            }
        },
        buttons = {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                OutlinedButton(
                    modifier = Modifier.fillMaxWidth(0.5f),
                    onClick = {
                        onEvent(ContactEvent.SaveContact)
                    },
                    colors = ButtonDefaults.buttonColors(
                        contentColor = MaterialTheme.colors.onBackground,
                        backgroundColor = Color.Transparent
                    ),
                    border = BorderStroke(
                        width = 2.dp,
                        color = selectAlpha(alpha = ContentAlpha.high)
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(text = "Save", fontWeight = FontWeight.SemiBold)
                }
            }
        }
    )
}

@Composable
fun ConfirmDeletion(
    modifier: Modifier = Modifier,
    showDialog: Boolean,
    onEvent: (ContactEvent) -> Unit,
    contact: Contacts,
    onDismiss: () -> Unit
) {
    if (showDialog) {
        // Content of the dialog
        Column(
            modifier = modifier
                .wrapContentWidth()
                .wrapContentHeight()
                .padding(16.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Are you sure",
                style = MaterialTheme.typography.h5.copy(color = MaterialTheme.colors.onBackground),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedButton(
                onClick = {
                    onEvent(ContactEvent.DeleteContact(contact))
                    onDismiss()
                }, colors = ButtonDefaults.buttonColors(
                    contentColor = Color.Red,
                    backgroundColor = Color.Transparent
                ),
                border = BorderStroke(
                    width = 2.dp,
                    color = selectAlpha(alpha = ContentAlpha.high)
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Delete", color = Color.Red)
            }
            Spacer(modifier = Modifier.height(5.dp))
            OutlinedButton(
                onClick = onDismiss, colors = ButtonDefaults.buttonColors(
                    contentColor = selectAlpha(alpha = ContentAlpha.high),
                    backgroundColor = Color.Transparent
                ),
                border = BorderStroke(
                    width = 2.dp,
                    color = MaterialTheme.colors.onBackground
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Cancel", color = MaterialTheme.colors.onBackground)
            }
        }
    }
}




