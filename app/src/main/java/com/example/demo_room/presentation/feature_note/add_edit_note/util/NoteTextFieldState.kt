package com.example.demo_room.presentation.feature_note.add_edit_note.util

import androidx.annotation.StringRes
import com.example.demo_room.R

data class NoteTextFieldState(
    val text: String = "",
    @StringRes val hint: Int = R.string.empty_string,
    val isHintVisible: Boolean = true,
)