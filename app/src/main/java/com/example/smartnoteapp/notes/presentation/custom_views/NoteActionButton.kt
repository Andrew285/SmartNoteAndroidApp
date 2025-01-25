package com.example.smartnoteapp.notes.presentation.custom_views

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.smartnoteapp.R

class NoteActionButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private lateinit var imageView: ImageView
    private val textView: TextView

    var text: String
        get() {
            return textView.text.toString()
        }
        set(value) {
            textView.text = value
        }

    init {
        orientation = HORIZONTAL

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ActionButtonView)
        val icon = typedArray.getResourceId(R.styleable.ActionButtonView_actionIcon, R.drawable.gray_heart)
        val text = typedArray.getString(R.styleable.ActionButtonView_actionText) ?: "0"
        val textSize = typedArray.getDimension(R.styleable.ActionButtonView_actionTextSize, 14f)
        typedArray.recycle()

        setImage(icon)

        textView = TextView(context).apply {
            this.text = text
            this.textSize = textSize / resources.displayMetrics.scaledDensity
            layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        }

        addView(imageView)
        addView(textView)
    }

    fun setImage(icon: Int) {
        imageView = ImageView(context).apply {
            setImageResource(icon)
            layoutParams = LayoutParams(
                context.resources.getDimensionPixelSize(R.dimen.note_action_image_size),
                context.resources.getDimensionPixelSize(R.dimen.note_action_image_size)
            )
        }
    }
}
