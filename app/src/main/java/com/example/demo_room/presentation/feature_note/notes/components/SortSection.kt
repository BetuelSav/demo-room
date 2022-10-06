package com.example.demo_room.presentation.feature_note.notes.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.demo_room.R
import com.example.demo_room.domain.util.NoteSort
import com.example.demo_room.domain.util.OrderSort

@Composable
fun SortSection(
    modifier: Modifier = Modifier,
    noteSort: NoteSort = NoteSort.Date(OrderSort.DESCENDING),
    onNoteSortChange: (NoteSort) -> Unit,
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = stringResource(id = R.string.sort_date),
                selected = noteSort is NoteSort.Date,
                onSelect = { onNoteSortChange(NoteSort.Date(noteSort.orderSort)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = stringResource(id = R.string.sort_title),
                selected = noteSort is NoteSort.Title,
                onSelect = { onNoteSortChange(NoteSort.Title(noteSort.orderSort)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = stringResource(id = R.string.sort_color),
                selected = noteSort is NoteSort.Color,
                onSelect = { onNoteSortChange(NoteSort.Color(noteSort.orderSort)) }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = stringResource(id = R.string.sort_ascending),
                selected = noteSort.orderSort == OrderSort.ASCENDING,
                onSelect = { onNoteSortChange(noteSort.copy(OrderSort.ASCENDING)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = stringResource(id = R.string.sort_descending),
                selected = noteSort.orderSort == OrderSort.DESCENDING,
                onSelect = { onNoteSortChange(noteSort.copy(OrderSort.DESCENDING)) }
            )
        }
    }
}