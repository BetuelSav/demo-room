package com.example.demo_room.domain.use_case

import com.example.demo_room.data.local.entities.Note
import com.example.demo_room.domain.repository.NoteRepository

class GetNoteByIdUseCase(private val noteRepository: NoteRepository) {
    suspend operator fun invoke(id: Int): Note? = noteRepository.getNoteById(id)
}