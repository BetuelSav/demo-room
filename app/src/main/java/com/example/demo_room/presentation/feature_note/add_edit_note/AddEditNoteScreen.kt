package com.example.demo_room.presentation.feature_note.add_edit_note

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.demo_room.R
import com.example.demo_room.data.local.entities.Note
import com.example.demo_room.presentation.feature_note.add_edit_note.components.TextFieldWithHint
import com.example.demo_room.presentation.feature_note.add_edit_note.util.AddEditNoteEvent
import com.example.demo_room.ui.theme.DarkGray
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

const val BACKGROUND_COLOR_ANIMATION_DURATION = 500

@Destination
@Composable
fun AddEditNoteScreen(
    navigator: DestinationsNavigator,
    noteId: Int?=null,
    noteColor: Int?=null,
    viewModel: AddEditNoteViewModel = getViewModel() { parametersOf(noteId, noteColor) },
) {
    val titleState = viewModel.noteTitle.value
    val contentState = viewModel.noteContent.value
    val scaffoldState = rememberScaffoldState()
    val noteBackgroundAnimate = remember { Animatable(Color(noteColor ?: viewModel.noteColor.value)) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is AddEditNoteViewModel.UiEvent.ShowSnackbar ->
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        duration = SnackbarDuration.Short
                    )
                is AddEditNoteViewModel.UiEvent.SaveNote -> navigator.navigateUp()
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.onEvent(AddEditNoteEvent.SaveNote) },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_save),
                    contentDescription = null,
                    tint = DarkGray
                )
            }
        },
        scaffoldState = scaffoldState
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .background(noteBackgroundAnimate.value)
            .padding(16.dp)
        ) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Note.noteColors.forEach { colorRGB ->
                    val colorInt = colorRGB.toArgb()
                    Box(modifier = Modifier
                        .size(40.dp)
                        .shadow(15.dp, CircleShape)
                        .clip(CircleShape)
                        .background(colorRGB)
                        .border(
                            width = 3.dp,
                            color = if (viewModel.noteColor.value == colorInt) Color.Black
                            else Color.Transparent,
                            shape = CircleShape
                        )
                        .clickable {
                            scope.launch {
                                noteBackgroundAnimate.animateTo(
                                    targetValue = Color(colorInt),
                                    animationSpec = tween(durationMillis = BACKGROUND_COLOR_ANIMATION_DURATION)
                                )
                            }
                            viewModel.onEvent(AddEditNoteEvent.ChangeColor(colorInt))
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            TextFieldWithHint(
                hintRes = R.string.title_hint,
                text = titleState.text,
                onValueChange = { newValue -> viewModel.onEvent(AddEditNoteEvent.EnteredTitle(newValue)) },
                onFocusChange = { focusState -> viewModel.onEvent(AddEditNoteEvent.ChangeTitleFocus(focusState)) },
                isHintVisible = titleState.isHintVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.h5
            )

            Spacer(modifier = Modifier.height(16.dp))
            TextFieldWithHint(
                hintRes = R.string.content_hint,
                text = contentState.text,
                onValueChange = { newValue -> viewModel.onEvent(AddEditNoteEvent.EnteredContent(newValue)) },
                onFocusChange = { focusState -> viewModel.onEvent(AddEditNoteEvent.ChangeContentFocus(focusState)) },
                isHintVisible = contentState.isHintVisible,
                singleLine = false,
                textStyle = MaterialTheme.typography.h5,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}