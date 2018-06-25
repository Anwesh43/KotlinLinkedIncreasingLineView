package com.anwesh.uiprojects.kotlinlinkedincreasinglineview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.anwesh.uiprojects.linkedincreasinglineview.LinkedIncreasingLineView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LinkedIncreasingLineView.create(this)
    }
}
