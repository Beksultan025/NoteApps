package com.example.noteapp02.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.noteapp02.data.db.daos.NoteDao
import com.example.noteapp02.data.db.models.NoteModel

@Database(entities = [NoteModel::class], version = 4)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}