package com.example.demo_room.domain.use_case

import com.example.demo_room.data.local.entities.Note
import com.example.demo_room.domain.repository.NoteRepository

class DeleteNoteUseCase(private val noteRepository: NoteRepository) {
    suspend operator fun invoke(note: Note) = noteRepository.deleteNote(note)
}