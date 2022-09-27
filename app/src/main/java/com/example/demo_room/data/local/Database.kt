package com.example.demo_room.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.demo_room.data.local.dao.NoteDao
import com.example.demo_room.data.local.entities.Note

@Database(entities = [Note::class], version = 1)
abstract class Database: RoomDatabase() {

    abstract fun noteDao() : NoteDao
}