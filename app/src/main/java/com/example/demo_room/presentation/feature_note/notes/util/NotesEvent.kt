package com.example.demo_room.presentation.feature_note.notes.util

import com.example.demo_room.data.local.entities.Note
import com.example.demo_room.domain.util.NoteSort

sealed class NotesEvent {
    data class Order(val noteSort: NoteSort) : NotesEvent()
    data class DeleteNote(val note: Note) : NotesEvent()
    object RestoreNote : NotesEvent()
    object ToggleOrderSection : NotesEvent()
}
