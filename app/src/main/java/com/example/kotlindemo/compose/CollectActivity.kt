package com.example.kotlindemo.compose

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kotlindemo.R
import com.example.kotlindemo.compose.ui.ZlColors
import com.example.kotlindemo.compose.util.ComposeUIUtil
import com.example.kotlindemo.compose.widget.CenterTopAppBar
import com.example.kotlindemo.compose.widget.PagerTab
import com.example.kotlindemo.compose.widget.PagerTabIndicator
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.zhaopin.social.appbase.util.curContext
import com.zhaopin.toast.showToast
import kotlinx.coroutines.launch

/**
 * @Description
 * @Author LuoJia
 * @Date 2023/11/15
 */
@OptIn(ExperimentalFoundationApi::class, ExperimentalLayoutApi::class)
class CollectActivity : ComposeActivity() {

    private val pageList = arrayOf("职位", "公司")
    private val tagList = listOf("1-3年", "大专", "企业客户(2B)", "电话销售")

    @Preview
    @Composable
    override fun MainPage() {
        kotlin.runCatching {
            ComposeUIUtil.hackTabMinWidth()
        }

        val pagerState = rememberPagerState()
        val scope = rememberCoroutineScope()

        Scaffold(
            topBar = { CollectAppBar() }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                ScrollableTabRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 6.dp),
                    selectedTabIndex = pagerState.currentPage,
                    edgePadding = 0.dp,
                    backgroundColor = Color.White,
                    divider = { },
                    indicator = { tabPositions ->
                        PagerTabIndicator(tabPositions = tabPositions, pagerState = pagerState)
                    }
                ) {
                    pageList.forEachIndexed { index, title ->
                        PagerTab(
                            pagerState = pagerState,
                            index = index,
                            title = title
                        ) {
                            // 点击事件
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        }
                    }
                }

                HorizontalPager(
                    pageCount = pageList.size,
                    state = pagerState,
                    modifier = Modifier.fillMaxSize()
                ) { pageIndex ->
                    if (pageIndex == 0) {
                        JobPage()
                    } else {
                        CompanyPage()
                    }
                }
            }
        }
    }

    @Composable
    fun JobPage() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(ZlColors.C_S2)
                .padding(horizontal = 8.dp)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(10) {
                    CollectJobCard()
                }
            }
        }
    }

    @Composable
    fun CompanyPage() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(ZlColors.C_S2)
                .padding(horizontal = 8.dp)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(3) {
                    CollectCompanyCard()
                }
            }
        }
    }

    @Composable
    fun CollectJobCard() {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White)
                .padding(horizontal = 12.dp, vertical = 16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                // 职位-公司
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(end = 10.dp),
                        text = "职位名称",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = TextStyle(
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = ZlColors.C_B1
                        )
                    )
                    Text(
                        modifier = Modifier.wrapContentWidth(),
                        text = "1万-1.5万",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = ZlColors.C_5B7BE9
                        )
                    )
                }
                // 公司信息
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                ) {
                    CompanyText(text = "智联招聘", fontSize = 14.sp)
                    CompanyText(text = "互联网100强", fontSize = 14.sp)
                    CompanyText(text = "1000-9999人", fontSize = 14.sp)
                }
                // 标签
                CollectFlow()
                // HR信息
                HrLayout()
            }
        }
    }

    @Composable
    private fun CollectCompanyCard() {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White)
                .padding(horizontal = 12.dp, vertical = 16.dp)
                .clickable {
                    curContext.showToast("点击了")
                }
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                // 公司logo
                Image(
                    modifier = Modifier
                        .size(48.dp)
                        .align(Alignment.Top),
                    painter = painterResource(id = R.drawable.c_base_logo_newnull),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 12.dp)
                ) {
                    // 公司名
                    Text(
                        text = "智联招聘",
                        style = TextStyle(
                            color = ZlColors.C_B1,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    // 公司信息
                    Row(
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        CompanyText(text = "其它", fontSize = 13.sp)
                        CompanyText(text = "1000-9999人", fontSize = 13.sp)
                        CompanyText(text = "互联网", fontSize = 13.sp)
                    }
                    // 公司地址
                    Text(
                        text = "北京市朝阳区阜荣街10号首开广场F5层",
                        style = TextStyle(
                            color = ZlColors.C_666666,
                            fontSize = 13.sp,
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(top = 5.dp)
                    )
                }
            }
        }
    }

    @Composable
    fun CollectAppBar() {
        CenterTopAppBar(
            title = {
                Text(
                    text = "我的收藏",
                )
            },
            navigationIcon = {
                Image(
                    painter = painterResource(id = R.drawable.b_common_title_back_icon),
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        finish()
                    }
                )
            }
        )
    }

    @Composable
    private fun CompanyText(text: String, fontSize: TextUnit) {
        Text(
            modifier = Modifier
                .widthIn(max = 220.dp)
                .padding(end = 6.dp),
            text = text,
            style = TextStyle(
                fontSize = fontSize,
                fontWeight = FontWeight.Normal,
                color = ZlColors.C_666666,
            ),
            maxLines = 1
        )
    }

    @Composable
    private fun CollectFlow() {
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(top = 6.dp),
        ) {
            tagList.forEach {
                Text(
                    modifier = Modifier
                        .background(
                            color = ZlColors.C_S2,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    text = it,
                    color = ZlColors.C_B2,
                    fontSize = 12.sp
                )
            }
        }
    }

    @Composable
    private fun HrLayout() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 11.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.wrapContentSize()) {
                Image(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape),
                    painter = painterResource(id = R.drawable.c_common_icon_hr_new_default),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                )
                Image(
                    painter = painterResource(id = R.drawable.c_common_icon_head_hr),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .size(8.dp)
                )
            }
            Text(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .fillMaxWidth()
                    .weight(1f),
                text = "招聘者",
                color = ZlColors.C_666666,
                fontSize = 12.sp
            )
            Text(
                text = "8月24日",
                color = ZlColors.C_B2,
                fontSize = 13.sp
            )
        }
    }

}