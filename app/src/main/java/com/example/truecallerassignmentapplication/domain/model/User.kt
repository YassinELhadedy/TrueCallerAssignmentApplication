package com.example.truecallerassignmentapplication.domain.model

data class User(
    val fullName: String,
    val nickName: String,
    val email: String,
    val password: String,
    val id: String?,
    val countryCode: String?,
    val phone: String?,
    val profileImage: String?,
    var newPassword: String?,
    var code: String?
)