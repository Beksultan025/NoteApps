package com.example.noteapp02.data.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "noteModel")
data class NoteModel(
    val title: String,
    val description: String,
    val time: String,
    val backgroundTint: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
