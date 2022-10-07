package com.example.demo_room.presentation.feature_note.add_edit_note

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demo_room.R
import com.example.demo_room.data.local.entities.InvalidNoteException
import com.example.demo_room.data.local.entities.Note
import com.example.demo_room.domain.use_case.AddNoteUseCase
import com.example.demo_room.domain.use_case.GetNoteByIdUseCase
import com.example.demo_room.presentation.feature_note.add_edit_note.util.AddEditNoteEvent
import com.example.demo_room.presentation.feature_note.add_edit_note.util.NoteTextFieldState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

const val ERROR_NOTE_NOT_SAVED = "Couldn't save note"

class AddEditNoteViewModel(
    private val getNoteByIdUseCase: GetNoteByIdUseCase,
    private val addNoteUseCase: AddNoteUseCase,
    noteId: Int?,
) : ViewModel() {

    private val _noteTitle = mutableStateOf(NoteTextFieldState(hint = R.string.title_hint))
    val noteTitle: State<NoteTextFieldState> = _noteTitle

    private val _noteContent = mutableStateOf(NoteTextFieldState(hint = R.string.content_hint))
    val noteContent: State<NoteTextFieldState> = _noteContent

    private val _noteColor = mutableStateOf<Int>(Note.noteColors.random().toArgb())
    val noteColor: State<Int> = _noteColor

    // One time events, similar with LiveEvent
    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow: SharedFlow<UiEvent> = _eventFlow

    private var currentNoteId: Int? = null

    init {
        currentNoteId = noteId
        getCurrentNote()
    }

    fun onEvent(event: AddEditNoteEvent) {
        when (event) {
            is AddEditNoteEvent.EnteredTitle -> _noteTitle.value = noteTitle.value.copy(text = event.newValue)
            is AddEditNoteEvent.ChangeTitleFocus -> _noteTitle.value = noteTitle.value.copy(
                isHintVisible = !event.focusState.isFocused && noteTitle.value.text.isBlank()
            )
            is AddEditNoteEvent.EnteredContent -> _noteContent.value = noteContent.value.copy(text = event.newValue)
            is AddEditNoteEvent.ChangeContentFocus -> _noteContent.value = noteContent.value.copy(
                isHintVisible = !event.focusState.isFocused && noteContent.value.text.isBlank()
            )
            is AddEditNoteEvent.ChangeColor -> _noteColor.value = event.color
            is AddEditNoteEvent.SaveNote -> viewModelScope.launch {
                try {
                    addNoteUseCase(
                        Note(
                            id = currentNoteId,
                            title = _noteTitle.value.text,
                            content = _noteContent.value.text,
                            timestamp = System.currentTimeMillis(),
                            color = _noteColor.value
                        )
                    )
                    _eventFlow.emit(UiEvent.SaveNote)
                } catch (e: InvalidNoteException) {
                    _eventFlow.emit(UiEvent.ShowSnackbar(e.message ?: ERROR_NOTE_NOT_SAVED))
                }
            }
        }
    }

    private fun getCurrentNote() {
        currentNoteId?.let { id ->
            viewModelScope.launch {
                getNoteByIdUseCase(id)?.let { note ->
                    _noteTitle.value = noteTitle.value.copy(
                        text = note.title,
                        isHintVisible = false
                    )
                    _noteContent.value = noteContent.value.copy(
                        text = note.content,
                        isHintVisible = false
                    )
                    _noteColor.value = note.color
                }
            }
        }
    }


    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        object SaveNote : UiEvent()

    }
}