package com.example.smartnoteapp.auth.domain.models

data class User (
    val name: String,
    val surname: String,
    val nickname: String,
    val age: Int,
    val status: String
)