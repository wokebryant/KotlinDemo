package com.example.kotlindemo.task.appbar

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.kotlindemo.R
import com.example.kotlindemo.databinding.LayoutCampusHome2Binding
import com.example.kotlindemo.utils.*
import com.example.kotlindemo.widget.drawable.GradientBorderDrawable
import com.example.kotlindemo.widget.pageTransformer.OverlapSliderTransformer
import com.zhaopin.social.module_common_util.ext.binding
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlin.math.abs

/**
 * @Description 校园首页AppBar改版页面
 * @Author LuoJia
 * @Date 2022-10-11
 */
class CampusAppBar @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attr, defStyleAttr) {

    companion object {
        private const val TAG = "CampusAppBar"
        private const val STANDARD_DENSITY = 2.75
    }

//    private val binding: LayoutCampusHome2Binding by lazy {
//        DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.layout_campus_home_2, this, true)
//    }

    private val binding: LayoutCampusHome2Binding by binding()

    /** 直播卡片滑动效果 */
    private val pageTransformer: CompositePageTransformer
        get()  {
            val compositePageTransformer = CompositePageTransformer()
            compositePageTransformer.addTransformer(OverlapSliderTransformer(
                ViewPager2.ORIENTATION_HORIZONTAL,
                0.3f,
                0f,
                1.0f,
                -dip2px(65f).toFloat())
            )
            return compositePageTransformer
        }

    /** 事件监听回调 */
    val campusHomeEventCallback: ICampusHomeEvent? = null

    init {
        initView()
        initData()
        getScreen()
        val width = SizeUtils.getScreenWidth(context)
        Log.i("LuoJia", width.toString())
    }

    private fun getRealGap() {

    }

    private fun getScreen() {
        val dm = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getMetrics(dm)
        val density = dm.density
    }

    private fun initView() {
        registerListener()
        startRecruitmentAnim()
        handleLiveCardViewPager()
        dispatchState(CampusHomeState.Tip)
    }

    private fun initData() {

    }

    /**
     * 注册监听事件
     */
    private fun registerListener() {
        // 提示区域点击事件
        with(binding.lyCampusHomeTip) {
            tvCampusResumeTitle.setOnClickListener {
                campusHomeEventCallback?.onResumeTipJumpClick()
            }
            tvCampusResumeCheck.setOnClickListener {
                campusHomeEventCallback?.onResumeTipJumpClick()
            }
            ivCampusResumeCancel.setOnClickListener {
                campusHomeEventCallback?.onResumeCancelClick()
            }
        }

        // 卡片区域点击事件
        with(binding.lyCampusHomeCard) {
            tvLiveMore.setOnClickListener {
                campusHomeEventCallback?.onMoreLiveClick()
            }
            tvActivityMore.setOnClickListener {
                campusHomeEventCallback?.onMoreActivityClick()
            }
            ivCurrentWeekTop.setOnClickListener {
                campusHomeEventCallback?.onActivityTopItemClick()
            }
            ivCurrentWeekBottom.setOnClickListener {
                campusHomeEventCallback?.onActivityBottomItemClick()
            }

            rlCampusFlipper.background = GradientBorderDrawable(
                borderColors = intArrayOf(getColor(R.color.white_90), getColor(R.color.white_70)),
                bgColors = intArrayOf(getColor(R.color.white_70), getColor(R.color.white_70)),
                borderWidth = dip2px(0.5f).toFloat(),
                radius = dip2px(12f).toFloat(),
                radiusType = GradientBorderDrawable.RadiusType.T,
                borderAngle = GradientBorderDrawable.ANGLE_TOP_BOTTOM,
                bgAngle = GradientBorderDrawable.ANGLE_LEFT_TOP_BOTTOM_RIGHT
            )

            lyCampusLive.background = GradientBorderDrawable(
                borderColors = intArrayOf(getColor(R.color.white_70), getColor(R.color.white_70), getColor(R.color.purple_100)),
                bgColors = intArrayOf(getColor(R.color.white_70), getColor(R.color.white_70)),
                borderWidth = dip2px(0.5f).toFloat(),
                radius = dip2px(12f).toFloat(),
                radiusType = GradientBorderDrawable.RadiusType.LB,
                borderAngle = GradientBorderDrawable.ANGLE_TOP_BOTTOM,
                bgAngle = GradientBorderDrawable.ANGLE_LEFT_TOP_BOTTOM_RIGHT
            )
            lyCampusActivity.background = GradientBorderDrawable(
                borderColors = intArrayOf(getColor(R.color.white_70), getColor(R.color.white_70), getColor(R.color.purple_100)),
                bgColors = intArrayOf(getColor(R.color.white_70), getColor(R.color.white_70)),
                borderWidth = dip2px(1f).toFloat(),
                radius = dip2px(12f).toFloat(),
                radiusType = GradientBorderDrawable.RadiusType.RB,
                borderAngle = GradientBorderDrawable.ANGLE_TOP_BOTTOM,
                bgAngle = GradientBorderDrawable.ANGLE_LEFT_TOP_BOTTOM_RIGHT
            )
        }

        // 底部金刚区点击事件
        with(binding.lyCampusHomeBottom) {
            lyLeft.setOnClickListener {
                campusHomeEventCallback?.onBottomLeftCenterClick()
            }
            lyCenter.setOnClickListener {
                campusHomeEventCallback?.onBottomRightCenterClick()
            }
            lyRight.setOnClickListener {
                campusHomeEventCallback?.onBottomRightCenterClick()
            }
        }
    }

