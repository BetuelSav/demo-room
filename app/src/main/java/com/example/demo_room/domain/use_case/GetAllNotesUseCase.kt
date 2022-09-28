package com.example.demo_room.domain.use_case

import com.example.demo_room.data.local.entities.Note
import com.example.demo_room.domain.repository.NoteRepository
import com.example.demo_room.domain.util.NoteSort
import com.example.demo_room.domain.util.OrderSort
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAllNotesUseCase(private val noteRepository: NoteRepository) {
    operator fun invoke(noteSort: NoteSort): Flow<List<Note>> =
        noteRepository.getAllNotes().map { notes ->
            when (noteSort.orderSort) {
                OrderSort.ASCENDING ->
                    when (noteSort) {
                        is NoteSort.Title -> notes.sortedBy { it.title.lowercase() }
                        is NoteSort.Date -> notes.sortedBy { it.timestamp }
                        is NoteSort.Color -> notes.sortedBy { it.color }
                    }
                OrderSort.DESCENDING ->
                    when (noteSort) {
                        is NoteSort.Title -> notes.sortedByDescending { it.title.lowercase() }
                        is NoteSort.Date -> notes.sortedByDescending { it.timestamp }
                        is NoteSort.Color -> notes.sortedByDescending { it.color }
                    }
            }
        }
}