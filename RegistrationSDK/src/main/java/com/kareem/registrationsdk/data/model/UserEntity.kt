package com.kareem.registrationsdk.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val username: String,
    val phone: String,
    val email: String,
    val password: String,
    val userImage: String?
)
