package com.example.canvas.model

import android.graphics.PointF
import android.util.Size

open class Shape(
    open val id: String,
    open val shapeType: ShapeType,
    open var point: PointF,
    open var size: Size,
    open var alpha: Int,
    open var background: String,
    var isSelected: Boolean
) {
    companion object {
        const val SHAPE_SIZE_WIDTH = 150
        const val SHAPE_SIZE_HEIGHT = 120
    }

    enum class ShapeType {
        RECT, PICTURE, TEXT
    }
}
