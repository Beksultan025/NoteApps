package com.example.noteapp02.data.db.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.noteapp02.data.db.models.NoteModel

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(noteModel: NoteModel)

    @Delete
    fun deleteAllNotes(noteModel: NoteModel)

    @Query("SELECT * FROM noteModel")
    fun getAllNotes() : LiveData<List<NoteModel>>
}