package com.example.demo_room.presentation.feature_note.notes

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.demo_room.R
import com.example.demo_room.presentation.feature_note.destinations.AddEditNoteScreenDestination
import com.example.demo_room.presentation.feature_note.notes.components.NoteItem
import com.example.demo_room.presentation.feature_note.notes.components.SortSection
import com.example.demo_room.presentation.feature_note.notes.util.NotesEvent
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@Destination(start = true)
@Composable
fun NotesScreen(
    navigator: DestinationsNavigator,
    viewModel: NotesViewModel = getViewModel(),
) {
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navigator.navigate(AddEditNoteScreenDestination()) },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = MaterialTheme.colors.onSurface
                )
            }
        },
        scaffoldState = scaffoldState
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.notes_title),
                    style = MaterialTheme.typography.h4
                )
                IconButton(
                    onClick = { viewModel.onEvent(NotesEvent.ToggleOrderSection) }
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = null,
                    )
                }
            }

            AnimatedVisibility(
                visible = state.isOrderSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                SortSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    noteSort = state.noteSort,
                    onNoteSortChange = { noteSort -> viewModel.onEvent(NotesEvent.Order(noteSort)) }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)

            ) {
                items(state.notes) { note ->
                    NoteItem(
                        note = note,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navigator.navigate(
                                    AddEditNoteScreenDestination(
                                        noteId = note.id,
                                        noteColor = note.color)
                                )
                            },
                        onDeleteClick = {
                            viewModel.onEvent(NotesEvent.DeleteNote(note))
                            scope.launch {
                                scaffoldState.snackbarHostState.showSnackbar(
                                    message = context.getString(R.string.note_deleted),
                                    actionLabel = context.getString(R.string.undo),
                                    duration = SnackbarDuration.Short
                                ).also { result ->
                                    if (result == SnackbarResult.ActionPerformed)
                                        viewModel.onEvent(NotesEvent.RestoreNote)
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}