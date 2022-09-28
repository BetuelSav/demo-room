package com.example.demo_room.domain.use_case

import com.example.demo_room.data.local.entities.Note
import com.example.demo_room.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class GetAllNotesUseCase(private val noteRepository: NoteRepository) {
    operator fun invoke(): Flow<List<Note>> = noteRepository.getAllNotes()
}