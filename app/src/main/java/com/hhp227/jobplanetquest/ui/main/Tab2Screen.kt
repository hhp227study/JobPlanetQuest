package com.hhp227.jobplanetquest.ui.main

import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.core.graphics.drawable.toIcon
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.hhp227.jobplanetquest.R
import com.hhp227.jobplanetquest.model.CellItem
import com.hhp227.jobplanetquest.model.RecruitItem
import com.hhp227.jobplanetquest.ui.theme.Gray80
import com.hhp227.jobplanetquest.ui.theme.Green80
import com.hhp227.jobplanetquest.ui.theme.Typography
import com.hhp227.jobplanetquest.util.DateUtil
import com.hhp227.jobplanetquest.util.DateUtil.toDateString
import com.hhp227.jobplanetquest.util.PriceUtil

@Composable
fun Tab2Screen(
    cellItems: List<CellItem> = emptyList(),
    onTabClick: () -> Unit = {},
    onCellItemClick: (CellItem) -> Unit = {},
    onRecruitItemClick: (RecruitItem) -> Unit = {}
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item {
            Box {
                TabLayout(
                    modifier = Modifier.padding(horizontal = 20.dp).padding(top = 16.dp),
                    tab1Modifier = Modifier
                        .clickable(onClick = onTabClick)
                        .border(1.dp, Gray80, RoundedCornerShape(50.dp))
                        .padding(vertical = 5.dp, horizontal = 12.dp),
                    tab2Modifier = Modifier
                        .background(Green80, RoundedCornerShape(50.dp))
                        .padding(vertical = 5.dp, horizontal = 12.dp),
                    tab1FontWeight = FontWeight.Normal,
                    tab2FontWeight = FontWeight.Bold,
                    tab1TextColor = Color.Black,
                    tab2TextColor = Color.White
                )
            }
        }
        items(cellItems) { cellItem ->
            when (cellItem) {
                is CellItem.CellTypeCompany -> CellCompanyItem(cellItem, Modifier.padding(horizontal = 20.dp).clickable { onCellItemClick(cellItem) })
                is CellItem.CellTypeHorizontalTheme -> CellHorizontalThemeItem(cellItem, onItemClick = onRecruitItemClick)
                is CellItem.CellTypeReview -> CellReviewItem(cellItem, Modifier.padding(bottom = 24.dp))
                else -> Unit
            }
            Divider()
        }
    }
    when {
        cellItems.size == 1 && cellItems.first() is CellItem.Loader -> {
            CircularProgressIndicator(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            )
        }
        cellItems.isEmpty() -> {
            Text(stringResource(R.string.no_result), Modifier.fillMaxSize().wrapContentSize(Alignment.Center))
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CellCompanyItem(
    cellItem: CellItem.CellTypeCompany,
    modifier: Modifier = Modifier
) {
    val bitmap = BitmapFactory.decodeResource(LocalContext.current.resources, R.drawable.user)

    Column(modifier = modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.padding(bottom = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            GlideImage(
                model = cellItem.logoPath,
                contentDescription = null,
                modifier = Modifier
                    .size(width = 58.dp, height = 58.dp)
                    .clip(RoundedCornerShape(4.dp)),
                contentScale = ContentScale.Crop
            ) {
                it.placeholder(R.drawable.user).error(bitmap)
            }
            Column(
                modifier = Modifier.padding(top = 4.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Text(text = cellItem.name)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(R.drawable.ic_jp_star_solid),
                        contentDescription = null,
                        modifier = Modifier.padding(start = 0.5.dp, end = 4.5.dp)
                    )
                    Text(
                        text = cellItem.rateTotalAvg.toString(),
                        modifier = Modifier.padding(end = 4.dp),
                        fontSize = 14.sp,
                        style = Typography.titleSmall
                    )
                    Text(
                        text = cellItem.industryName,
                        fontSize = 14.sp
                    )
                }
            }
        }
        Divider()
        Row(modifier = Modifier.padding(bottom = 8.dp)) {
            Image(
                painter = painterResource(R.drawable.ic_icon_quote),
                contentDescription = null,
                modifier = Modifier
                    .padding(top = 24.dp)
                    .size(28.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = DateUtil.getDate(cellItem.updateDate)
                    ?.toDateString()
                    ?: DateUtil.getParse(cellItem.updateDate),
                modifier = Modifier.padding(top = 30.dp),
                fontSize = 15.sp
            )
        }
        Text(
            text = cellItem.reviewSummary,
            modifier = Modifier.padding(bottom = 12.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .border(1.dp, Gray80, RoundedCornerShape(8.dp))
                .wrapContentSize(Alignment.Center),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "평균 연봉", color = Green80)
                Spacer(modifier = Modifier.width(12.dp))
                Text(text = PriceUtil.getPrice(cellItem.salaryAvg), fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "만원")
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "면접 질문",
            modifier = Modifier.padding(bottom = 8.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
        Text(text = cellItem.interviewQuestion)
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun CellHorizontalThemeItem(
    cellItem: CellItem.CellTypeHorizontalTheme,
    modifier: Modifier = Modifier,
    onItemClick: (RecruitItem) -> Unit
) {
    Column(modifier = modifier.fillMaxSize()) {
        Text(
            text = cellItem.sectionTitle,
            modifier = Modifier.padding(start = 24.dp, top = 1.dp, bottom = 18.dp),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        LazyRow(
            contentPadding = PaddingValues(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(cellItem.recommendRecruit) { recruitItem ->
                RecruitItem(
                    recruitItem = recruitItem,
                    modifier = Modifier.width(154.dp).clickable { onItemClick(recruitItem) }
                )
            }
        }
        Spacer(modifier = Modifier.height(23.dp))
    }
}

@Composable
fun CellReviewItem(
    cellItem: CellItem.CellTypeReview,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier
        .fillMaxSize()
        .wrapContentSize(Alignment.Center)) {
        Text(text = "해당 레이아웃은 디자인이 주어지지 않았습니다.")
    }
}

@Preview(showBackground = true)
@Composable
fun Tab2ScreenPreview() {
    Tab2Screen()
}

@Preview(showBackground = true)
@Composable
fun CellCompanyItemPreview() {
    CellCompanyItem(
        cellItem = CellItem.CellTypeCompany.getDefaultValue()
    )
}

@Preview(showBackground = true)
@Composable
fun CellHorizontalThemeItemPreview() {
    CellHorizontalThemeItem(
        cellItem = CellItem.CellTypeHorizontalTheme.getDefaultValue(),
        modifier = Modifier.wrapContentSize(),
        onItemClick = fun(_) = Unit
    )
}

@Preview(showBackground = true)
@Composable
fun CellReviewItemPreview() {
    CellReviewItem(
        cellItem = CellItem.CellTypeReview.getDefaultValue(),
        modifier = Modifier.wrapContentSize()
    )
}