package com.anwesh.uiprojects.kotlinlinkedincreasinglineview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import com.anwesh.uiprojects.linkedincreasinglineview.LinkedIncreasingLineView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var view : LinkedIncreasingLineView = LinkedIncreasingLineView.create(this)
        view.addCompletionListener {
            Toast.makeText(this, "advanced to stage ${it + 1}", Toast.LENGTH_SHORT).show()
        }
        fullScreen()
    }
}

fun MainActivity.fullScreen() {
    supportActionBar?.hide()
    window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
}
