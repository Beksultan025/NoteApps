package com.example.noteapp02.data.db.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.noteapp02.data.db.models.NoteModel

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(noteModel: NoteModel)

    @Query("SELECT * FROM noteModel")
    fun getAllNotes() : LiveData<List<NoteModel>>

    @Delete
    fun deleteAllNote(noteModel: NoteModel)

    @Update
    fun updateNote(noteModel: NoteModel)

    @Query("SELECT * FROM noteModel WHERE id = :id")
    fun getNoteById(id: Int) : NoteModel
}