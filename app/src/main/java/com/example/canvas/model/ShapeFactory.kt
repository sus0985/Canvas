package com.example.canvas.model

import android.graphics.PointF
import android.net.Uri
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

    private fun createPicture(start: PointF, uri: Uri?): Picture = Picture(
        RandomUtil.getRandomID(),
        Shape.ShapeType.PICTURE,
        start,
        Size(0, 0),
        (1..10).random(),
        RandomUtil.getRandomColor(),
        uri
    )

    private fun createPen(start: PointF): Pen = Pen(
        RandomUtil.getRandomID(),
        Shape.ShapeType.PEN,
        start,
        Size(0, 0),
        (1..10).random(),
        RandomUtil.getRandomColor(),
        mutableListOf()
    )

    fun createShape(
        shapeType: Shape.ShapeType,
        start: PointF,
        uri: Uri? = null
    ): Shape =
        when (shapeType) {
            Shape.ShapeType.RECT -> createRectangle(start)
            Shape.ShapeType.PICTURE -> createPicture(start, uri)
            Shape.ShapeType.PEN -> createPen(start)
        }
}
