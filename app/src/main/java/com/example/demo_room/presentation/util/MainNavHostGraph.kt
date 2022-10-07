package com.example.demo_room.presentation.util

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.demo_room.presentation.feature_note.add_edit_note.AddEditNoteScreen
import com.example.demo_room.presentation.feature_note.notes.NotesScreen
import com.example.demo_room.presentation.util.Screen.Companion.ARG_NOTE_COLOR
import com.example.demo_room.presentation.util.Screen.Companion.ARG_NOTE_ID

@Composable
fun MainNavHostGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.NotesScreen.route
    ) {
        composable(route = Screen.NotesScreen.route) {
            NotesScreen(navController = navController)
        }
        composable(
            route = Screen.AddEditNoteScreen.route + "?$ARG_NOTE_ID={$ARG_NOTE_ID}&$ARG_NOTE_COLOR={$ARG_NOTE_COLOR}",
            arguments = listOf(
                navArgument(ARG_NOTE_ID) {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                },
                navArgument(ARG_NOTE_COLOR) {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) {
            val noteId = it.arguments?.getString(ARG_NOTE_ID)
            val noteColor = it.arguments?.getString(ARG_NOTE_COLOR)
            AddEditNoteScreen(
                navController = navController,
                noteId = noteId,
                noteColor = noteColor
            )
        }
    }
}