package com.example.canvas.controller

import android.annotation.SuppressLint
import android.graphics.*
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.canvas.R
import com.example.canvas.databinding.ActivityMainBinding
import com.example.canvas.model.*
import com.example.canvas.model.Picture
import com.example.canvas.view.CanvasNavigator

class MainActivity : AppCompatActivity(), CanvasNavigator {

    private lateinit var binding: ActivityMainBinding
    private lateinit var getContent: ActivityResultLauncher<String>

    private val canvasModel = CanvasModel()
    private var isButtonOpen: Boolean = false
    private var drawingOption: Shape.ShapeType? = null
    private var uri: Uri? = null

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.main = this

        getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { img: Uri? ->
            img?.let {
                uri = img
                drawingOption = Shape.ShapeType.PICTURE
            } ?: run { drawingOption = null }
        }

        binding.canvas.setOnTouchListener { v, event ->

            if (event.action == MotionEvent.ACTION_DOWN && drawingOption == null) {
                canvasModel.startMove(canvasModel.isShape(event.x, event.y))
                binding.canvas.invalidate()
            }

            if (event.pointerCount == 2) {
                when (event.action) {
                    MotionEvent.ACTION_MOVE -> {
                        canvasModel.setMovingPoint(event.x, event.y)
                    }
                    MotionEvent.ACTION_POINTER_UP -> {
                        canvasModel.endMove()
                    }
                }
            }

            drawShape(event)
            true
        }

        binding.canvas.setCanvasNavigator(this)

        canvasModel.currentDrawingShape.observe(this) {
            binding.canvas.invalidate()
        }

        canvasModel.movingShape.observe(this) {
            binding.canvas.invalidate()
        }
    }


    private fun drawShape(event: MotionEvent?) {
        event ?: return
        drawingOption ?: return

        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                drawingOption?.let {
                    canvasModel.startDrawing(event.x, event.y, it, uri)
                }
            }
            MotionEvent.ACTION_MOVE -> {
                canvasModel.setCurrentPoint(event.x, event.y)
            }
            MotionEvent.ACTION_UP -> {
                canvasModel.endDrawing()
            }
        }
    }

    override fun updateCanvas(canvas: Canvas) {
        canvasModel.shapeList.forEach { shape ->
            val paint = Paint().apply {
                color = Integer.parseInt(shape.background, 16)
                alpha = shape.alpha * 25 + 5
                strokeWidth = 5f
            }
            when (shape) {
                is Rectangle -> canvas.drawRect(
                    shape.point.x,
                    shape.point.y,
                    shape.point.x + shape.size.width,
                    shape.point.y + shape.size.height,
                    paint
                )
                is Picture -> {
                    val bitmap = getBitmapFromUri() ?: return@forEach
                    val drawing = Bitmap.createScaledBitmap(
                        bitmap,
                        shape.size.width + 1,
                        shape.size.height + 1,
                        false
                    )
                    canvas.drawBitmap(
                        drawing,
                        shape.point.x,
                        shape.point.y,
                        paint
                    )
                }
                is Pen -> {
                    var prev = shape.startPoint
                    shape.pointList.forEach { point ->
                        canvas.drawLine(prev.x, prev.y, point.x, point.y, paint)
                        prev = point
                    }
                }
            }

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

    fun onFloatingActionButtonClick(view: View) {
        val openAnimation = AnimationUtils.loadAnimation(this, R.anim.floating_action_button_open)
        val closeAnimation = AnimationUtils.loadAnimation(this, R.anim.floating_action_button_close)

        val setAnimation: (Animation) -> Unit = { anim ->
            with(binding) {
                if (isButtonOpen) canvasButton.setImageResource(R.drawable.ic_plus)
                else canvasButton.setImageResource(R.drawable.ic_close)
                rectButton.startAnimation(anim)
                pictureButton.startAnimation(anim)
                penButton.startAnimation(anim)
                cancelButton.startAnimation(anim)
                rectButton.isClickable = !isButtonOpen
                pictureButton.isClickable = !isButtonOpen
                penButton.isClickable = !isButtonOpen
                cancelButton.isClickable = !isButtonOpen
            }
        }

        when (view.id) {
            R.id.cancelButton -> drawingOption = null
            R.id.rectButton -> drawingOption = Shape.ShapeType.RECT
            R.id.pictureButton -> getContent.launch("image/*")
            R.id.penButton -> drawingOption = Shape.ShapeType.PEN
        }

        if (isButtonOpen) setAnimation(closeAnimation)
        else setAnimation(openAnimation)

        isButtonOpen = !isButtonOpen

        drawingOption?.let {
            Toast.makeText(this, it.name, Toast.LENGTH_LONG).show()
        }
    }

    private fun getBitmapFromUri(): Bitmap? {
        return uri?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val source = ImageDecoder.createSource(this.contentResolver, it)
                ImageDecoder.decodeBitmap(source)
            } else {
                MediaStore.Images.Media.getBitmap(this.contentResolver, it)
            }
        }
    }
}
