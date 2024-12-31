package com.kareem.registrationsdk.domain.model

data class UserModel(
    var id: Int? = null,
    val username: String,
    val phone: String,
    val email: String,
    val password: String,
    val userImage: String?
)