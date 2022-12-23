package com.hhp227.jobplanetquest.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.hhp227.jobplanetquest.R
import com.hhp227.jobplanetquest.model.Rating
import com.hhp227.jobplanetquest.model.RecruitItem
import com.hhp227.jobplanetquest.ui.theme.Gray80
import com.hhp227.jobplanetquest.ui.theme.Green80
import com.hhp227.jobplanetquest.ui.theme.Typography
import com.hhp227.jobplanetquest.util.PriceUtil

@Composable
fun Tab1Screen(
    recruitItems: List<Any> = emptyList(),
    onTabClick: () -> Unit = {},
    onNavigate: (RecruitItem) -> Unit = {}
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item(span = { GridItemSpan(2) }) {
            Box {
                TabLayout(
                    modifier = Modifier.padding(vertical = 16.dp),
                    tab1Modifier = Modifier
                        .background(Green80, RoundedCornerShape(50.dp))
                        .padding(vertical = 6.dp, horizontal = 12.dp),
                    tab2Modifier = Modifier
                        .clickable(onClick = onTabClick)
                        .border(1.dp, Gray80, RoundedCornerShape(50.dp))
                        .padding(vertical = 6.dp, horizontal = 12.dp),
                    tab1FontWeight = FontWeight.Bold,
                    tab2FontWeight = FontWeight.Normal,
                    tab1TextColor = Color.White,
                    tab2TextColor = Color.Black
                )
            }
        }
        items(recruitItems) { recruitItem ->
            if (recruitItem is RecruitItem) {
                RecruitItem(
                    recruitItem = recruitItem,
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .clickable { onNavigate(recruitItem) }
                )
            }
        }
    }
    when {
        recruitItems.isEmpty() -> {
            Text(
                text = stringResource(id = R.string.no_result),
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            )
        }
        recruitItems.size == 1 && recruitItems.first() == Unit -> {
            CircularProgressIndicator(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun RecruitItem(recruitItem: RecruitItem, modifier: Modifier = Modifier) {
    val max = remember {
        if (recruitItem.company.ratings.isEmpty()) 0 else recruitItem.company.ratings.maxOf(Rating::rating)
    }

    Column(modifier = modifier) {
        GlideImage(
            model = recruitItem.imageUrl,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .aspectRatio(1.6210526f)
                .padding(top = 0.dp, bottom = 8.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
        Text(
            text = recruitItem.title,
            modifier = Modifier.padding(bottom = 5.dp),
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2
        )
        Row(
            modifier = Modifier.padding(bottom = 3.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.ic_jp_star_solid),
                contentDescription = null,
                modifier = Modifier.padding(end = 4.5.dp)
            )
            Text(
                text = max.toString(),
                modifier = Modifier.padding(end = 4.dp),
                fontSize = 14.sp,
                style = Typography.titleSmall
            )
            Text(
                text = recruitItem.company
                    .ratings
                    .find { it.rating == max }
                    ?.type
                    ?: "Empty",
                fontSize = 14.sp
            )
        }
        if (recruitItem.appeal.isNotBlank()) {
            Row(
                modifier = Modifier.padding(bottom = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                recruitItem.appeal.split(", ").take(2).forEach {
                    Text(
                        text = it,
                        modifier = Modifier
                            .border(1.dp, Gray80, RoundedCornerShape(4.dp))
                            .padding(vertical = 2.dp, horizontal = 6.dp),
                        color = Color.LightGray,
                        fontSize = 10.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
        Text(text = "보상금: ${PriceUtil.getPrice(recruitItem.reward)}원", style = Typography.labelLarge)
    }
}

@Preview(showBackground = true)
@Composable
fun Tab1ScreenPreview() {
    Tab1Screen()
}

@Preview(showBackground = true)
@Composable
fun Tab1ScreenItemPreview() {
    RecruitItem(RecruitItem.getDefaultValue())
}