package com.linversion.speedy.me

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

/**
 * @author linversion
 * on 2023/12/24
 */
@Composable
fun MeRoute(
    viewModel: MeViewModel = hiltViewModel()
) {
    MeScreen()
}

@Composable
internal fun MeScreen() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Me")
    }
}