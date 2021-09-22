package com.example.canvas.controller

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.databinding.DataBindingUtil
import com.example.canvas.R
import com.example.canvas.databinding.ActivityMainBinding
import com.example.canvas.model.CanvasModel
import com.example.canvas.model.Shape
import com.example.canvas.view.CanvasNavigator

class MainActivity : AppCompatActivity(), CanvasNavigator {

    private lateinit var binding: ActivityMainBinding
    private val canvasModel = CanvasModel()
    private var isButtonOpen: Boolean = false
    private var drawingOption: Shape.ShapeType? = null

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.main = this

        binding.canvas.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN && drawingOption == null) {
                canvasModel.isShape(event.x, event.y)
                binding.canvas.invalidate()
            }

            drawShape(event)
            true
        }

        binding.canvas.setCanvasNavigator(this)
        binding.canvasButton.setOnClickListener {
            toggleButton()
        }
        binding.rectButton.setOnClickListener {
            toggleButton()
            drawingOption = Shape.ShapeType.RECT
        }
        binding.cancelButton.setOnClickListener {
            toggleButton()
            drawingOption = null
        }
    }

    private fun drawShape(event: MotionEvent?) {
        event ?: return
        drawingOption ?: return

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

            if (shape.isSelected) {
                val stroke = Paint().apply {
                    style = Paint.Style.STROKE
                    color = Color.BLACK
                    strokeWidth = 5f
                }
                canvas.drawRect(
                    shape.point.x,
                    shape.point.y,
                    shape.point.x + shape.size.width,
                    shape.point.y + shape.size.height,
                    stroke
                )
            }
        }
    }

    private fun toggleButton() {
        val openAnimation = AnimationUtils.loadAnimation(this, R.anim.floating_action_button_open)
        val closeAnimation = AnimationUtils.loadAnimation(this, R.anim.floating_action_button_close)
        with(binding) {
            if (isButtonOpen) {
                canvasButton.setImageResource(R.drawable.ic_plus)
                rectButton.startAnimation(closeAnimation)
                cancelButton.startAnimation(closeAnimation)
                rectButton.isClickable = false
                cancelButton.isClickable = false
            } else {
                canvasButton.setImageResource(R.drawable.ic_close)
                rectButton.startAnimation(openAnimation)
                cancelButton.startAnimation(openAnimation)
                rectButton.isClickable = true
                cancelButton.isClickable = true
            }
        }
        isButtonOpen = !isButtonOpen
    }

}
