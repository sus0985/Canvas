package com.example.canvas.model

class Canvas {
    private val shapeList = mutableListOf<Shape>()

    fun addShape(shape: Shape) {
        shapeList.add(shape)
    }

    fun isShape(x: Float, y: Float): Boolean {
        // TODO 좌표값 전달 받은 후 도형인지 확인
    }
}
