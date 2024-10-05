package com.example.noteapp02.ui.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.noteapp02.ui.fragment.onboard.OnBoardPagingFragment
import com.example.noteapp02.ui.fragment.onboard.OnBoardPagingFragment.Companion.ARG_ONBOARD_POSITION

class OnBoardPagingAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int) = OnBoardPagingFragment().apply {
        arguments = Bundle().apply {
            putInt(ARG_ONBOARD_POSITION, position  )
        }
    }
}