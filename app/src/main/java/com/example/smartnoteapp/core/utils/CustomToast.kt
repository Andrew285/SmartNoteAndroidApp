package com.example.smartnoteapp.core.utils

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.smartnoteapp.R

object CustomToast {
    @RequiresApi(Build.VERSION_CODES.Q)
    fun makeText(context: Context, text: String, type: ToastType) {
        val inflater = LayoutInflater.from(context)
        val layout: View = inflater.inflate(R.layout.custom_toast_layout, null)

        val backgroundColor = getBackgroundColor(context, type)
        val backgroundDrawable = GradientDrawable().apply {
            setColor(backgroundColor)
            cornerRadius = 80f // Adjust the corner radius as needed (16dp in this example)
            setPadding(25, 15, 25, 15)
        }
        layout.background = backgroundDrawable

        val toastImageView: ImageView = layout.findViewById(R.id.toastImageView)
        val toastTextView: TextView = layout.findViewById(R.id.toastTextView)
        toastTextView.setTextColor(context.resources.getColor(R.color.black))

        val imageId = getImageResource(type)
        toastImageView.setBackgroundResource(imageId)
        toastTextView.text = text

        val toast = Toast(context)
        toast.view = layout
        toast.duration = Toast.LENGTH_SHORT
        toast.show()
    }

    private fun getImageResource(type: ToastType): Int {
        return when (type) {
            ToastType.SUCCESS -> R.drawable.checked_icon
            ToastType.ERROR -> R.drawable.cross_icon
            ToastType.INFO -> R.drawable.info_icon
        }
    }

    private fun getBackgroundColor(context: Context, type: ToastType): Int {
        return when (type) {
            ToastType.SUCCESS -> ContextCompat.getColor(context, R.color.light_green)
            ToastType.ERROR -> ContextCompat.getColor(context, R.color.light_red)
            ToastType.INFO -> ContextCompat.getColor(context, R.color.light_blue)
        }
    }

    enum class ToastType { INFO, ERROR, SUCCESS }
}