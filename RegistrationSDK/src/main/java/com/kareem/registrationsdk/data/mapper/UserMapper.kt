package com.kareem.registrationsdk.data.mapper

import com.kareem.registrationsdk.data.model.UserEntity
import com.kareem.registrationsdk.domain.model.UserModel

fun UserModel.toUserEntity(): UserEntity {
    return UserEntity(
        id = this.id,
        username = this.username,
        phone = this.phone,
        email = this.email,
        password = this.password,
        userImage = this.userImage
    )
}

fun UserEntity.toUserModel(): UserModel {
    return UserModel(
        id = this.id,
        username = this.username,
        phone = this.phone,
        email = this.email,
        password = this.password,
        userImage = this.userImage
    )
}
