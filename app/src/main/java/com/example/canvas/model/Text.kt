package com.example.canvas.model

import android.graphics.PointF
import android.util.Size

data class Text(
    override val id: String,
    override val shapeType: ShapeType,
    override var point: PointF,
    override var size: Size,
    override var alpha: Int,
    override var background: String,
    val text: String
) : Shape(id, shapeType, point, size, alpha, background)
