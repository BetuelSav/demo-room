package com.example.demo_room.presentation.feature_note.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demo_room.R
import com.example.demo_room.data.local.entities.Note
import com.example.demo_room.domain.use_case.AddNoteUseCase
import com.example.demo_room.domain.use_case.DeleteNoteUseCase
import com.example.demo_room.domain.use_case.GetAllNotesUseCase
import com.example.demo_room.domain.util.NoteSort
import com.example.demo_room.domain.util.OrderSort
import com.example.demo_room.presentation.feature_note.notes.util.NotesEvent
import com.example.demo_room.presentation.feature_note.notes.util.NotesState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class NotesViewModel(
    private val getAllNotesUseCase: GetAllNotesUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val addNoteUseCase: AddNoteUseCase,
) : ViewModel() {

    private val _state = mutableStateOf(NotesState())
    val state: State<NotesState> = _state

    private var recentlyDeleteNote: Note? = null

    private var getNotesJob: Job? = null

    init {
        getNotes(NoteSort.Date(OrderSort.DESCENDING))
        //create mock data
//        viewModelScope.launch {
//            for (i in 1..10) {
//                addNoteUseCase(
//                    Note(
//                        title = "Mock title $i",
//                        content = "Mock description $i \n nock new line \n mock new line",
//                        timestamp = 0L,
//                        color = R.color.purple_700
//                    )
//                )
//            }
//        }
    }

    fun onEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.Order -> getNotes(event.noteSort)
            is NotesEvent.DeleteNote -> viewModelScope.launch {
                deleteNoteUseCase(event.note)
                recentlyDeleteNote = event.note
            }
            is NotesEvent.RestoreNote -> viewModelScope.launch {
                addNoteUseCase(recentlyDeleteNote ?: return@launch)
                recentlyDeleteNote = null
            }
            is NotesEvent.ToggleOrderSection ->
                _state.value = state.value.copy(isOrderSectionVisible = state.value.isOrderSectionVisible.not())
        }
    }

    private fun getNotes(noteSort: NoteSort) {
        getNotesJob?.cancel()
        getNotesJob = getAllNotesUseCase(noteSort).onEach { notes ->
            _state.value = state.value.copy(
                notes = notes,
                noteSort = noteSort
            )
        }.launchIn(viewModelScope)
    }
}