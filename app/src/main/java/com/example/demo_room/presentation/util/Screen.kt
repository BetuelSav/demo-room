package com.example.demo_room.presentation.util

sealed class Screen(val route: String) {
    object NotesScreen : Screen("notes_screen")
    object AddEditNoteScreen : Screen("add_edit_note_screen")

    companion object{
        const val ARG_NOTE_ID = "note_id"
        const val ARG_NOTE_COLOR = "note_color"
    }
}