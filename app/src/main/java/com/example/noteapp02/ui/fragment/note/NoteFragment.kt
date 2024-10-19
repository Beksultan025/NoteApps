package com.example.noteapp02.ui.fragment.note

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.noteapp02.App
import com.example.noteapp02.R
import com.example.noteapp02.data.db.models.NoteModel
import com.example.noteapp02.databinding.FragmentNoteBinding
import com.example.noteapp02.interfaces.OnClickListeners
import com.example.noteapp02.ui.adapters.NoteAdapter
import com.example.noteapp02.utils.PreferenceHelper

class NoteFragment : Fragment(), OnClickListeners {

    private lateinit var binding: FragmentNoteBinding
    private val preferences = PreferenceHelper()
    private val noteAdapter = NoteAdapter(this, this)
    private var layoutManager = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        setupListeners()
        getAllNotes()
    }

    private fun initialize() = with(binding) {
        preferences.unit(requireContext())
        if (preferences.layoutManager) {
            rvNotes.layoutManager = LinearLayoutManager(context)
            imgShape.setImageResource(R.drawable.img_shape)
        } else {
            rvNotes.layoutManager = GridLayoutManager(context, 2)
            imgShape.setImageResource(R.drawable.img_layout_manager)
        }
        rvNotes.adapter = noteAdapter
    }

    private fun setupListeners() {
        binding.btnAdd.setOnClickListener {
            findNavController().navigate(R.id.noteDetailFragment)
        }
        binding.imgShape.setOnClickListener {
            layoutManager = !layoutManager
            if (layoutManager) {
                preferences.layoutManager = true
                binding.rvNotes.layoutManager = LinearLayoutManager(context)
                binding.imgShape.setImageResource(R.drawable.img_shape)
            } else {
                preferences.layoutManager = false
                binding.rvNotes.layoutManager = GridLayoutManager(context, 2)
                binding.imgShape.setImageResource(R.drawable.img_layout_manager)
            }
        }
    }

    private fun getAllNotes() {
        App.appDatabase?.noteDao()?.getAllNotes()?.observe(viewLifecycleOwner) { note ->
            noteAdapter.submitList(note)
        }
    }

    override fun onLongClickItem(noteModel: NoteModel) {
        AlertDialog.Builder(requireContext())
            .setTitle("Удаление элемента")
            .setMessage("Вы точно хотите удалить этот элемент?")
            .setPositiveButton("Да") { _, _ ->
                App.appDatabase?.noteDao()?.deleteAllNote(noteModel)
            }.setNegativeButton("Нет") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    override fun onClickItem(noteModel: NoteModel) {
        val argument = NoteFragmentDirections.actionNoteFragmentToNoteDetailFragment(noteModel.id)
        findNavController().navigate(argument)
    }
}