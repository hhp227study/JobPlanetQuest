package com.hhp227.jobplanetquest.ui.main

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.hhp227.jobplanetquest.R
import com.hhp227.jobplanetquest.model.CellItem
import com.hhp227.jobplanetquest.ui.JobPlanetNavigation
import com.hhp227.jobplanetquest.ui.detail.DetailScreen
import com.hhp227.jobplanetquest.ui.theme.Green80
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@ExperimentalMaterial3Api
@Composable
fun MainScreen() {
    val viewModel: MainViewModel = hiltViewModel()
    val navController = rememberNavController()
    val recruitItems by viewModel.recruitItemList.collectAsState(initial = listOf(Unit))
    val cellItems by viewModel.cellItemList.collectAsState(initial = listOf(CellItem.Loader))
    val tabs = stringArrayResource(R.array.tab_items).zip(listOf<@Composable () -> Unit>(
        { Tab1Screen(
            recruitItems = recruitItems,
            onTabClick = { viewModel.tabPosition = 1 },
            onNavigate = { recruitItem ->
                navController.navigate(
                    JobPlanetNavigation.Detail.name +
                            "?recruitItem=${Json.encodeToString(recruitItem)}"
                )
            }
        ) },
        { Tab2Screen(
            cellItems = cellItems,
            onTabClick = { viewModel.tabPosition = 0 },
            onCellItemClick = { cellItem ->
                navController.navigate(
                    JobPlanetNavigation.Detail.name +
                            "?cellItem=${Json.encodeToString(cellItem)}"
                )
            },
            onRecruitItemClick = { recruitItem ->
                navController.navigate(
                    JobPlanetNavigation.Detail.name +
                            "?recruitItem=${Json.encodeToString(recruitItem)}"
                )
            }
        ) }
    ))

    NavHost(
        navController = navController,
        startDestination = JobPlanetNavigation.Main.name
    ) {
        composable(JobPlanetNavigation.Main.name) {
            Scaffold(topBar = {
                JobPlanetAppBar(viewModel)
            }) { paddingValues ->
                Box(modifier = Modifier.padding(paddingValues)) {
                    tabs[viewModel.tabPosition].second()
                }
            }
        }
        composable(
            JobPlanetNavigation.Detail.name + "?recruitItem={recruitItem}&cellItem={cellItem}",
            listOf(
                navArgument("recruitItem") {
                    type = NavType.StringType
                    nullable = true
                },
                navArgument("cellItem") {
                    type = NavType.StringType
                    nullable = true
                }
            )
        ) {
            DetailScreen(onNavigateUp = navController::navigateUp)
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun JobPlanetAppBar(viewModel: MainViewModel = hiltViewModel()) {
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val queryText by viewModel.queryText.collectAsState()

    Column {
        TopAppBar(title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(R.drawable.jp_jobplanet),
                    contentDescription = null,
                    modifier = Modifier.padding(start = 4.dp)
                )
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.dp)
                        .focusRequester(focusRequester),
                    value = queryText,
                    onValueChange = viewModel::setQueryText,
                    placeholder = {
                        Text(text = "기업, 채용공고 검색")
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        containerColor = Color.Transparent,
                        cursorColor = LocalContentColor.current,
                        placeholderColor = Color.LightGray
                    ),
                    maxLines = 1,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = {
                        viewModel.onQuerySubmit()
                        keyboardController?.hide()
                    }),
                )
            }
        })
        Divider(color = Green80)
    }
}

@Composable
fun TabLayout(
    modifier: Modifier = Modifier,
    @SuppressLint("ModifierParameter")
    tab1Modifier: Modifier,
    @SuppressLint("ModifierParameter")
    tab2Modifier: Modifier,
    tab1FontWeight: FontWeight,
    tab2FontWeight: FontWeight,
    tab1TextColor: Color,
    tab2TextColor: Color
) {
    Row(
        modifier = modifier
            .wrapContentWidth()
    ) {
        Text(
            text = stringArrayResource(R.array.tab_items)[0],
            modifier = tab1Modifier,
            color = tab1TextColor,
            fontSize = 14.sp,
            fontWeight = tab1FontWeight
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = stringArrayResource(R.array.tab_items)[1],
            modifier = tab2Modifier,
            color = tab2TextColor,
            fontSize = 14.sp,
            fontWeight = tab2FontWeight
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TopAppBarPreview() {
    JobPlanetAppBar()
}