@file:OptIn(ExperimentalMaterial3Api::class)

package com.coffechain.khmanga.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.coffechain.khmanga.ui.theme.KōhīMangaTheme

@Composable
fun CafeTopBar(
    profileUrl: String? = null,
    searchOnClicked: () -> Unit = {},
    profileOnClicked: () -> Unit = {},
    backOnClick: () -> Unit = {}
) {
    TopAppBar(
        title = {
            IconButton(
                onClick = { searchOnClicked },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            ) {
                TextField(
                    value = "",
                    readOnly = true,
                    onValueChange = {  },
                    label = { Text("Search") },
                    textStyle = MaterialTheme.typography.bodyMedium,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search Icon"
                        )
                    }
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = backOnClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        },
        actions = {
            // e.g. profile icon, search, etc.
            IconButton(onClick = { profileOnClicked }) {
                AsyncImage(
                    model = profileUrl,
                    contentDescription = "Profile",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                        .clip(CircleShape) // circle
                        .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
                )
                Icon(Icons.Filled.Person, contentDescription = "Profile")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    )
}

@Preview
@Composable
private fun PreviewTopBar() {
    KōhīMangaTheme {
        Scaffold(
            topBar = {
                CafeTopBar(profileUrl = "Jojo", backOnClick = {}, searchOnClicked = {}, profileOnClicked = {},)
                     },
        ) { padding ->
            Surface(
                modifier = Modifier.padding(padding),
                color = MaterialTheme.colorScheme.background
            ) {
            }

        }
    }
}