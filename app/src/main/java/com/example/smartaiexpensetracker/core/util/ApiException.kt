package com.example.smartaiexpensetracker.core.util

class ApiException(val statusCode:Int, override val message: String?) : Exception(message)