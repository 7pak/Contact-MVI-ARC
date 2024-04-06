package com.example.roomjetpack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.rememberNavController
import com.example.roomjetpack.navgraph.NavGraph
import com.example.roomjetpack.ui.theme.RoomJetpackTheme
import com.example.roomjetpack.viewmodel.ContactViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    //Without Hilt
//    private val db by lazy {
//        Room.databaseBuilder(context = applicationContext,
//        ContactDataBase::class.java,"contact.db")
//            .build()
//    }
//
//    private val viewModel by  viewModels<ContactViewModel>(
//        factoryProducer = {
//            object : ViewModelProvider.Factory{
//                override fun <T : ViewModel> create(modelClass: Class<T>): T {
//                    return ContactViewModel(db.dao) as T
//                }
//            }
//        }
//    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: ContactViewModel by viewModels()

        setContent {
            val state by viewModel.state.collectAsState()
            val navController = rememberNavController()
            RoomJetpackTheme {
                NavGraph(navController =navController , state = state, onEvent =viewModel::onEvent )

                //ContentScreen(state = state, onEvent = viewModel::onEvent)
            }
        }
    }
}
