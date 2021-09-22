package com.example.canvas.controller

import android.annotation.SuppressLint
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.canvas.R
import com.example.canvas.databinding.ActivityMainBinding
import com.example.canvas.model.CanvasModel
import com.example.canvas.model.Shape
import com.example.canvas.view.CanvasNavigator

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val canvasModel = CanvasModel()

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.main = this

        binding.canvas.setOnTouchListener { _, event ->
            Log.d("MainActivity", canvasModel.isShape(event.x, event.y).toString())
            drawShape(event)
            true
        }

        binding.canvas.setCanvasNavigator(object : CanvasNavigator {
            override fun updateCanvas(canvas: Canvas) {
                canvasModel.shapeList.forEach { shape ->
                    val paint = Paint().apply {
                        color = Integer.parseInt(shape.background, 16)
                        alpha = shape.alpha * 25 + 5
                    }
                    canvas.drawRect(
                        shape.point.x,
                        shape.point.y,
                        shape.point.x + shape.size.width,
                        shape.point.y + shape.size.height,
                        paint
                    )
                }
            }
        })
    }

    private fun drawShape(event: MotionEvent?) {
        event ?: return

        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                canvasModel.startDrawing(event.x, event.y)
            }
            MotionEvent.ACTION_MOVE -> {
                canvasModel.setCurrentPoint(event.x, event.y)
                binding.canvas.invalidate()
            }
            MotionEvent.ACTION_UP -> {
                canvasModel.endDrawing()
                binding.canvas.invalidate()
            }
        }
    }


}
