package com.app.calculator_betona.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.app.calculator_betona.R

class TopicItem(context: Context, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {
    constructor(context: Context) : this(context, null)

    private val textView: TextView
    private val imageView: ImageView

    init {
        LayoutInflater.from(context).inflate(R.layout.topic_item, this, true)
        textView = findViewById(R.id.textView)
        imageView = findViewById(R.id.imageView)

        attrs?.let {
            context.obtainStyledAttributes(attrs, R.styleable.TopicItem).apply {
                getString(R.styleable.TopicItem_text)?.let {
                    textView.text = it
                }
                getDrawable(R.styleable.TopicItem_drawable)?.let {
                    imageView.setImageDrawable(it)
                }
                recycle()
            }
        }
    }

    fun setText(string: String) {
        textView.text = string
    }

    fun setDrawable(drawable: Drawable) {
        imageView.setImageDrawable(drawable)
    }
}
