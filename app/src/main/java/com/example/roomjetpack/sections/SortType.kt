package com.example.roomjetpack.sections

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.roomjetpack.states.ContactEvent
import com.example.roomjetpack.states.ContactState
import com.example.roomjetpack.states.SortType

@Composable
fun SortType(
    state:ContactState,
    onEvent:(ContactEvent)->Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(state = rememberScrollState())
            .padding(vertical = 10.dp, horizontal = 3.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(text = "Sort by")
        SortType.values().forEach { sortType ->
            OutlinedButton(
                onClick = {
                    onEvent(ContactEvent.SortContacts(sortType))
                }, colors = ButtonDefaults.outlinedButtonColors(
                    backgroundColor = Color.Transparent,
                    contentColor = LocalContentColor.current.copy(alpha = if (sortType == state.sortType) ContentAlpha.high else ContentAlpha.disabled)
                ),
                border = BorderStroke(
                    1.dp,
                    color = LocalContentColor.current.copy(alpha = if (sortType == state.sortType) ContentAlpha.high else ContentAlpha.disabled)
                ), modifier = Modifier
                    .size(width = 120.dp, height = 30.dp)
                    .padding(horizontal = 3.dp), contentPadding = PaddingValues(5.dp),
                shape = CircleShape

            ) {
                when (sortType) {
                    SortType.FIRST_NAME -> Text(
                        text = "First Name", fontSize = 13.sp
                    )
                    SortType.LAST_NAME -> Text(
                        text = "Surname", fontSize = 13.sp
                    )
                    SortType.PHONE_NUMBER -> Text(
                        text = "Phone Number", fontSize = 13.sp
                    )
                }
            }
//                        Row(modifier = Modifier.clickable {
//                        onEvent(ContactEvent.SortContacts(sortType))
//                        }, verticalAlignment = Alignment.CenterVertically) {
//                            RadioButton(
//                                selected = sortType == state.sortType,
//                                onClick = { onEvent(ContactEvent.SortContacts(sortType)) })
//                            Text(text = sortType.name)
//                        }
        }
    }
}