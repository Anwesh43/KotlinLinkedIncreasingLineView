package com.anwesh.uiprojects.linkedincreasinglineview

/**
 * Created by anweshmishra on 26/06/18.
 */
import android.view.MotionEvent
import android.content.Context
import android.view.View
import android.graphics.Paint
import android.graphics.Canvas

class LinkedIncreasingLineView(ctx : Context) : View(ctx) {

    private val paint : Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onDraw(canvas : Canvas) {

    }

    override fun onTouchEvent(event : MotionEvent) : Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
        }
        return true
    }
}