package com.hhp227.jobplanetquest.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import com.hhp227.jobplanetquest.ui.theme.JobPlanetQuestTheme
import com.hhp227.jobplanetquest.ui.main.MainScreen

@ExperimentalMaterial3Api
@Composable
fun JobPlanetQuestApp() {
    JobPlanetQuestTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            MainScreen()
        }
    }
}