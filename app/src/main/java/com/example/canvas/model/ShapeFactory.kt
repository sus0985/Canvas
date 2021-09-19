package com.example.canvas.model

abstract class ShapeFactory {
    abstract fun newInstance(width: Int, height: Int): Shape

    companion object {
        fun createShape(shapeFactory: ShapeFactory, width: Int, height: Int): Shape =
            shapeFactory.newInstance(width, height)
    }
}
