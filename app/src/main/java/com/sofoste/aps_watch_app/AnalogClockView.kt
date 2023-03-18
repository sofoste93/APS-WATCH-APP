package com.sofoste.aps_watch_app

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import java.util.*
import kotlin.math.cos
import kotlin.math.sin

class AnalogClockView : View {
    private var mWidth: Int = 0
    private var mHeight: Int = 0
    private var mRadius: Float = 1f
    private var mCenterX: Float = 0f
    private var mCenterY: Float = 0f

    private val mPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mHourHandPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mMinuteHandPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mSecondHandPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var mHourHandLength: Float = 0f
    private var mMinuteHandLength: Float = 0f
    private var mSecondHandLength: Float = 0f

    private var mHourHandThickness: Float = 0f
    private var mMinuteHandThickness: Float = 0f
    private var mSecondHandThickness: Float = 0f

    private var mHour: Int = 0
    private var mMinute: Int = 0
    private var mSecond: Int = 0

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    init {
        // Set default values for paint objects
        mPaint.color = Color.WHITE
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = 2f

        mHourHandPaint.color = Color.BLACK
        mHourHandPaint.style = Paint.Style.STROKE
        mHourHandPaint.strokeCap = Paint.Cap.ROUND

        mMinuteHandPaint.color = Color.RED
        mMinuteHandPaint.style = Paint.Style.STROKE
        mMinuteHandPaint.strokeCap = Paint.Cap.ROUND

        mSecondHandPaint.color = Color.DKGRAY
        mSecondHandPaint.style = Paint.Style.STROKE
        mSecondHandPaint.strokeCap = Paint.Cap.ROUND

        // Set default values for hand lengths and thicknesses
        mHourHandLength = 0.5f
        mMinuteHandLength = 0.7f
        mSecondHandLength = 0.9f

        mHourHandThickness = 5f
        mMinuteHandThickness = 3f
        mSecondHandThickness = 2f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Draw clock face
        mPaint.color = Color.WHITE
        mPaint.style = Paint.Style.FILL
        canvas.drawCircle(mCenterX, mCenterY, mRadius, mPaint)

        // Draw the minute markings within the circle
        mPaint.color = Color.WHITE
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = mRadius * 0.03f
        for (i in 0..11) {
            val angle = i * Math.PI / 6
            val startX = mCenterX + (mRadius - 20) * sin(angle).toFloat()
            val startY = mCenterY - (mRadius - 20) * cos(angle).toFloat()
            val stopX = mCenterX + (mRadius - 5) * sin(angle).toFloat()
            val stopY = mCenterY - (mRadius - 5) * cos(angle).toFloat()
            canvas.drawLine(startX, startY, stopX, stopY, mPaint)
        }
// Draw the main numbers within the circle
        mPaint.color = Color.BLACK
        mPaint.style = Paint.Style.FILL
        mPaint.textSize = mRadius * 0.2f
        mPaint.textAlign = Paint.Align.CENTER
        canvas.drawText("12", mCenterX, mCenterY - mRadius * 0.83f, mPaint)
        canvas.drawText("1", mCenterX + (mRadius - 60) * sin(Math.PI / 6).toFloat(),
            mCenterY - (mRadius - 100) * cos(Math.PI / 6).toFloat(), mPaint)
        canvas.drawText("2", mCenterX + (mRadius - 60) * sin(Math.PI / 3).toFloat(),
            mCenterY - (mRadius - 150) * cos(Math.PI / 3).toFloat(), mPaint)
        canvas.drawText("3", mCenterX + mRadius * 0.86f, mCenterY, mPaint)
        canvas.drawText("4", mCenterX + (mRadius - 50) * sin(2 * Math.PI / 3).toFloat(),
            mCenterY - (mRadius + 50) * cos(2 * Math.PI / 3).toFloat(), mPaint)
        canvas.drawText("5", mCenterX + (mRadius - 20) * sin(5 * Math.PI / 6).toFloat(),
            mCenterY - (mRadius - 20) * cos(5 * Math.PI / 6).toFloat(), mPaint)
        canvas.drawText("6", mCenterX, mCenterY + mRadius * 0.97f, mPaint)
        canvas.drawText("7", mCenterX + (mRadius - 20) * sin(7 * Math.PI / 6).toFloat(),
            mCenterY - (mRadius - 20) * cos(7 * Math.PI / 6).toFloat(), mPaint)
        canvas.drawText("10", mCenterX + (mRadius - 80) * sin(2 * Math.PI / 3 + Math.PI).toFloat(),
            mCenterY - (mRadius - 150) * cos(2 * Math.PI / 3 + Math.PI).toFloat(), mPaint)
        canvas.drawText("9", mCenterX - mRadius * 0.86f, mCenterY, mPaint)
        canvas.drawText("8", mCenterX + (mRadius + 10) * sin(-16 * Math.PI / 7).toFloat(),
            mCenterY + (mRadius - 80) * cos(-16 * Math.PI / 7).toFloat(), mPaint)
        canvas.drawText("11", mCenterX + (mRadius - 70) * sin(11 * Math.PI / 6).toFloat(),
            mCenterY - (mRadius - 120) * cos(11 * Math.PI / 6).toFloat(), mPaint)


        // Draw the clock face border
        mPaint.color = Color.BLACK
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = mRadius * 0.02f
        canvas.drawCircle(mCenterX, mCenterY, mRadius, mPaint)

        // Draw minute markings
        mPaint.color = Color.BLACK
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = mRadius * 0.01f
        for (i in 0..59) {
            if (i % 5 == 0) {
                continue
            }
            val angle = i * Math.PI / 30
            val startX = mCenterX + (mRadius - 50) * sin(angle).toFloat()
            val startY = mCenterY - (mRadius - 50) * cos(angle).toFloat()
            val stopX = mCenterX + mRadius * sin(angle).toFloat()
            val stopY = mCenterY - mRadius * cos(angle).toFloat()
            canvas.drawLine(startX, startY, stopX, stopY, mPaint)
        }

        // Draw hour hand
        drawHourHand(canvas)

        // Draw minute hand
        drawMinuteHand(canvas)

        // Draw second hand
        drawSecondHand(canvas)

        // Update the time and invalidate the view to redraw
        updateTime(
            Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
            Calendar.getInstance().get(Calendar.MINUTE),
            Calendar.getInstance().get(Calendar.SECOND)
        )
        invalidate()
    }



