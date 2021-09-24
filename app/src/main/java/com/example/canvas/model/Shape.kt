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
    enum class ShapeType {
        RECT, PICTURE, PEN
    }
}
