package com.example.demo_room.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.demo_room.ui.theme.BabyBlue
import com.example.demo_room.ui.theme.LightGreen
import com.example.demo_room.ui.theme.RedOrange
import com.example.demo_room.ui.theme.RedPink
import com.example.demo_room.ui.theme.Violet

@Entity
data class Note(
    @PrimaryKey val id: Int? = null,
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
) {
    companion object {
        val noteColors = listOf(RedOrange, LightGreen, Violet, BabyBlue, RedPink)
    }
}

class InvalidNoteException(message: String) : Exception(message)
