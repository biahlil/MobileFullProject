package com.coffechain.khmanga.data.repository

import android.content.Context
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationAPIClient
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import com.auth0.android.result.UserProfile
import com.coffechain.khmanga.domain.repo.Auth0Repository
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume

@Singleton
class AuthRepositoryImpl @Inject constructor(private val account: Auth0) : Auth0Repository {

    private val authClient = AuthenticationAPIClient(account)

    override fun login(
        context: Context,
        onSuccess: (Credentials) -> Unit,
        onFailure: (AuthenticationException) -> Unit
    ) {
        WebAuthProvider.login(account)
            .withScheme("https") // Pastikan sesuai dengan konfigurasi di AndroidManifest
            .withScope("openid profile email offline_access") // offline_access untuk refresh token
            .start(context, object : Callback<Credentials, AuthenticationException> {
                override fun onSuccess(result: Credentials) {
                    onSuccess(result)
                }

                override fun onFailure(error: AuthenticationException) {
                    onFailure(error)
                }
            })
    }

    override fun logout(
        context: Context,
        onSuccess: () -> Unit,
        onFailure: (AuthenticationException) -> Unit
    ) {
        WebAuthProvider.logout(account)
            .withScheme("https")
            .start(context, object : Callback<Void?, AuthenticationException> {
                override fun onSuccess(result: Void?) {
                    onSuccess()
                }

                override fun onFailure(error: AuthenticationException) {
                    onFailure(error)
                }
            })
    }

    override suspend fun getUserProfile(accessToken: String): Result<UserProfile> =
        suspendCancellableCoroutine { continuation ->
            authClient.userInfo(accessToken)
                .start(object : Callback<UserProfile, AuthenticationException> {
                    override fun onSuccess(result: UserProfile) {
                        continuation.resume(Result.success(result))
                    }

                    override fun onFailure(error: AuthenticationException) {
                        continuation.resume(Result.failure(error))
                    }
                })
        }
}