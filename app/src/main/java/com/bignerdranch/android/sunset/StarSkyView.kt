package com.bignerdranch.android.sunset

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import kotlin.random.Random

private const val STARS_MIN_AMOUNT = 20
private const val STARS_MAX_AMOUNT = 40

class StarSkyView(context: Context, attrs: AttributeSet): View(context, attrs) {

    private val starColor = Paint().apply {
        color = ContextCompat.getColor(context, R.color.star)
    }

    private val starsMinAmount = 20
    private val starsMaxAmount = 40
    private var starsList = mutableListOf<Star>()
    private var starsAmount = 0

    override fun onDraw(canvas: Canvas?) {
        shuffleStars()

        starsList.forEach {
            canvas?.drawRect(it.left, it.top, it.right, it.bottom, starColor)
        }
    }

    fun shuffleStars() {
        starsList.clear()
        starsAmount = Random.nextInt(STARS_MIN_AMOUNT, STARS_MAX_AMOUNT)
        while (starsAmount > 0) {
            val star = Star.placeRandomStar(this)
            starsList.add(star)
            starsAmount--
        }
    }
}