    /**
     * 开启公司招聘上下轮播动效
     */
    private fun startRecruitmentAnim() {
        // 公司Icon轮播
        val iconFlipper = binding.lyCampusHomeCard.ivCompanyIconFlipper
        iconFlipper.startImageFlipper(testIconList)
        val textFlipper = binding.lyCampusHomeCard.tvCompanyJobFlipper
        textFlipper.startTextFlipper(testTextList)
        iconFlipper.inAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {
                textFlipper.showNext()
            }

            override fun onAnimationEnd(p0: Animation?) {
            }

            override fun onAnimationRepeat(p0: Animation?) {
            }

        })
        iconFlipper.startFlipping()
    }

    private var currentSelectPage = -1;

    /**
     * 处理直播卡片轮播
     */
    @SuppressLint("NotifyDataSetChanged")
    private fun handleLiveCardViewPager() {
        val data = mutableListOf(
            LiveCardModel(url = textLiveCardUrlList[0], position =  0),
            LiveCardModel(url = textLiveCardUrlList[1], position =  0),
            LiveCardModel(url = textLiveCardUrlList[2], position =  0),
            LiveCardModel(url = textLiveCardUrlList[3], position =  0),
            LiveCardModel(url = textLiveCardUrlList[4], position =  0)
        )
        val currentItem = if (data.size <= 2) 0
                          else CampusBannerUtil.getOriginalPosition(data.size)
        val realData = data.map {
            it.position = currentItem
            it
        }
        val liveCardAdapter = LiveCardViewpagerAdapter(realData)
        binding.lyCampusHomeCard.vpLiveCard.run {
            adapter = liveCardAdapter
            offscreenPageLimit = 1
            setCurrentItem(currentItem, false)
            val recyclerView= getChildAt(0) as RecyclerView
            recyclerView.apply {
                val leftPadding = dip2px(10f)
                val rightPadding = dip2px(10f)
                setPadding(leftPadding, 0, rightPadding, 0)
                clipToPadding = false
            }
            setPageTransformer(pageTransformer)
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    realData.forEach {
                        it.position = position
                    }
                    liveCardAdapter.notifyDataSetChanged()
                    currentSelectPage = position
                    Log.i("LuoJia", "selectedPage= $position")
                }
            })
        }

    }

    private fun hideItem(position: Int) {
        val recyclerView = getChildAt(0) as RecyclerView
        val itemView = recyclerView.findViewHolderForAdapterPosition(position) as LiveCardViewpagerAdapter.PagerViewHolder
        itemView.binding.flCampusLiveState.setGone()

    }

    /**
     * 根据State显示不同的UI
     */
    private fun dispatchState(state: CampusHomeState) {
        when (state) {
            CampusHomeState.Tip -> {
                binding.lyCampusHomeTip.root.setGone()
            }
            CampusHomeState.Normal -> {

            }
            CampusHomeState.NoActivity -> {
                with(binding.lyCampusHomeCard) {
                    lyCampusActivity.setGone()
                    lyCampusActivityEmpty.setVisible()
                }
            }
            CampusHomeState.NoLive -> {
                with(binding.lyCampusHomeCard) {
                    lyCampusLive.setGone()
                    lyCampusLiveEmpty.setVisible()
                }
            }
            CampusHomeState.NoActivityAndLive -> {
                with(binding.lyCampusHomeCard) {
                    llRecruitment.setGone()
//                    rlCampusFlipper.background = getDrawable(R.drawable.campus_home_top_card_bg_v2)
                }
            }
        }
    }

    fun View.dp2px(dpValue: Float): Int {
        return (dpValue * resources.displayMetrics.density + 0.5f).toInt()
    }

}