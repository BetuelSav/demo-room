package com.example.demo_room.presentation.feature_note.notes.util

import com.example.demo_room.data.local.entities.Note
import com.example.demo_room.domain.util.NoteSort
import com.example.demo_room.domain.util.OrderSort

data class NotesState(
    val notes: List<Note> = emptyList(),
    val noteSort: NoteSort = NoteSort.Date(OrderSort.DESCENDING),
    val isOrderSectionVisible: Boolean = false
    )
