package com.uzias.starwarsshop.util

import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.Button

fun Button.changeTextWithAnimation(text: String){
    val anim = AlphaAnimation(1.0f, 0.0f)
    anim.duration = 200
    anim.repeatCount = 1
    anim.repeatMode = Animation.REVERSE
    anim.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationEnd(animation: Animation?) { }
        override fun onAnimationStart(animation: Animation?) { }
        override fun onAnimationRepeat(animation: Animation?) {
            this@changeTextWithAnimation.text = text
        }
    })
    this.startAnimation(anim)

}