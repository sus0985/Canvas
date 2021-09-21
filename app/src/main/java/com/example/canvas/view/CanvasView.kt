package com.example.canvas.view

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import com.example.canvas.model.CanvasModel

class CanvasView(context: Context, attr: AttributeSet?) : View(context, attr) {
    var model: CanvasModel? = null

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas ?: return

        model?.drawShape(canvas)
    }
}
