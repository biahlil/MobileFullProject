package com.coffechain.khmanga

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.coffechain.khmanga.domain.repo.UserRepository
import com.coffechain.khmanga.ui.cafeselect.CafeSelectScreen
import com.coffechain.khmanga.ui.theme.KōhīMangaTheme
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var userRepository: UserRepository

    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { result ->
        this.onSignInResult(result)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag("MainActivity").d("onCreate: Activity Selesai Dibuat.")
        enableEdgeToEdge()
        setContent {
            KōhīMangaTheme {
                AppEntry()
            }
        }
    }

    // di AuthViewModel atau di mana pun logika logout berada
//    fun onLogoutClicked() {
//        viewModelScope.launch {
//            authRepository.logout(context).onSuccess {
//                userRepository.clearLocalUser() // Hapus data lokal setelah logout berhasil
//            }
//        }
//    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        Timber.tag("FirebaseUI").d("onSignInResult: Menerima hasil dari alur login. ResultCode: ${result.resultCode}")

        if (result.resultCode == RESULT_OK) {
            val user = FirebaseAuth.getInstance().currentUser!!
            Timber.tag("FirebaseUI").i("Sign-in BERHASIL. UID: ${user.uid}, Provider: ${response?.providerType}")

            if (response?.isNewUser == true) {
                Timber.tag("FirebaseUI").i("Pengguna baru terdeteksi, membuat profil...")
                val newUser = com.coffechain.khmanga.domain.model.User( // Ganti dengan package Anda
                    uid = user.uid,
                    displayName = user.displayName,
                    email = user.email,
                    photoUrl = user.photoUrl?.toString()
                )

                lifecycleScope.launch {
                    Timber.tag("Firestore").d("Mencoba membuat profil untuk UID: ${newUser.uid}")
                    userRepository.createUserProfile(newUser)
                        .onSuccess {
                            Timber.tag("Firestore").i("Profil Firestore berhasil dibuat.")
                        }
                        .onFailure { e ->
                            Timber.tag("Firestore").e(e, "Gagal membuat profil Firestore.")
                        }
                }
            } else {
                Timber.tag("FirebaseUI").i("Pengguna lama terdeteksi. Selamat datang kembali!")
            }
        } else {
            if (response == null) {
                Timber.tag("FirebaseUI").w("Sign-in DIBATALKAN oleh pengguna.")
            } else {
                val error = response.error
                Timber.tag("FirebaseUI").e(error, "Sign-in GAGAL. Kode Error: ${error?.errorCode}")
            }
        }
    }

    @Composable
    private fun AppEntry() {
        var currentUser by remember { mutableStateOf(FirebaseAuth.getInstance().currentUser) }

        DisposableEffect(Unit) {
            val authInstance = FirebaseAuth.getInstance()

            val authStateListener = FirebaseAuth.AuthStateListener { auth ->
                currentUser = auth.currentUser
                Timber.tag("MainActivity").d("AuthState changed. New user UID: ${auth.currentUser?.uid}")
            }

            authInstance.addAuthStateListener(authStateListener)

            onDispose {
                authInstance.removeAuthStateListener(authStateListener)
                Timber.tag("MainActivity").d("AuthStateListener removed.")
            }
        }

        Timber.tag("MainActivity").d("AppEntry recomposing. Current user is: ${currentUser?.displayName}")

        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            if (currentUser == null) {
                Timber.tag("MainActivity").d("User is null, menampilkan Layar Login.")
                LoginPlaceholderScreen(onLoginClick = ::launchSignInFlow)
            } else {
                Timber.tag("MainActivity").d("User is logged in, menampilkan CafeSelectScreen.")
                CafeSelectScreen()
            }
        }
    }
    private fun launchSignInFlow() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build(),
            AuthUI.IdpConfig.AnonymousBuilder().build()
        )

        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .setLogo(R.drawable.ic_launcher_foreground)
            .setTheme(R.style.Theme_KōhīManga)
            .build()

        Timber.tag("FirebaseUI").d("Meluncurkan alur login FirebaseUI...")
        signInLauncher.launch(signInIntent)
    }
}

@Composable
fun LoginPlaceholderScreen(onLoginClick: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = onLoginClick) {
            Text("Login atau Daftar")
        }
    }
}