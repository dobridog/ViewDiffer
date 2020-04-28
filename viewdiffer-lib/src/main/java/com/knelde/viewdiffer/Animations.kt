package com.knelde.viewdiffer

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.TransitionDrawable
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.annotation.ColorInt
import androidx.annotation.IntRange

fun highlight(
    view: View, @ColorInt color: Int = Color.YELLOW, @IntRange(
        from = 1000,
        to = 10000
    ) durationMs: Int = 2000
) {
    val highlight =
        arrayOf(
            ColorDrawable(color),
            view.background ?: ColorDrawable(Color.TRANSPARENT)
        )
    val trans = TransitionDrawable(highlight)
    view.background = trans
    trans.isCrossFadeEnabled = true

    trans.startTransition(durationMs)
}

fun pulse(
    view: View, scale: Float = 1.25f, @IntRange(
        from = 150,
        to = 2000
    ) durationMs: Int = 250
) {
    val startDelay = 200L
    val growX = ObjectAnimator.ofFloat(view, "scaleX", 1f, scale)
    val growY = ObjectAnimator.ofFloat(view, "scaleY", 1f, scale)

    val shrinkX = ObjectAnimator.ofFloat(view, "scaleX", scale, 1f)
    val shrinkY = ObjectAnimator.ofFloat(view, "scaleY", scale, 1f)

    val bounce = AnimatorSet().apply {
        play(growX)
            .with(growY)
            .before(shrinkX)
            .before(shrinkY)
    }

    bounce.interpolator = AccelerateDecelerateInterpolator()

    bounce.startDelay = startDelay
    bounce.duration = durationMs.toLong()

    bounce.start()
}