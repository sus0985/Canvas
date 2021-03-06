package com.example.canvas.model

import android.graphics.PointF
import android.util.Size

data class Pen(
    override val id: String,
    override val shapeType: ShapeType,
    override var point: PointF,
    override var size: Size,
    override var alpha: Int,
    override var background: String,
    var pointList: MutableList<PointF>,
    var startPoint: PointF
) : Shape(id, shapeType, point, size, alpha, background, false)
