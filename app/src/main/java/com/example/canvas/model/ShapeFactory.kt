package com.example.canvas.model

import android.graphics.PointF
import android.util.Size
import com.example.canvas.util.RandomUtil

object ShapeFactory {
    private fun createRectangle(start: PointF): Rectangle = Rectangle(
        RandomUtil.getRandomID(),
        Shape.ShapeType.RECT,
        start,
        Size(0, 0),
        (1..10).random(),
        RandomUtil.getRandomColor()
    )

    private fun createPicture(width: Int, height: Int): Picture = Picture(
        RandomUtil.getRandomID(),
        Shape.ShapeType.PICTURE,
        PointF((0..width).random().toFloat(), (0..height).random().toFloat()),
        Size(Shape.SHAPE_SIZE_WIDTH, Shape.SHAPE_SIZE_HEIGHT),
        (1..10).random(),
        RandomUtil.getRandomColor(),
        null
    )

    private fun createText(width: Int, height: Int): Text = Text(
        RandomUtil.getRandomID(),
        Shape.ShapeType.TEXT,
        PointF((0..width).random().toFloat(), (0..height).random().toFloat()),
        Size(Shape.SHAPE_SIZE_WIDTH, Shape.SHAPE_SIZE_HEIGHT),
        (1..10).random(),
        RandomUtil.getRandomColor(),
        "TEXT"
    )

    fun createShape(shapeType: Shape.ShapeType, width: Int, height: Int, start: PointF): Shape =
        when (shapeType) {
            Shape.ShapeType.RECT -> createRectangle(start)
            Shape.ShapeType.PICTURE -> createPicture(width, height)
            Shape.ShapeType.TEXT -> createText(width, height)
        }
}
