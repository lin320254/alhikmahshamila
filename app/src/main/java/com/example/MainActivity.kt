package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.ui.ActiveAppScreen
import com.example.ui.BookViewModel
import com.example.ui.HomeScreen
import com.example.ui.MainLibraryScreen
import com.example.ui.SignInScreen
import com.example.ui.WebReaderScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.util.configureAppCheck

class MainActivity : ComponentActivity() {
    private val bookViewModel: BookViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configureAppCheck(this, intent)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ShamilaApp(viewModel = bookViewModel)
                }
            }
        }
    }
}

@Composable
fun ShamilaApp(viewModel: BookViewModel) {
    val currentUser by viewModel.currentUser.collectAsState()
    val currentLang by viewModel.currentLanguage.collectAsState()

    if (currentUser == null) {
        SignInScreen(
            authManager = viewModel.authManager,
            currentLang = currentLang,
            onLanguageChange = { viewModel.setLanguage(it) }
        )
    } else {
        val activeBook by viewModel.activeReadingBook.collectAsState()

        AnimatedContent(
            targetState = activeBook,
            transitionSpec = {
                fadeIn() togetherWith fadeOut()
            },
            label = "reader_transition"
        ) { currentBook ->
            if (currentBook != null) {
                WebReaderScreen(
                    book = currentBook,
                    lang = currentLang,
                    onClose = { viewModel.closeReader() },
                    onToggleFavorite = { viewModel.toggleFavorite(it) }
                )
            } else {
                val currentScreen by viewModel.currentAppScreen.collectAsState()
                AnimatedContent(
                    targetState = currentScreen,
                    transitionSpec = {
                        fadeIn() togetherWith fadeOut()
                    },
                    label = "screen_transition"
                ) { screen ->
                    when (screen) {
                        ActiveAppScreen.MAIN_LIBRARY -> MainLibraryScreen(viewModel = viewModel)
                        ActiveAppScreen.HOME -> HomeScreen(viewModel = viewModel)
                    }
                }
            }
        }
    }
}
