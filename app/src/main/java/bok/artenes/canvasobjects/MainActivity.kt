package bok.artenes.canvasobjects

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val colorBackground by lazy {
        ResourcesCompat.getColor(resources, R.color.colorBackground, null)
    }

    private val colorRectangle by lazy {
        ResourcesCompat.getColor(resources, R.color.colorRectangle, null)
    }

    private val colorAccent by lazy {
        ResourcesCompat.getColor(resources, R.color.colorAccent, null)
    }

    private val paint by lazy {
        Paint().also {
            it.color = colorBackground
        }
    }

    private val paintText by lazy {
        Paint(Paint.UNDERLINE_TEXT_FLAG).also {
            it.color = ResourcesCompat.getColor(resources, R.color.colorPrimaryDark, null)
            it.textSize = 70f
        }
    }

    private lateinit var canvas: Canvas

    private val rect = Rect()

    private val bounds = Rect()

    private var offset: Int = OFFSET

    private lateinit var bitmap : Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imageView.setOnClickListener { drawSomething() }
    }

    private fun drawSomething() {
        val width = imageView.width
        val height = imageView.height
        val halfWidth = width / 2
        val halfHeight = height / 2

        when {
            offset == OFFSET -> {
                bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
                imageView.setImageBitmap(bitmap)
                canvas = Canvas(bitmap)
                canvas.drawColor(colorBackground)
                canvas.drawText(getString(R.string.keep_tapping), 100f, 100f, paintText)
                offset += OFFSET
            }
            offset < halfWidth && offset < halfHeight -> {
                paint.color = colorRectangle - MULTIPLIER * offset
                rect.set(offset, offset, width - offset, height - offset)
                canvas.drawRect(rect, paint)
                offset += OFFSET
            }
            else -> {
                paint.color = colorAccent
                canvas.drawCircle(halfWidth.toFloat(), halfHeight.toFloat(), halfWidth / 3f, paint)
                val text = getString(R.string.done)
                paintText.getTextBounds(text, 0, text.length, bounds)
                val x = halfWidth - bounds.centerX()
                val y = halfHeight - bounds.centerY()
                canvas.drawText(text, x.toFloat(), y.toFloat(), paintText)
            }
        }

        imageView.invalidate()
    }

    companion object {
        private const val OFFSET = 60
        private const val MULTIPLIER = 100
    }

}
