package com.anwesh.uiprojects.linkedincreasinglineview

/**
 * Created by anweshmishra on 26/06/18.
 */
import android.app.Activity
import android.view.MotionEvent
import android.content.Context
import android.content.pm.ActivityInfo
import android.view.View
import android.graphics.Paint
import android.graphics.Canvas
import android.graphics.Color

val IL_NODES : Int = 5

class LinkedIncreasingLineView(ctx : Context) : View(ctx) {

    private val paint : Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val renderer : Renderer = Renderer(this)

    override fun onDraw(canvas : Canvas) {
        renderer.render(canvas, paint)
    }

    override fun onTouchEvent(event : MotionEvent) : Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                renderer.handleTap()
            }
        }
        return true
    }

    data class ILState(var scale : Float = 0f, var dir : Float = 0f, var prevScale : Float = 0f) {

        fun update(stopcb : (Float) -> Unit) {
            scale += 0.1f * dir
            if (Math.abs(scale - prevScale) > 1) {
                scale = prevScale + dir
                dir = 0f
                prevScale = scale
                stopcb(prevScale)
            }
        }

        fun startUpdating(startcb : () -> Unit) {
            if (dir == 0f) {
                dir = 1 - 2 * prevScale
                startcb()
            }
        }
    }

    data class ILAnimator(var view : View, var animated : Boolean = false) {

        fun animate(cb : () -> Unit) {
            if (animated) {
                cb()
                try {
                    Thread.sleep(50)
                    view.invalidate()
                } catch(ex : Exception) {

                }
            }
        }

        fun start() {
            if (!animated) {
                animated = true
                view.postInvalidate()
            }
        }

        fun stop() {
            if (animated) {
                animated = false
            }
        }
    }

    data class ILNode(var i : Int, private val state : ILState = ILState()) {

        private var next : ILNode? = null

        private var prev : ILNode? = null

        init {
            addNeighbor()
        }

        fun addNeighbor() {
            if (i < IL_NODES - 1) {
                next = ILNode(i + 1)
                next?.prev = this
            }
        }

        fun draw(canvas : Canvas, paint : Paint) {
            val w : Float = canvas.width.toFloat()
            val h : Float = canvas.height.toFloat()
            val hGap : Float = (h * 0.95f) / IL_NODES
            val wGap : Float = (w * 0.95f) / IL_NODES
            prev?.draw(canvas, paint)
            paint.strokeWidth = Math.min(w, h) / 60
            paint.strokeCap = Paint.Cap.ROUND
            paint.color = Color.parseColor("#01579B")
            canvas.save()
            canvas.translate(wGap * i + wGap * state.scale,h/2)
            val hy : Float = (hGap * i + hGap * state.scale) / 2
            canvas.drawLine(0f, -hy, 0f, hy, paint)
            canvas.restore()
        }

        fun update(stopcb : (Float) -> Unit) {
            state.update(stopcb)
        }

        fun startUpdating(startcb : () -> Unit) {
            state.startUpdating(startcb)
        }

        fun getNext(dir : Int, cb : () -> Unit) : ILNode {
            var curr : ILNode? = prev
            if (dir == 1) {
                curr = next
            }
            if (curr != null) {
                return curr
            }
            cb()
            return this
        }
    }

    data class LinkedIncreasingLine(var i : Int) {

        private var curr : ILNode = ILNode(0)

        private var dir : Int = 1

        fun draw(canvas : Canvas, paint : Paint) {
            curr.draw(canvas, paint)
        }

        fun update(stopcb : (Float) -> Unit) {
            curr.update {
                curr = curr.getNext(dir) {
                    dir *= -1
                }
                stopcb(it)
            }
        }

        fun startUpdating(startcb : () -> Unit) {
            curr.startUpdating(startcb)
        }
    }

    data class Renderer(var view : LinkedIncreasingLineView) {

        private val lil : LinkedIncreasingLine = LinkedIncreasingLine(0)

        private val animator : ILAnimator = ILAnimator(view)

        fun render(canvas : Canvas, paint : Paint) {
            canvas.drawColor(Color.parseColor("#212121"))
            lil.draw(canvas, paint)
            animator.animate {
                lil.update {
                    animator.stop()
                }
            }
        }

        fun handleTap() {
            lil.startUpdating {
                animator.start()
            }
        }
    }

    companion object {
        fun create(activity : Activity) : LinkedIncreasingLineView {
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            val view : LinkedIncreasingLineView = LinkedIncreasingLineView(activity)
            activity.setContentView(view)
            return view
        }
    }
 }