package lv.rigadevday.android.utils.view

import android.content.Context
import android.graphics.*
import android.graphics.Shader.TileMode.CLAMP
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import lv.rigadevday.android.utils.logE


class CircularImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = -1
) : android.support.v7.widget.AppCompatImageView(context, attrs, defStyleAttr) {

    private var canvasSize: Int = 0

    private val paint: Paint = Paint()
    private var image: Bitmap? = null
    private var actualDrawable: Drawable? = null

    init {
        paint.isAntiAlias = true
    }

    override fun getScaleType(): ImageView.ScaleType {
        return ImageView.ScaleType.CENTER_CROP
    }

    override fun setScaleType(scaleType: ImageView.ScaleType) {
        if (scaleType != ImageView.ScaleType.CENTER_CROP) {
            throw IllegalArgumentException("Only ScaleType.CENTER_CROP is supported.")
        }
    }

    public override fun onDraw(canvas: Canvas) {
        loadBitmap()
        if (image == null) return

        if (!isInEditMode) {
            canvasSize = canvas.width
            if (canvas.height < canvasSize) {
                canvasSize = canvas.height
            }
        }

        val circleCenter = canvasSize / 2
        canvas.drawCircle(circleCenter.toFloat(), circleCenter.toFloat(), circleCenter.toFloat(), paint!!)
    }

    private fun loadBitmap() {
        if (drawable === actualDrawable)
            return

        this.actualDrawable = drawable
        this.image = drawableToBitmap(drawable)
        updateShader()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        canvasSize = w
        if (h < canvasSize) canvasSize = h
        if (image != null) updateShader()
    }


    private fun updateShader() {
        image?.let {
            image = cropBitmap(it)
            paint.shader = BitmapShader(it, CLAMP, CLAMP).apply {
                setLocalMatrix(Matrix().apply {
                    setScale(canvasSize.toFloat() / it.width.toFloat(), canvasSize.toFloat() / it.height.toFloat())
                })
            }
        }
    }

    private fun cropBitmap(bitmap: Bitmap) =
        if (bitmap.width >= bitmap.height) {
            Bitmap.createBitmap(bitmap, bitmap.width / 2 - bitmap.height / 2, 0, bitmap.height, bitmap.height)
        } else {
            Bitmap.createBitmap(bitmap, 0, bitmap.height / 2 - bitmap.width / 2, bitmap.width, bitmap.width)
        }

    private fun drawableToBitmap(drawable: Drawable?): Bitmap? {
        if (drawable == null) {
            return null
        } else if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }

        val intrinsicWidth = drawable.intrinsicWidth
        val intrinsicHeight = drawable.intrinsicHeight

        if (intrinsicWidth <= 0 || intrinsicHeight <= 0) return null

        try {
            // Create Bitmap object out of the drawable
            val bitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            return bitmap
        } catch (e: OutOfMemoryError) {
            "OutOfMemoryError while generating bitmap!".logE()
            return null
        }

    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec))
    }

    private fun measureWidth(measureSpec: Int): Int {
        val specMode = View.MeasureSpec.getMode(measureSpec)
        val specSize = View.MeasureSpec.getSize(measureSpec)

        return when (specMode) {
            View.MeasureSpec.EXACTLY -> specSize
            View.MeasureSpec.AT_MOST -> specSize
            else -> canvasSize
        }
    }

    private fun measureHeight(measureSpecHeight: Int): Int {
        val specMode = View.MeasureSpec.getMode(measureSpecHeight)
        val specSize = View.MeasureSpec.getSize(measureSpecHeight)

        return 2 + when (specMode) {
            View.MeasureSpec.EXACTLY -> specSize
            View.MeasureSpec.AT_MOST -> specSize
            else -> canvasSize
        }
    }

}
