package com.linversion.speedy.question

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
fun QuestionRoute(
    viewModel: QuestionViewModel = hiltViewModel()
) {
    QuestionScreen()
}

@Composable
internal fun QuestionScreen() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Question")
    }
}