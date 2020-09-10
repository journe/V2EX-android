package com.journey.android.v2ex.libs

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PointF
import android.graphics.PorterDuff.Mode.DST_OUT
import android.graphics.PorterDuffXfermode
import android.graphics.RectF
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.journey.android.v2ex.R

class CircleImageView @JvmOverloads constructor(
  context: Context,
  attr: AttributeSet? = null,
  defStyleAttr: Int = 0
) : androidx.appcompat.widget.AppCompatImageView(context, attr, defStyleAttr) {

  private var radius = 0f
  private val centerPoint = PointF()
  private val clearPath = Path()

  private val clearPaint = Paint().apply {
    isAntiAlias = true
    isDither = true
    style = Paint.Style.FILL
    xfermode = PorterDuffXfermode(DST_OUT)
  }

  private val borderPaint = Paint().apply {
    isAntiAlias = true
    isDither = true
    style = Paint.Style.STROKE
  }

  init {
    val typedArray = context.obtainStyledAttributes(
        attr, R.styleable.CircleImageView,
        defStyleAttr, 0
    )

    if (null != typedArray) {

      val color = typedArray.getColor(
          R.styleable.CircleImageView_borderColor,
          ContextCompat.getColor(context, R.color.white)
      )
      val width = typedArray.getDimension(R.styleable.CircleImageView_borderWidth, 0f)

      borderPaint.color = color
      borderPaint.strokeWidth = width

      typedArray.recycle()
    }
  }

  @SuppressLint("DrawAllocation")
  override fun onMeasure(
    widthMeasureSpec: Int,
    heightMeasureSpec: Int
  ) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec)

    val widthF = measuredWidth.toFloat()
    val heightF = measuredHeight.toFloat()
    val centerX = widthF / 2
    val centerY = heightF / 2
    centerPoint.x = centerX
    centerPoint.y = centerY
    radius = Math.min(centerX, centerY)

    clearPath.reset()
    clearPath.moveTo(0f, 0f)
    clearPath.lineTo(centerX, 0f)
    clearPath.addArc(RectF(0f, 0f, widthF, heightF), -180f, 90f)
    clearPath.lineTo(0f, 0f)

    clearPath.moveTo(widthF, 0f)
    clearPath.lineTo(centerX, 0f)
    clearPath.addArc(RectF(0f, 0f, widthF, heightF), -90f, 90f)
    clearPath.lineTo(widthF, 0f)

    clearPath.moveTo(0f, heightF)
    clearPath.lineTo(0f, centerY)
    clearPath.addArc(RectF(0f, 0f, widthF, heightF), 180f, -90f)
    clearPath.lineTo(0f, heightF)

    clearPath.moveTo(widthF, heightF)
    clearPath.lineTo(widthF, centerY)
    clearPath.addArc(RectF(0f, 0f, widthF, heightF), 0f, 90f)
    clearPath.lineTo(widthF, heightF)
  }

  override fun onDraw(canvas: Canvas?) {

    if (null != canvas) {
      val layerId = canvas.saveLayer(
          0f, 0f, width.toFloat(),
          height.toFloat(), null, Canvas.ALL_SAVE_FLAG
      )
      val drawable = drawable
      if (drawable is BitmapDrawable) {
        val bitmap = drawable.bitmap
        if (null != bitmap && !bitmap.isRecycled) {
          super.onDraw(canvas)
        }
      } else {
        super.onDraw(canvas)
      }

      canvas.drawPath(clearPath, clearPaint)
      canvas.drawCircle(
          centerPoint.x, centerPoint.y, radius - borderPaint.strokeWidth / 2, borderPaint
      )

      canvas.restoreToCount(layerId)

    }
  }

}