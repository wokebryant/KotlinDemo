package com.example.kotlindemo.task.resume

import android.os.Bundle
import android.view.MotionEvent
import android.view.ViewGroup.MarginLayoutParams
import androidx.activity.viewModels
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlindemo.R
import com.example.kotlindemo.base.BaseActivity
import com.example.kotlindemo.databinding.ActivityResumeRecommendBinding
import com.example.kotlindemo.study.mvi.core.collectEvent
import com.example.kotlindemo.study.mvi.core.collectStateLast
import com.example.kotlindemo.task.ai.util.AiRecommendSnapHelper
import com.example.kotlindemo.task.ai.util.RecyclerViewPageChangeListenerHelper
import com.example.kotlindemo.task.resume.delegate.ResumeRecommendCardDelegate
import com.example.kotlindemo.utils.setGone
import com.zhaopin.list.multitype.adapter.MultiTypeAdapter
import com.zhaopin.list.multitype.adapter.addList
import com.zhaopin.list.multitype.adapter.setList
import com.zhaopin.list.multitype.binder.ItemViewDelegate
import com.zhaopin.list.multitype.loadmore.model.LoadMore
import com.zhaopin.list.multitype.loadmore.model.LoadMoreStatus
import com.zhaopin.social.appcommon.c.OsUtils
import com.zhaopin.social.module_common_util.ext.binding
import com.zhaopin.social.module_common_util.ext.dp
import com.zhaopin.toast.showToast

/**
 * @Description 简历点后推荐页面
 * @Author LuoJia
 * @Date 2024/03/19
 */
class ResumeRecommendActivity : BaseActivity(), IResumeRecommendCallback {

    private val binding: ActivityResumeRecommendBinding by binding()

    private val viewModel: ResumeRecommendViewModel by viewModels()

    /** 列表适配器 */
    private val listAdapter by lazy {
        MultiTypeAdapter(
//            loadMoreEnable = true,
//            autoLoadEnable = true,
//            loadMore = LoadMore(loadMoreStatus = LoadMoreStatus.Loading),
//            loadMoreListener = { }
        ).apply {
            registerResumeRecommendList(this@ResumeRecommendActivity)
        }
    }

    /** 骨架屏 */
    private val skeletonManager by lazy {
        SkeletonManager().apply {
            recyclerView = binding.rvList
            adapter = listAdapter
            itemLayout = R.layout.layout_resume_recommend_skeleton
        }
    }

    /** 列表滑动工具类 */
    private var pageSnapHelper: AiRecommendSnapHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        collect()
        request()
    }

    private fun initView() {
        initBar()
        initRecyclerView()
    }

    private fun initBar() {
        binding.layoutTopBar.updateLayoutParams<MarginLayoutParams> {
            topMargin = OsUtils.getStatusBarHeight()
        }
        binding.layoutTopBar.setClickCallback(this)
    }

    private fun initRecyclerView() {
        binding.rvList.run {
            pageSnapHelper = AiRecommendSnapHelper()
            pageSnapHelper?.attachToRecyclerView(this)
            scrollListener.setSnapHelper(pageSnapHelper)
            adapter = listAdapter
            itemAnimator = null
            setItemViewCacheSize(20)
            setHasFixedSize(true)
            setRecycledViewPool(RecyclerView.RecycledViewPool())
            addOnScrollListener(scrollListener)
        }
    }

    private val scrollListener =
        RecyclerViewPageChangeListenerHelper(object : RecyclerViewPageChangeListenerHelper.OnPageChangeListener {
            override fun onScrollStateChanged(
                recyclerView: RecyclerView?,
                newState: Int,
                position: Int
            ) { }

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) { }

            override fun onPageSelected(position: Int) {
                if (position != 0) {
                    closeGuideAnim()
                }
            }
        })

    /**
     * 收集数据
     */
    private fun collect() {
        viewModel.stateFlow.collectStateLast(this) {
            updateList(it.list)
            updatePageState()
        }
        viewModel.eventFlow.collectEvent(this) {
            when (it) {
                is ResumeRecommendEvent.RemoveItem -> listAdapter.removeResumeRecommendItem(it.index, it.item)
                is ResumeRecommendEvent.UpdateItem -> listAdapter.updateResumeRecommendItem(it.index, it.item)
                is ResumeRecommendEvent.MoveToNext -> moveToNext()
            }
        }
    }

    /**
     * 请求数据
     */
    private fun request() {
        viewModel.requestResumeCardData()
    }

    /**
     * 更新列表数据
     */
    private fun updateList(list: List<Any>) {
        if (list.isEmpty()) {
            return
        }
        val firstList = list.subList(2, list.size)
        val secondList = list.subList(0, 2)
        listAdapter.setList(list.subList(0, 5))
        binding.rvList.scrollToPosition(3)
//        binding.rvList.postDelayed({
//            listAdapter.addList(0, secondList)
//        }, 200)
    }

    /**
     * 更新页面状态
     */
    private fun updatePageState() {
//        skeletonManager.show()
    }

    /**
     * 更新加载状态
     */
    private fun updateLoadStatus() {

    }

    /**
     * 切换下一张卡片
     */
    private fun moveToNext() {
        val moveCardHeight = ResumeRecommendManager.getResumeCardHeight()
        binding.rvList.smoothScrollBy(0, moveCardHeight)
    }

    /**
     * 关闭引导动画
     */
    private fun closeGuideAnim() {
        binding.inGuide.root.setGone()
        val animView = binding.inGuide.positionRankLottieView
        if (animView.isAnimating) {
            animView.cancelAnimation()
        }
    }


    override fun onReportClick() {
       showToast("举报点击")
    }

    override fun onShareClick() {
        showToast("分享点击")

    }

    override fun onInappropriateClick() {
        moveToNext()
    }

    override fun onCollectClick() {

    }

    override fun onCallClick() {

    }

    override fun onGreetOrChatClick() {

    }

    override fun onCancelInappropriateClick() {

    }

}