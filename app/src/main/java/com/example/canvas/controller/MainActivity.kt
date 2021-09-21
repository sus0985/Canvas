package com.example.canvas.controller

import android.annotation.SuppressLint
import android.graphics.Canvas
import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.canvas.R
import com.example.canvas.databinding.ActivityMainBinding
import com.example.canvas.model.CanvasModel
import com.example.canvas.model.Shape

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val canvasModel = CanvasModel()

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.main = this
        binding.canvas.model = canvasModel

        binding.canvas.setOnTouchListener { _, event ->
            drawShape(event)
            true
        }
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
