package com.example.noteapp02.ui.fragment.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.noteapp02.databinding.FragmentChatBinding
import com.example.noteapp02.ui.adapters.ChatAdapter
import com.google.firebase.Firebase
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore

class ChatFragment : Fragment() {

    private lateinit var binding: FragmentChatBinding
    private val chatAdapter = ChatAdapter()
    private val db = Firebase.firestore
    private lateinit var query: Query
    private lateinit var listener: ListenerRegistration

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        setupListeners()
        observeData()
    }

    private fun initialize() {
        binding.rvChat.adapter = chatAdapter
    }

    private fun setupListeners() {
        binding.imgBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.btnSend.setOnClickListener {
            if (binding.edChat.text.isNotEmpty()) {
                val user = hashMapOf(
                    "name" to binding.edChat.text.toString()
                )
                db.collection("chat")
                    .add(user)
                    .addOnSuccessListener {
                        Toast.makeText(
                            requireContext(),
                            "Данные были успешно отправлены!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(requireContext(), "Не удалось отправить!", Toast.LENGTH_SHORT)
                            .show()
                    }
                binding.edChat.text.clear()    
            }else{
                Toast.makeText(requireContext(), "Пустой текст!", Toast.LENGTH_SHORT).show()
            }
            
        }
    }

    private fun observeData() {
        query = db.collection("chat")
        listener = query.addSnapshotListener { value, error ->
            if (error != null) {
                return@addSnapshotListener
            }
            value?.let { snapshot ->
                val messages = mutableListOf<String>()
                for (doc in snapshot.documents) {
                    val message = doc.getString("name")
                    messages.add(doc.id)
                    message?.let {
                        messages.add(it)
                    }
                }
                chatAdapter.submitList(messages)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        listener.remove()
    }
}