    private fun drawHourHand(canvas: Canvas) {
        val hourAngle = (mHour + mMinute / 60.0) * 30.0
        val hourX = mCenterX + 0.7f * mRadius * sin(Math.toRadians(hourAngle)).toFloat()
        val hourY = mCenterY - 0.7f * mRadius * cos(Math.toRadians(hourAngle)).toFloat()

        mHourHandPaint.strokeWidth = mHourHandThickness
        canvas.drawLine(mCenterX, mCenterY, hourX, hourY, mHourHandPaint)
    }

    private fun drawMinuteHand(canvas: Canvas) {
        val minuteAngle = (mMinute + mSecond / 60.0) * 6.0
        val minuteX = mCenterX + 0.9f + 0.9 * mRadius * sin(Math.toRadians(minuteAngle)).toFloat()
        val minuteY = mCenterY - 0.9f * mRadius * cos(Math.toRadians(minuteAngle)).toFloat()

        mMinuteHandPaint.strokeWidth = mMinuteHandThickness
        canvas.drawLine(mCenterX, mCenterY, minuteX.toFloat(), minuteY, mMinuteHandPaint)
    }

    private fun drawSecondHand(canvas: Canvas) {
        val secondAngle = mSecond * 6.0
        val secondX = mCenterX + 0.9f * mRadius * sin(Math.toRadians(secondAngle)).toFloat()
        val secondY = mCenterY - 0.9f * mRadius * cos(Math.toRadians(secondAngle)).toFloat()

        mSecondHandPaint.strokeWidth = mSecondHandThickness
        canvas.drawLine(mCenterX, mCenterY, secondX, secondY, mSecondHandPaint)
    }

    fun updateTime(get: Int, get1: Int, get2: Int) {
        // Get current time
        val calendar = Calendar.getInstance()
        mHour = calendar.get(Calendar.HOUR)
        mMinute = calendar.get(Calendar.MINUTE)
        mSecond = calendar.get(Calendar.SECOND)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        // Get the available width and height for the view
        val availableWidth = MeasureSpec.getSize(widthMeasureSpec)
        val availableHeight = MeasureSpec.getSize(heightMeasureSpec)

        // Set the view size to be a square that fits within the available space
        val minSize = availableWidth.coerceAtMost(availableHeight) //Math.min
        setMeasuredDimension(minSize, minSize)

        // Calculate the center point and radius of the view
        mWidth = measuredWidth
        mHeight = measuredHeight
        mRadius = mWidth.coerceAtMost(mHeight) / 2f //Math.min
        mCenterX = mWidth / 2f
        mCenterY = mHeight / 2f

        // Calculate the length of the clock hands based on the view size
        mHourHandLength = mRadius * 0.5f
        mMinuteHandLength = mRadius * 0.7f
        mSecondHandLength = mRadius * 0.9f

        // Set the thickness of the clock hands based on the view size
        mHourHandThickness = mRadius * 0.05f
        mMinuteHandThickness = mRadius * 0.03f
        mSecondHandThickness = mRadius * 0.02f
    }
}
