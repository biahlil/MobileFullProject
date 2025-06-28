package com.coffechain.khmanga.data.local

import com.coffechain.khmanga.data.local.entity.UserEntity
import com.coffechain.khmanga.domain.model.User

fun User.toEntity(): UserEntity {
    return UserEntity(
        uid = this.uid,
        displayName = this.displayName,
        email = this.email,
        photoUrl = this.photoUrl
    )
}

fun UserEntity.toDomainModel(): User {
    return User(
        uid = this.uid,
        displayName = this.displayName,
        email = this.email,
        photoUrl = this.photoUrl
    )
}