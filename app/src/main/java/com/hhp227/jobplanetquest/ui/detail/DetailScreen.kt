package com.hhp227.jobplanetquest.ui.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hhp227.jobplanetquest.model.CellItem
import com.hhp227.jobplanetquest.ui.JobPlanetNavigation
import com.hhp227.jobplanetquest.ui.main.CellCompanyItem
import com.hhp227.jobplanetquest.ui.main.RecruitItem
import com.hhp227.jobplanetquest.ui.theme.Green80

@ExperimentalMaterial3Api
@Composable
fun DetailScreen(onNavigateUp: () -> Unit) {
    val viewModel: DetailViewModel = hiltViewModel()
    val scrollState = rememberScrollState()
    val recruitItem by viewModel.recruitItem.collectAsState(null)
    val cellItem by viewModel.cellItem.collectAsState(null)

    Scaffold(topBar = {
        Column {
            TopAppBar(
                title = { Text(text = JobPlanetNavigation.Detail.name) },
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
            Divider(color = Green80)
        }
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(scrollState)
                .padding(20.dp)
        ) {
            if (recruitItem != null) {
                RecruitItem(recruitItem = recruitItem!!, modifier = Modifier.fillMaxSize())
            } else if (cellItem != null && cellItem is CellItem.CellTypeCompany) {
                CellCompanyItem(cellItem as CellItem.CellTypeCompany)
            }
        }
    }
}