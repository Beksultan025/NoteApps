package com.example.noteapp02.ui.fragment.note

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.core.content.res.ResourcesCompat.getDrawable
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.noteapp02.App
import com.example.noteapp02.R
import com.example.noteapp02.data.db.models.NoteModel
import com.example.noteapp02.databinding.ColorPickerDialogBinding
import com.example.noteapp02.databinding.FragmentNoteDetailBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NoteDetailFragment : Fragment() {

    private lateinit var binding: FragmentNoteDetailBinding
    private lateinit var popupView: ColorPickerDialogBinding
    private var selectedView: View? = null
    private var backgroundColorView: Int? = null
    private var noteId = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteDetailBinding.inflate(inflater, container, false)
        popupView = ColorPickerDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        setupListeners()
        isEditTextEmpty()
        data()
        setupPopup()
    }


    private fun initialize() = with(binding) {
        tvTime.text = SimpleDateFormat("dd MMMM HH:mm", Locale("ru")).format(Date())
    }

    private fun setupListeners() = with(binding) {
        imgMoreOptions.setOnClickListener { view ->
            showCustomPopup(view)
        }
        imgBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun isEditTextEmpty() = with(binding) {
        tvReady.visibility = View.GONE
        val textWatcher = { _: Editable? ->
            tvReady.visibility =
                if (etTitle.text.isNotEmpty() && etDescription.text.isNotEmpty()) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
        }
        etTitle.doAfterTextChanged(textWatcher)
        etDescription.doAfterTextChanged(textWatcher)
    }

    private fun data() = with(binding) {
        val args = arguments?.getInt("note", -1)
        val data = App.appDatabase?.noteDao()?.getNoteById(args!!)
        noteId = args!!

        etTitle.setText(data?.title)
        etDescription.setText(data?.description)
        backgroundColorView = data?.backgroundColor

        tvReady.setOnClickListener {
            if (noteId != -1) {
                val noteModel = NoteModel(
                    etTitle.text.toString(),
                    etDescription.text.toString(),
                    tvTime.text.toString(),
                    backgroundColor = backgroundColorView!!
                )
                noteModel.id = noteId
                App.appDatabase?.noteDao()?.updateNote(noteModel)
            } else {
                App.appDatabase?.noteDao()
                    ?.insertNote(
                        NoteModel(
                            etTitle.text.toString(),
                            etDescription.text.toString(),
                            tvTime.text.toString(),
                            backgroundColor = backgroundColorView ?: R.color.yellow
                        )
                    )
            }
            findNavController().navigateUp()
        }
    }

    private fun setupPopup() = with(popupView) {
        val listColorsView =
            arrayListOf(colorYellow, colorPurple, colorRed, colorPink, colorBlue, colorGreen)
        listColorsView.forEach { view ->
            view.setOnClickListener {
                onClickView(view)
                backgroundColorView =  view.backgroundTintList?.defaultColor
            }
        }
    }

    private fun onClickView(view: View) {
        selectedView?.foreground = null
        selectedView = view
        selectedView?.foreground = getDrawable(resources, R.drawable.corner_view_choose, null)
    }

    private fun showCustomPopup(view: View) {
        val popupWindow = PopupWindow(
            popupView.root,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            true
        )
        popupWindow.showAsDropDown(view, 0, 0)
    }
}