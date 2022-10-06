package com.example.demo_room.domain.util

sealed class NoteSort(val orderSort: OrderSort) {
    class Title(orderSort: OrderSort) : NoteSort(orderSort)
    class Date(orderSort: OrderSort) : NoteSort(orderSort)
    class Color(orderSort: OrderSort) : NoteSort(orderSort)

    fun copy(orderSort: OrderSort): NoteSort =
        when (this) {
            is Title -> Title(orderSort)
            is Date -> Date(orderSort)
            is Color -> Color(orderSort)
        }
}
