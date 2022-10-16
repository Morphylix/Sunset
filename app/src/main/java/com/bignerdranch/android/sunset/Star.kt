package com.bignerdranch.android.sunset

import android.graphics.PointF
import android.view.View
import kotlin.random.Random


const val STAR_SIZE = 10.0f

class Star(val start: PointF, size: Float) {
    val left = start.x
    val right = start.x + size
    val top = start.y
    val bottom = start.y + size


    companion object {

        fun placeRandomStar(view: View): Star {
            val x = Random.nextDouble(view.width.toDouble()).toFloat()
            val y = Random.nextDouble(view.height.toDouble()).toFloat()
            val start = PointF(x, y)

            return Star(start, STAR_SIZE)
        }
    }
}