package com.example.roomjetpack.sections

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.roomjetpack.entities.Contacts

@Composable
fun ContactsList(
    contact: Contacts,
    currentContact: (Contacts) -> Unit,
    onDelete: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 10.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colors.onBackground,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(horizontal = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(5.dp), verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(
                text = "${contact.firstName} ${contact.lastName}",
                fontSize = MaterialTheme.typography.h6.fontSize,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = contact.phoneNumber,
                fontSize = 13.sp,
                fontWeight = FontWeight.Normal
            )
        }
        IconButton(onClick = {
            currentContact(contact)
            onDelete()
        }) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete contact"
            )
        }
    }
}



