package com.example.demo_room.domain.util

sealed class NoteSort(val orderSort: OrderSort) {
    data class Title(val orderSortEnum: OrderSort) : NoteSort(orderSortEnum)
    data class Date(val orderSortEnum: OrderSort) : NoteSort(orderSortEnum)
    data class Color(val orderSortEnum: OrderSort) : NoteSort(orderSortEnum)
}
