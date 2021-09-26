package com.example.canvas.model

import android.graphics.PointF
import android.net.Uri
import android.util.Size
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class CanvasModel {
    private val _shapeList = mutableListOf<Shape>()
    val shapeList: List<Shape> get() = _shapeList

    private val _currentDrawingShape = MutableLiveData<Shape?>()
    val currentDrawingShape: LiveData<Shape?> get() = _currentDrawingShape

    private val _movingShape = MutableLiveData<Shape?>()
    val movingShape: LiveData<Shape?> get() = _movingShape

    fun addShape(shape: Shape) {
        _shapeList.add(shape)
    }

    fun isShape(x: Float, y: Float): Shape? {
        val list = shapeList.reversed()

        list.map { it.apply { isSelected = false } }.forEach { shape ->
            with(shape) {
                if (point.x <= x && point.y <= y && point.x + size.width >= x && point.y + size.height >= y) {
                    shape.isSelected = true
                    return shape
                }
            }
        }

        return null
    }

    fun startDrawing(x: Float, y: Float, shapeType: Shape.ShapeType, uri: Uri? = null) {
        _currentDrawingShape.value =
            ShapeFactory.createShape(shapeType, PointF(x, y), uri).also {
                addShape(it)
            }
    }

    fun setCurrentPoint(x: Float, y: Float) {
        _currentDrawingShape.value = _currentDrawingShape.value?.apply {
            size = Size((x - point.x).toInt(), (y - point.y).toInt())

            if (this is Pen) {
                this.pointList.add(PointF(x, y))
            }
        }
    }

    fun endDrawing() {
        _currentDrawingShape.value?.apply {
            when (this) {
                is Rectangle -> {
                    if (size.width < 0) {
                        point.x = point.x + size.width
                        size = Size(kotlin.math.abs(size.width), size.height)
                    }
                    if (size.height < 0) {
                        point.y = point.y + size.height
                        size = Size(size.width, kotlin.math.abs(size.height))
                    }
                }
                is Pen -> {
                    val left = pointList.minByOrNull { it.x }?.x ?: return
                    val top = pointList.minByOrNull { it.y }?.y ?: return
                    val width = pointList.maxByOrNull { it.x }?.let { it.x - left } ?: return
                    val height = pointList.maxByOrNull { it.y }?.let { it.y - top } ?: return

                    point = PointF(left, top)
                    size = Size(width.toInt(), height.toInt())
                }
            }
        }
        _currentDrawingShape.value = null
    }

    fun startMove(shape: Shape?) {
        _movingShape.value = shape
    }

    fun setMovingPoint(x: Float, y: Float) {
        _movingShape.value = _movingShape.value?.apply {
            if (this is Pen) {
                startPoint.x = startPoint.x + (x - startPoint.x) - size.width / 2
                startPoint.y = startPoint.y + (y - startPoint.y) - size.height / 2

                pointList.forEach {
                    it.x = it.x + (x - point.x) - size.width / 2
                    it.y = it.y + (y - point.y) - size.height / 2
                }
            }
            point.x = x - size.width / 2
            point.y = y - size.height / 2
        }
    }

    fun endMove() {
        _shapeList.find { _movingShape.value?.id == it.id }?.let {
            it.point = _movingShape.value?.point ?: return

            if (it is Pen) {
                it.startPoint = (_movingShape.value as Pen).startPoint
                it.pointList = (_movingShape.value as Pen).pointList
            }
        }

        _movingShape.value = null
    }
}
