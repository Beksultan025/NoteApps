package com.example.noteapp02.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp02.data.db.models.NoteModel
import com.example.noteapp02.databinding.ItemNoteBinding
import com.example.noteapp02.interfaces.OnClickListeners

class NoteAdapter(
    private val onLongClick: OnClickListeners,
    private val onClick: OnClickListeners
) : ListAdapter<NoteModel, NoteAdapter.NoteViewHolder>(DiffCallback()) {

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
            onLongClick.onLongClickItem(getItem(position))
            true
        }
        holder.itemView.setOnClickListener {
            onClick.onClickItem(getItem(position))
        }
    }

    class NoteViewHolder(private val binding: ItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(noteModel: NoteModel)  = with(binding){
            tvTitle.text = noteModel.title
            tvDescription.text = noteModel.description
            tvTime.text = noteModel.time
            container.setBackgroundColor(noteModel.backgroundColor!!)
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<NoteModel>() {
    override fun areItemsTheSame(oldItem: NoteModel, newItem: NoteModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: NoteModel, newItem: NoteModel): Boolean {
        return oldItem == newItem
    }

}
