package com.example.canvas.model

import android.graphics.PointF
import android.util.Size

abstract class Shape(
    val id: String,
    val shapeType: ShapeType,
    var point: PointF,
    var size: Size,
    var alpha: Int,
    var background: String
) {
    companion object {
        const val SHAPE_SIZE_WIDTH = 150
        const val SHAPE_SIZE_HEIGHT = 120
    }

    enum class ShapeType {
        RECT, PICTURE, TEXT
    }
}
