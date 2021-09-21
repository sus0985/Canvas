package com.example.canvas.model

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.util.Size

class CanvasModel {
    private val shapeList = mutableListOf<Shape>()
    private var currentShape: Shape? = null

    fun addShape(shape: Shape) {
        shapeList.add(shape)
    }

    fun isShape(x: Float, y: Float): Boolean {
        // TODO 좌표값 전달 받은 후 도형 안을 클릭했는지 확인
        return false
    }

    fun startDrawing(x: Float, y: Float) {
        currentShape = ShapeFactory.createShape(Shape.ShapeType.RECT, 0, 0, PointF(x, y)).also {
            addShape(it)
        }
    }

    fun setCurrentPoint(x: Float, y: Float) {
        currentShape?.let {
            it.size = Size((x - it.point.x).toInt(), (y - it.point.y).toInt())
        }
    }

    fun endDrawing() {
        currentShape = null
    }

    fun drawShape(canvas: Canvas) {
        shapeList.forEach { shape ->
            val paint = Paint().apply {
                color = Integer.parseInt(shape.background, 16)
                alpha = shape.alpha * 25 + 5
            }
            canvas.drawRect(
                shape.point.x,
                shape.point.y,
                shape.point.x + shape.size.width,
                shape.point.y + shape.size.height,
                paint
            )
        }
    }
}
