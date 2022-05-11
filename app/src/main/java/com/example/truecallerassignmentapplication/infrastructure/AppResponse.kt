package com.example.truecallerassignmentapplication.infrastructure

import com.google.gson.Gson

data class AppResponse(
    val status: Boolean = false,
    val message:String,
    val data: Any? = null

) {
    fun getResult(): String {
        return Gson().toJson(data)
    }
}