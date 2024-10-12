package com.example.noteapp02.ui.adapters

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp02.App
import com.example.noteapp02.data.db.models.NoteModel
import com.example.noteapp02.databinding.ItemNoteBinding

class NoteAdapter : ListAdapter<NoteModel, NoteAdapter.NoteViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            ItemNoteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.onBind(getItem(position))
        holder.itemView.setOnLongClickListener {
            AlertDialog.Builder(holder.itemView.context)
                .setTitle("Удаление элемента")
                .setMessage("Вы точно хотите удалить этот элемент?")
                .setPositiveButton("Да"){ _, _ ->
                    App.appDatabase?.noteDao()?.deleteAllNotes(getItem(position))
                }.setNegativeButton("Нет"){ dialog, _ ->
                    dialog.dismiss()
                }.show()
            true
        }
    }

    class NoteViewHolder(private val binding: ItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: NoteModel?) {
            binding.tvTitle.text = item?.title
            binding.tvDescription.text = item?.description
            binding.tvTime.text = item?.time
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<NoteModel>(){
    override fun areItemsTheSame(oldItem: NoteModel, newItem: NoteModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: NoteModel, newItem: NoteModel): Boolean {
        return oldItem == newItem
    }

}
