package com.example.kotlindemo.compose

import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Surface
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.runtime.snapshots.SnapshotMutableState
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.filter
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.kotlindemo.R
import com.example.kotlindemo.compose.data.CollectCompanyItem
import com.example.kotlindemo.compose.data.CollectJobItem
import com.example.kotlindemo.compose.ui.ZlColors
import com.example.kotlindemo.compose.util.ComposeUIUtil
import com.example.kotlindemo.compose.viewmodel.CollectViewModel
import com.example.kotlindemo.compose.widget.CenterTopAppBar
import com.example.kotlindemo.compose.widget.CollectDialog
import com.example.kotlindemo.compose.widget.EmptyContent
import com.example.kotlindemo.compose.widget.LoadMoreLazyColum
import com.example.kotlindemo.compose.widget.PagerTab
import com.example.kotlindemo.compose.widget.PagerTabIndicator
import com.example.kotlindemo.compose.widget.StatePage
import com.example.kotlindemo.compose.widget.refresh.SwipeRefreshLayout
import com.example.kotlindemo.compose.widget.refresh.header.LoadHeader
import com.example.kotlindemo.utils.copyOf
import com.zhaopin.social.appbase.util.curContext
import com.zhaopin.toast.showToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * @Description
 * @Author LuoJia
 * @Date 2023/11/15
 */
@OptIn(ExperimentalFoundationApi::class, ExperimentalLayoutApi::class, ExperimentalMaterialApi::class)
class CollectActivity : ComposeActivity() {

    private val viewModel by viewModels<CollectViewModel>()

