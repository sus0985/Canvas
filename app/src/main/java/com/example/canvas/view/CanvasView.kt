package com.example.canvas.view

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View

class CanvasView(context: Context, attr: AttributeSet?) : View(context, attr) {
    private lateinit var canvasNavigator: CanvasNavigator

    fun setCanvasNavigator(canvasNavigator: CanvasNavigator) {
        this.canvasNavigator = canvasNavigator
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas ?: return

        canvasNavigator.updateCanvas(canvas)
    }
}
