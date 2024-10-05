package com.example.noteapp02.ui.animation

import android.view.View
import androidx.viewpager2.widget.ViewPager2

class DepthPageTransformer() : ViewPager2.PageTransformer {
    override fun transformPage(page: View, position: Float) {
        when {
            position < -1 -> {
                page.alpha = 0f
            }

            position <= 0 -> {
                page.alpha = 1 + position
                page.translationX = 0f
                page.scaleY = 1 + 0.1f * position
            }

            position <= 1 -> {
                page.alpha = 1 - position
                page.translationX = -page.width * position
                page.scaleY = 1 - 0.1f * position
            }
        }
    }
}