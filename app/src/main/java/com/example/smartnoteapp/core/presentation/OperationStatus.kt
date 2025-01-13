package com.example.smartnoteapp.core.presentation

sealed class OperationStatus {
    data object Loading: OperationStatus()
    data class Success(val message: String): OperationStatus()
    data class Info(val message: String): OperationStatus()
    data class Error(val message: String): OperationStatus()
}