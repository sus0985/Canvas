package com.example.canvas.model

import android.graphics.PointF
import android.util.Size
import com.example.canvas.util.RandomUtil.getRandomColor
import com.example.canvas.util.RandomUtil.getRandomID

class Rectangle(
    id: String,
    shapeType: ShapeType,
    point: PointF,
    size: Size,
    alpha: Int,
    background: String
) : Shape(id, shapeType, point, size, alpha, background) {

    companion object RectangleFactory : ShapeFactory() {
        override fun newInstance(width: Int, height: Int): Shape =
            Rectangle(
                getRandomID(),
                ShapeType.RECT,
                PointF((0..width).random().toFloat(), (0..height).random().toFloat()),
                Size(SHAPE_SIZE_WIDTH, SHAPE_SIZE_HEIGHT),
                (1..10).random(),
                getRandomColor()
            )
    }
}
