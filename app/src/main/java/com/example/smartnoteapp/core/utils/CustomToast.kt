package com.example.smartnoteapp.core.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.smartnoteapp.R


object CustomToast {
    fun makeText(context: Context, text: String, type: ToastType) {
        val inflater = LayoutInflater.from(context)
        val layout: View = inflater.inflate(R.layout.custom_toast_layout, null)

        val toastImageView: ImageView = layout.findViewById(R.id.toastImageView)
        val toastTextView: TextView = layout.findViewById(R.id.toastTextView)

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

    enum class ToastType { INFO, ERROR, SUCCESS }
}