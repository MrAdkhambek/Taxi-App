package me.adkhambek.taxi.view

import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import me.adkhambek.taxi.R


/**
 **  Created by MrAdkhambek on 18/01/21.
 **  CoinGoing
 **/

class RoundedCornerLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val path = Path()
    private val rectF = RectF(0F, 0F, 0F, 0F)
    private val paint: Paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
    }

    private val corners = floatArrayOf(
        0f, 0f,     // Top left radius in px
        0f, 0f,     // Top right radius in px
        0f, 0f,     // Bottom right radius in px
        0f, 0f      // Bottom left radius in px
    )

    init {

        val typedArray: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundedCornerLayout, defStyleAttr, defStyleRes)

        try {
            paint.color = typedArray.getColor(R.styleable.RoundedCornerLayout_android_background, Color.TRANSPARENT)
            val radiusAll = typedArray.getDimension(R.styleable.RoundedCornerLayout_radius_all, 0f)

            val topLeftRadius = typedArray.getDimension(R.styleable.RoundedCornerLayout_radius_top_left, radiusAll)
            corners[0] = topLeftRadius
            corners[1] = topLeftRadius

            val topRightRadius = typedArray.getDimension(R.styleable.RoundedCornerLayout_radius_top_right, radiusAll)
            corners[2] = topRightRadius
            corners[3] = topRightRadius

            val bottomLeftRadius = typedArray.getDimension(R.styleable.RoundedCornerLayout_radius_bottom_left, radiusAll)
            corners[4] = bottomLeftRadius
            corners[5] = bottomLeftRadius

            val bottomRightRadius = typedArray.getDimension(R.styleable.RoundedCornerLayout_radius_bottom_right, radiusAll)
            corners[6] = bottomRightRadius
            corners[7] = bottomRightRadius

        } finally {
            typedArray.recycle()
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        rectF.set(0F, 0F, w.toFloat(), h.toFloat())
        resetPath()
    }

    override fun draw(canvas: Canvas) {
        canvas.clipPath(path)
        super.draw(canvas)
    }

    override fun dispatchDraw(canvas: Canvas?) {
        canvas?.clipPath(path)
        super.dispatchDraw(canvas)
    }

    private fun resetPath() {
        path.reset()
        path.addRoundRect(rectF, corners, Path.Direction.CCW)
        path.close()
    }
}