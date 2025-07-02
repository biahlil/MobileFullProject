package com.coffechain.khmanga.domain.repo

import android.content.Context
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.result.Credentials
import com.auth0.android.result.UserProfile

interface Auth0Repository {
    fun login(
        context: Context,
        onSuccess: (Credentials) -> Unit,
        onFailure: (AuthenticationException) -> Unit
    )
    fun logout(
        context: Context,
        onSuccess: () -> Unit,
        onFailure: (AuthenticationException) -> Unit
    )
    suspend fun getUserProfile(accessToken: String): Result<UserProfile>
}