    @Preview
    @Composable
    override fun MainPage() {
        kotlin.runCatching {
            ComposeUIUtil.hackTabMinWidth()
        }

        // 获取数据
        val pageList = viewModel.pageList
        viewModel.getJobData()

        val pagerState = rememberPagerState()
        val scope = rememberCoroutineScope()
        val bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

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
                                pagerState.scrollToPage(index)
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
                        JobPage(scope, bottomSheetState)
                    } else {
                        CompanyPage(scope, bottomSheetState)
                    }
                }
            }
        }

        // 底部弹窗
        CollectDialog(
            onClick = {

            },
            scope = scope,
            state = bottomSheetState
        )
    }

    @Composable
    fun JobPage(
        scope: CoroutineScope,
        bottomSheetState: ModalBottomSheetState
    ) {
        val list: LazyPagingItems<CollectJobItem> = viewModel.jobData.collectAsLazyPagingItems()

        SwipeRefreshLayout(
            isRefreshing = list.loadState.refresh is LoadState.Loading && list.itemCount > 0,
            onRefresh = { list.refresh() },
            indicator = {
                LoadHeader(state = it)
            },
            modifier = Modifier.background(ZlColors.C_S2)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(ZlColors.C_S2)
                    .padding(horizontal = 8.dp)
            ) {
                LoadMoreLazyColum(
                    loadState = list.loadState,
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(
                        count = list.itemCount,
                        key = {
                            list[it]?.jobName ?: ""
                        }
                    ) { it ->
                        val itemData = list[it] ?: return@items
                        CollectJobCard(
                            jobItem = itemData,
                            onLongClick = {
                                scope.launch {
                                    bottomSheetState.show()
                                }
                            }
                        )
                    }
                }
            }
        }

        StatePage(pagingItems = list, empty = EmptyContent(text = "仅展示最近3个月收藏的职位"))
    }

    @Composable
    fun CompanyPage(
        scope: CoroutineScope,
        bottomSheetState: ModalBottomSheetState
    ) {
        val list = viewModel.companyData.collectAsLazyPagingItems()

        SwipeRefreshLayout(
            isRefreshing = list.loadState.refresh is LoadState.Loading && list.itemCount > 0,
            onRefresh = { list.refresh() },
            indicator = {
                LoadHeader(state = it)
            },
            modifier = Modifier.background(ZlColors.C_S2)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(ZlColors.C_S2)
                    .padding(horizontal = 8.dp)
            ) {
                LoadMoreLazyColum(
                    loadState = list.loadState,
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(list.itemCount) {
                        val itemData = list[it] ?: return@items
                        CollectCompanyCard(
                            companyItem = itemData,
                            onLongClick = {
                                scope.launch {
                                    bottomSheetState.show()
                                }
                            }
                        )
                    }
                }
            }
        }

        StatePage(pagingItems = list, empty = EmptyContent(text = "仅展示最近3个月收藏的公司"))
    }

    @OptIn(ExperimentalTextApi::class)
    @Composable
    fun CollectJobCard(jobItem: CollectJobItem, onLongClick: () -> Unit) {
        val contentColor = if (jobItem.isOffline) jobItem.offlineColor else jobItem.onlineColor
        var jobNameWidth by remember {
            mutableStateOf(300)
        }

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White)
                .padding(horizontal = 12.dp, vertical = 16.dp)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onLongPress = {
                            onLongClick.invoke()
                        },
                        onTap = {

                        }
                    )
                }
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                // 职位-工资
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(end = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            modifier = Modifier.widthIn(max = 200.dp),
                            text = jobItem.jobName,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (jobItem.isOffline) jobItem.offlineColor else jobItem.jobNameColor
                            ),
                        )
                        if (jobItem.firstTagUrl.isNotEmpty()) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current).data(jobItem.firstTagUrl).build(),
                                contentDescription = "",
                                modifier = Modifier.padding(start = 5.dp)
                            )
                        }
                        if (jobItem.secondTagUrl.isNotEmpty()) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current).data(jobItem.secondTagUrl).build(),
                                contentDescription = "",
                                modifier = Modifier
                                    .padding(start = 5.dp)
                                    .fillMaxWidth()
                                    .weight(1f)
                            )
                        }
                    }

                    Text(
                        modifier = Modifier.wrapContentWidth(),
                        text = if (jobItem.isOffline) "职位已下线" else jobItem.salary,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (jobItem.isOffline) jobItem.offlineColor else jobItem.salaryColor
                        )
                    )
                }
                // 公司信息
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                ) {
                    CompanyText(text = jobItem.companyName, fontSize = 14.sp, color = contentColor)
                    CompanyText(text = jobItem.companyStrength, fontSize = 14.sp, color = contentColor)
                    CompanyText(text = jobItem.companySize, fontSize = 14.sp, color = contentColor)
                }
                // 标签
                CollectFlow(jobItem.skillList, contentColor)
                // HR信息
                HrLayout(jobItem, contentColor)
            }
        }
    }

    @Composable
    private fun CollectCompanyCard(companyItem: CollectCompanyItem, onLongClick: () -> Unit) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White)
                .padding(horizontal = 12.dp, vertical = 16.dp)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onLongPress = {
                            onLongClick.invoke()
                        },
                        onTap = {

                        }
                    )
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
                        text = companyItem.companyName,
                        style = TextStyle(
                            color = ZlColors.C_B1,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    // 公司信息
                    Row(
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        CompanyText(text = companyItem.companyProperty, fontSize = 13.sp)
                        CompanyText(text = companyItem.companySize, fontSize = 13.sp)
                        CompanyText(text = companyItem.industry, fontSize = 13.sp)
                    }
                    // 公司地址
                    Text(
                        text = companyItem.address,
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
    private fun CompanyText(text: String, fontSize: TextUnit, color: Color = ZlColors.C_B2) {
        Text(
            modifier = Modifier
                .widthIn(max = 220.dp)
                .padding(end = 6.dp),
            text = text,
            style = TextStyle(
                fontSize = fontSize,
                fontWeight = FontWeight.Normal,
                color = color,
            ),
            maxLines = 1
        )
    }

    @Composable
    private fun CollectFlow(tagList: List<String>, color: Color) {
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
                    color = color,
                    fontSize = 12.sp
                )
            }
        }
    }

    @Composable
    private fun HrLayout(jobItem: CollectJobItem, color: Color) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 11.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.wrapContentSize()) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current).data(jobItem.hrAvatar).build(),
                    placeholder = painterResource(id = R.drawable.c_common_icon_hr_new_default),
                    contentDescription = "",
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                if (jobItem.hrOnline) {
                    Image(
                        painter = painterResource(id = R.drawable.c_common_icon_head_hr),
                        contentDescription = null,
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .size(8.dp)
                    )
                }
            }
            Text(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .fillMaxWidth()
                    .weight(1f),
                text = jobItem.hrName,
                color = color,
                fontSize = 12.sp
            )
            Text(
                text = jobItem.publishDate,
                color = color,
                fontSize = 13.sp
            )
        }
    }

}