package com.example.ai_doubt_hint_generator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.doubthint.ui.screens.HintScreen
import com.app.doubthint.ui.screens.HomeScreen
import com.app.doubthint.ui.screens.SimilarQuestionsScreen
import com.app.doubthint.ui.screens.SolutionScreen
import com.app.doubthint.ui.navigation.NavRoutes
import com.app.doubthint.viewmodel.HintViewModel
import com.example.ai_doubt_hint_generator.ui.theme.AIDoubtHintGeneratorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AIDoubtHintGeneratorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RootApp()
                }
            }
        }
    }
}

@Composable
private fun RootApp(
    viewModel: HintViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val navController = rememberNavController()

    LaunchedEffect(state.currentHintLevel, state.solution, state.similarQuestions.size) {
        when {
            state.solution != null && state.similarQuestions.isNotEmpty() -> {
                navController.navigate(NavRoutes.SIMILAR) {
                    launchSingleTop = true
                }
            }

            state.solution != null -> {
                navController.navigate(NavRoutes.SOLUTION) {
                    launchSingleTop = true
                }
            }

            state.currentHintLevel > 0 -> {
                navController.navigate(NavRoutes.HINT) {
                    launchSingleTop = true
                }
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = NavRoutes.HOME
    ) {
        composable(NavRoutes.HOME) {
            HomeScreen(onIntent = viewModel::handleIntent)
        }
        composable(NavRoutes.HINT) {
            HintScreen(
                state = state,
                onIntent = viewModel::handleIntent
            )
        }
        composable(NavRoutes.SOLUTION) {
            SolutionScreen(
                state = state,
                onIntent = viewModel::handleIntent
            )
        }
        composable(NavRoutes.SIMILAR) {
            SimilarQuestionsScreen(state = state)
        }
    }
}