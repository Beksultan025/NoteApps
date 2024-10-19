package com.example.noteapp02.interfaces

import com.example.noteapp02.data.db.models.NoteModel

interface OnClickListeners {

    fun onLongClickItem(noteModel: NoteModel)

    fun onClickItem(noteModel: NoteModel)
}