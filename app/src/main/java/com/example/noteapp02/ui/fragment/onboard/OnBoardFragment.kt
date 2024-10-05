package com.example.noteapp02.ui.fragment.onboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.noteapp02.databinding.FragmentOnBoardBinding
import com.example.noteapp02.ui.adapters.OnBoardPagingAdapter
import com.example.noteapp02.ui.animation.DepthPageTransformer
import com.google.android.material.tabs.TabLayoutMediator

class OnBoardFragment : Fragment() {

    private lateinit var binding: FragmentOnBoardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnBoardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        setupListeners()
    }

    private fun initialize() = with(binding.viewpager2) {
        adapter = OnBoardPagingAdapter(this@OnBoardFragment)
        TabLayoutMediator(binding.intoTabLayout, this) { _, _ -> }.attach()
        setPageTransformer(DepthPageTransformer())
    }


    private fun setupListeners() = with(binding.viewpager2) {
        registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    2 -> {
                        skipAnimOut(binding.tvSkip)
                        fadeIn(binding.btnStart)
                    }

                    else -> {
                        fadeOut(binding.btnStart)
                        skipAnimIn(binding.tvSkip)
                        binding.tvSkip.setOnClickListener {
                            setCurrentItem(currentItem + 2, true)
                        }
                    }
                }
            }
        })

    }

    private fun skipAnimIn(view: View) {
        view.visibility = View.VISIBLE
        view.alpha = 0f
        view.animate()
            .alpha(1f)
            .setDuration(750)
            .setListener(null)
            .start()
    }


    private fun skipAnimOut(view: View) {
        view.animate()
            .alpha(0f)
            .translationY(50f)
            .setDuration(750)
            .withEndAction {
                view.visibility = View.INVISIBLE
            }.start()
    }

}

private fun fadeIn(view: View) {
    view.visibility = View.VISIBLE
    view.alpha = 0f
    view.animate()
        .alpha(1f)
        .translationY(-50f)
        .setDuration(750)
        .setListener(null)
        .start()
}

private fun fadeOut(view: View) {
    view.animate()
        .alpha(0f)
        .translationY(50f)
        .setDuration(750)
        .withEndAction {
            view.visibility = View.INVISIBLE
        }.start()
}
