package com.example.noteapp02.data.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.noteapp02.R

@Entity(tableName = "noteModel")
data class NoteModel(
    val title: String,
    val description: String,
    val time: String,
    val backgroundColor: Int? = R.color.yellow
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
