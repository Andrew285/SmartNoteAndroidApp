package com.example.smartnoteapp.notes.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category (
    val id: Long = 0,
    val name: String = ""
): Parcelable