package com.example.roomjetpack.navgraph

import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.roomjetpack.ContentScreen
import com.example.roomjetpack.entities.Contacts
import com.example.roomjetpack.sections.ContactInfo
import com.example.roomjetpack.states.ContactEvent
import com.example.roomjetpack.states.ContactState

const val CONTACT_LIST = "contact_list"
const val CONTACT_INFO= "contact_info"

@Composable
fun NavGraph(navController: NavHostController,state:ContactState,onEvent:(ContactEvent)->Unit) {
    var currentContact by remember {
        mutableStateOf(Contacts("","","",null))
    }

    NavHost(navController = navController, startDestination = CONTACT_LIST){
        composable(route = CONTACT_LIST){
            ContentScreen(state = state, onEvent = onEvent,navController){
                currentContact  = it
            }
        }

        composable(route = CONTACT_INFO){
            //we were doing some thing called Screen class watch out the nav basics
            ContactInfo(navController = navController, contact =currentContact, onEvent = onEvent)
        }
    }
}