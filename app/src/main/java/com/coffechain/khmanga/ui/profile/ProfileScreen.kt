package com.coffechain.khmanga.ui.profile

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.cloudinary.android.MediaManager
import com.coffechain.khmanga.R

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    onNavigateToAuth: () -> Unit
) {

    val userProfile by viewModel.userProfile.collectAsState()
    val imageUploadState by viewModel.imageUploadState.collectAsState()
    val context = LocalContext.current
    val imageUrl = remember(userProfile?.photoUrl) {
        userProfile?.photoUrl?.let { publicId ->
            val baseBuilder = MediaManager.get().url().generate(publicId)
            "$baseBuilder?w=400&h=400&c=fill&g=face"
        }
    }
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            viewModel.onProfileImageSelected(uri)
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box {
            AsyncImage(
                model = imageUrl ?: R.drawable.defaultprofilepic,
                contentDescription = "Foto Profil",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
                    .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
                    .clickable {
                        imagePickerLauncher.launch("image/*")
                    }
            )

            if (imageUploadState is ImageUploadState.Uploading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = userProfile?.displayName ?: "Pengguna Anonim",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = userProfile?.email ?: "Tidak ada email",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                viewModel.logout(context)
                onNavigateToAuth()
            },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
        ) {
            Text("Logout")
        }

        Button(onClick = { viewModel.seedDatabase() }) {
            Text("DEBUG: Seed Database Lengkap")
        }
    }
}