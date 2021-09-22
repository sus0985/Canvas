package com.example.canvas.view

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View

class CanvasView(context: Context, attr: AttributeSet?) : View(context, attr) {
    private lateinit var canvasNavigator: CanvasNavigator

    fun setCanvasNavigator(navigator: (canvas: Canvas) -> Unit) {
        canvasNavigator = object : CanvasNavigator {
            override fun updateCanvas(canvas: Canvas) {
                navigator(canvas)
            }
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas ?: return

        canvasNavigator.updateCanvas(canvas)
    }
}
