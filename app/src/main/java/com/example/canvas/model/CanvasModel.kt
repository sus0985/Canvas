package com.example.canvas.model

import android.graphics.PointF
import android.util.Size

class CanvasModel {
    private val _shapeList = mutableListOf<Shape>()
    val shapeList: List<Shape> get() = _shapeList

    private var currentShape: Shape? = null

    fun addShape(shape: Shape) {
        _shapeList.add(shape)
    }

    fun isShape(x: Float, y: Float): Shape? {
        val list = shapeList.reversed()

        list.map { it.apply { isSelected = false } }.forEach { shape ->
            with(shape) {
                if (point.x <= x && point.y <= y && point.x + size.width >= x && point.y + size.height >= y) {
                    shape.isSelected = true
                    return shape
                }
            }
        }

        return null
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
        currentShape?.apply {
            if (size.width < 0) {
                point.x = point.x + size.width
                size = Size(kotlin.math.abs(size.width), size.height)
            }
            if (size.height < 0) {
                point.y = point.y + size.height
                size = Size(size.width, kotlin.math.abs(size.height))
            }
        }
        currentShape = null
    }
}
