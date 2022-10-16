package com.bignerdranch.android.sunset

import android.animation.AnimatorSet
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.Interpolator
import androidx.core.content.ContextCompat
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var sceneView: View
    private lateinit var sunView: View
    private lateinit var skyView: View
    private lateinit var starSkyView: StarSkyView
    private var isForwardAnimation = true


    private val blueSkyColor: Int by lazy {
        ContextCompat.getColor(this, R.color.blue_sky)
    }
    private val sunsetSkyColor: Int by lazy {
        ContextCompat.getColor(this, R.color.sunset_sky)
    }
    private val nightSkyColor: Int by lazy {
        ContextCompat.getColor(this, R.color.night_sky)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sceneView = findViewById(R.id.scene)
        sunView = findViewById(R.id.sun)
        skyView = findViewById(R.id.sky)
        starSkyView = findViewById(R.id.star_sky_view)

        sceneView.setOnClickListener {
            startAnimation()
        }

//        while (starsAmount > 0) {
//            starsList.add(Star.placeRandomStar(skyView))
//            starsAmount--
//        }
    }

    private fun startAnimation() {
        val sunYStart = sunView.top.toFloat()
        val sunYEnd = skyView.height.toFloat()
        val heightAnimatorForward = ObjectAnimator
            .ofFloat(sunView, "y", sunYStart, sunYEnd)
            .setDuration(3000)
        heightAnimatorForward.interpolator = AccelerateInterpolator()

        val sunTrembleAnimatorForward = ObjectAnimator
            .ofFloat(sunView, "x", sunView.left - 2.0f, sunView.left + 2.0f, sunView.left - 2.0f)
        sunTrembleAnimatorForward.repeatCount = 16

        val sunsetSkyAnimatorForward = ObjectAnimator
            .ofInt(skyView, "backgroundColor", blueSkyColor, sunsetSkyColor)
            .setDuration(3000)
        sunsetSkyAnimatorForward.setEvaluator(ArgbEvaluator())

        val nightSkyAnimatorForward = ObjectAnimator
            .ofInt(skyView, "backgroundColor", sunsetSkyColor, nightSkyColor)
            .setDuration(1500)
        nightSkyAnimatorForward.setEvaluator(ArgbEvaluator())

        val heightAnimatorBackward = ObjectAnimator
            .ofFloat(sunView, "y", sunYEnd, sunYStart)
            .setDuration(3000)
        heightAnimatorBackward.interpolator = AccelerateInterpolator()

        val sunsetSkyAnimatorBackward = ObjectAnimator
            .ofInt(skyView, "backgroundColor", sunsetSkyColor, blueSkyColor)
            .setDuration(3000)
        sunsetSkyAnimatorBackward.setEvaluator(ArgbEvaluator())
        sunsetSkyAnimatorBackward.interpolator = AccelerateInterpolator()

        val nightSkyAnimatorBackward = ObjectAnimator
            .ofInt(skyView, "backgroundColor", nightSkyColor, sunsetSkyColor)
            .setDuration(1500)
        nightSkyAnimatorBackward.setEvaluator(ArgbEvaluator())

        val starAnimatorForward = ObjectAnimator
            .ofFloat(starSkyView, "alpha", 0.0f, 1.0f)
            .setDuration(3000)
        starAnimatorForward.interpolator = AccelerateInterpolator()

        val starAnimatorBackward = ObjectAnimator
            .ofFloat(starSkyView, "alpha", 1.0f, 0.0f)
            .setDuration(2000)

        if (isForwardAnimation) {
            val animatorSetForward = AnimatorSet()
            animatorSetForward.play(heightAnimatorForward)
                .with(sunsetSkyAnimatorForward)
                .with(starAnimatorForward)
                .before(nightSkyAnimatorForward)
            animatorSetForward.start()

            starSkyView.invalidate()

            isForwardAnimation = false
        } else {
            val animatorSetBackward = AnimatorSet()
            animatorSetBackward.play(sunsetSkyAnimatorBackward)
                .with(heightAnimatorBackward)
                .with(starAnimatorBackward)
                .after(nightSkyAnimatorBackward)
            animatorSetBackward.start()

            isForwardAnimation = true
        }
        sunTrembleAnimatorForward.start()
    }

}