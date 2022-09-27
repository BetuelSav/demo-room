package com.example.demo_room.data.local.repository

import com.example.demo_room.data.local.dao.NoteDao
import com.example.demo_room.data.local.entities.Note
import com.example.demo_room.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImplementation(private val dao: NoteDao) : NoteRepository {

    override fun getNotes(): Flow<List<Note>> = dao.getNotes()

    override suspend fun getNoteById(id: Int): Note? = dao.getNoteById(id)

    override suspend fun insertNote(note: Note) = dao.insertNote(note)

    override suspend fun deleteNote(note: Note) = dao.deleteNote(note)

}