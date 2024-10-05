package com.example.noteapp02.ui.fragment.onboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.noteapp02.R
import com.example.noteapp02.databinding.FragmentOnBoardPagingBinding

class OnBoardPagingFragment : Fragment() {

    private lateinit var binding: FragmentOnBoardPagingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnBoardPagingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        setupListeners()
    }

    private fun initialize() = with(binding) {
        when (requireArguments().getInt(ARG_ONBOARD_POSITION)) {
            0 -> {
                tvTitle.text = "Удобство"
                tvDescription.text =
                    "Создавайте заметки в два клика! Записывайте мысли, идеи и важные задачи мгновенно."
                lottieAnimation.setAnimation(R.raw.first_lotiie)
            }

            1 -> {
                tvTitle.text = "Организация"
                tvDescription.text =
                    "Организуйте заметки по папкам и тегам. Легко находите нужную информацию в любое время.."
                lottieAnimation.setAnimation(R.raw.second_lottie)
            }

            2 -> {
                tvTitle.text = "Синхронизация"
                tvDescription.text =
                    "Синхронизация на всех устройствах. Доступ к записям в любое время и в любом месте."
                lottieAnimation.setAnimation(R.raw.third_lottie)
            }
        }
    }

    private fun setupListeners() {
    }

    companion object {
        const val ARG_ONBOARD_POSITION = "onBoard"
    }
}
