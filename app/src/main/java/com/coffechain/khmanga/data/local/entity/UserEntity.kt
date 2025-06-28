package com.coffechain.khmanga.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val uid: String,
    val displayName: String?,
    val email: String?,
    val photoUrl: String?
)