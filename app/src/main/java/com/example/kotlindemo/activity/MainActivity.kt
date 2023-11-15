package com.example.kotlindemo.activity

import android.os.Bundle
import android.view.View
import android.view.Window
import com.example.kotlindemo.study.flow.FlowActivity
import com.example.kotlindemo.adapter.delegate.MainItemDelegate
import com.example.kotlindemo.base.BaseActivity
import com.example.kotlindemo.data.mainItemList
import com.example.kotlindemo.task.linkage.LinkageActivity
import com.example.kotlindemo.databinding.ActivityMainBinding
import com.example.kotlindemo.jetpack.paging3.PagingActivity
import com.example.kotlindemo.model.MainItemType
import com.example.kotlindemo.study.mvi.MviSampleActivity
import com.example.kotlindemo.task.appbar.AppBarActivity
import com.example.kotlindemo.task.microenterprises.view.home.MicroResumeHomeFragment
import com.example.kotlindemo.task.mutildelivery.DeliveryActivity
import com.example.kotlindemo.task.search.PositionSearchBridgeActivity
import com.example.kotlindemo.task.searchresult.PositionSearchResultActivity
import com.example.kotlindemo.utils.AppUtil
import com.example.kotlindemo.utils.StatusBarUtil
import com.example.kotlindemo.utils.binding
import com.example.wallet.WalletActivity
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import com.zhaopin.list.multitype.adapter.MultiTypeAdapter
import com.zhaopin.list.multitype.adapter.setList

class MainActivity : BaseActivity() {

    companion object {
        private const val SHARE_NAME_MATERIAL = "MATERIAL"
        private const val SHARE_NAME_MOTION = "MOTION"
        private const val SHARE_NAME_CONSTRAINT = "CONSTRAINT"
        private const val SHARE_NAME_PAGING = "PAGING"
        private const val SHARE_NAME_VIEW_PAGER = "VIEW_PAGER"
    }

    private val binding: ActivityMainBinding by binding()

    private val listAdapter by lazy {
        MultiTypeAdapter(
        ).apply {
            register(MainItemDelegate(::onItemClick))
        }
    }

    /**
     * 首页Item点击
     */
    private fun onItemClick(type: MainItemType) {
        when (type) {
            MainItemType.Material -> AppUtil.startActivity<MaterialDesignActivity>(this) {}

            MainItemType.Motion -> AppUtil.startActivity<MotionActivity>(this) {}

            MainItemType.Constraint -> AppUtil.startActivity<ConstraintActivity>(this) {}

            MainItemType.Paging -> AppUtil.startActivity<PagingActivity>(this) {}

            MainItemType.ViewPager2 -> AppUtil.startActivity<ViewPager2Activity>(this) {}

            MainItemType.Mark -> AppUtil.startActivity<MarkActivity>(this) {}

            MainItemType.BlockChain -> AppUtil.startActivity<WalletActivity>(this) {}

            MainItemType.MVI -> AppUtil.startActivity<MviSampleActivity>(this) {}

            MainItemType.Flow -> AppUtil.startActivity<FlowActivity>(this) {}

            MainItemType.ZLTask -> { }

            MainItemType.AppBar -> AppUtil.startActivity<AppBarActivity>(this) {}

            MainItemType.Linkage -> AppUtil.startActivity<LinkageActivity>(this) {}

            MainItemType.MircoCompany -> AppUtil.startActivity<MicroResumeHomeFragment>(this) {}

            MainItemType.SearchBridge -> AppUtil.startActivity<PositionSearchBridgeActivity>(this) {}

            MainItemType.SearchResult -> AppUtil.startActivity<PositionSearchResultActivity>(this) {}

            MainItemType.PositionRank -> AppUtil.startActivity<DeliveryActivity>(this) {}
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        doContainerTransform()
        super.onCreate(savedInstanceState)
        initView()
        StatusBarUtil.setRootViewFitsSystemWindows(this, true)
    }

    private fun doContainerTransform() {
        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
        setExitSharedElementCallback(MaterialContainerTransformSharedElementCallback())
        window.sharedElementsUseOverlay = false
    }

    private fun initView() {
        binding.rvMain.adapter = listAdapter
        listAdapter.setList(mainItemList)
    }

}