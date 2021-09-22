package com.example.canvas.model

import android.graphics.PointF
import android.net.Uri
import android.util.Size

data class Picture(
    override val id: String,
    override val shapeType: ShapeType,
    override var point: PointF,
    override var size: Size,
    override var alpha: Int,
    override var background: String,
    val uri: Uri?
) : Shape(id, shapeType, point, size, alpha, background, false)
