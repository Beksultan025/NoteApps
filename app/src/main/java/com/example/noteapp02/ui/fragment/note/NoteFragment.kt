package com.example.noteapp02.ui.fragment.note

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.noteapp02.App
import com.example.noteapp02.R
import com.example.noteapp02.databinding.FragmentNoteBinding
import com.example.noteapp02.ui.adapters.NoteAdapter

class NoteFragment : Fragment() {

    private lateinit var binding: FragmentNoteBinding
    private val noteAdapter = NoteAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteBinding.inflate(inflater,container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        setupListeners()
        getAllNotes()
    }

    private fun initialize() = with(binding) {
        rvNotes.adapter = noteAdapter
    }

    private fun setupListeners() {
        binding.btnAdd.setOnClickListener{
            findNavController().navigate(R.id.noteDetailFragment)
        }
    }

    private fun getAllNotes() {
        val app = App
        app.appDatabase?.noteDao()?.getAllNotes()?.observe(viewLifecycleOwner){ note ->
            noteAdapter.submitList(note)
        }
    }
}