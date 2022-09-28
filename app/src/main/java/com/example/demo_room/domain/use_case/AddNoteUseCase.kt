package com.example.demo_room.domain.use_case

import com.example.demo_room.data.local.entities.InvalidNoteException
import com.example.demo_room.data.local.entities.Note
import com.example.demo_room.domain.repository.NoteRepository

class AddNoteUseCase(private val noteRepository: NoteRepository) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        when {
            note.title.isBlank() -> throw  InvalidNoteException("The title can't be empty")
            note.content.isBlank() -> throw InvalidNoteException("The conte can't be empty")
            else -> noteRepository.insertNote(note)
        }
    }
}