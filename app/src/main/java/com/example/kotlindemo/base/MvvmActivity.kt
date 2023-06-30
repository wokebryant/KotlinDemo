package com.example.kotlindemo.base

import android.view.View
import androidx.viewbinding.ViewBinding
import com.alibaba.android.arouter.launcher.ARouter
import com.example.kotlindemo.R
import com.zhaopin.common.widget.loading.LoadingDialog
import com.zhaopin.social.common.mvvm.activity.BaseActivity
import com.zhaopin.social.common.mvvm.model.LoadingType
import com.zhaopin.social.common.mvvm.model.ToastMsg
import com.zhaopin.social.common.mvvm.viewmodel.BaseViewModel
import com.zhaopin.social.common.mvvm.viewmodel.TitleBarVModel
import com.zhaopin.social.common.mvvm.widgets.pagestate.state.Empty
import com.zhaopin.social.common.mvvm.widgets.pagestate.state.Error
import com.zhaopin.toast.showToast

open class MvvmActivity<VM : BaseViewModel, B : ViewBinding>(contentLayoutId: Int) :
    BaseActivity<VM, B>(contentLayoutId) {

    //空页面的数据
    open val empty by lazy {
        Empty(
            emptyText = "暂无数据",
            emptyIcon = R.drawable.b_common_b_app_no_data_icon
        )
    }

    //错误页面的数据类
    open val error by lazy {
        Error(
            errorText = "网络开小差了~",
            errorIcon = R.drawable.core_common_error,
            actionText = "点击重试"
        ) { v ->
            onErrorRetry()
        }
    }

    //埋点相关数据
//    open val track: TrackPage? = null
//
//    private var refreshLayout: SmartRefreshLayout? = null
    override val titleBarModel: TitleBarVModel by lazy {
        super.titleBarModel.copy(
            backRes = R.drawable.b_common_title_back_icon,
            closeRes = R.drawable.c_base_page_close
        )
    }

    override fun onCreateBegin() {
        super.onCreateBegin()
//        ARouter.getInstance().inject(this)
    }

//    override fun initPageStateMap(): MutableMap<PageState, StateViewHolder<out PageState, out ViewDataBinding>> {
//        return mutableMapOf(
//            Pair(
//                empty,
//                EmptyStateViewHolder()
//            ),
//            Pair(Loading(loadingText = null), LoadingStateViewHolder()),
//            Pair(
//                error, ErrorStateViewHolder()
//            )
//        )
//    }


    override fun addLifeCycleObserver() {
        super.addLifeCycleObserver()
//        ImLifecycleObserver(this)
//        if (UserInfo.isBUser(this)) {
//            // 添加B独有的LifeCycle监听
//            ActiveLifecycleObserver(this)
//            ReachManagerLifecycleObserver(this)
//        } else {
//            // 添加C端用的LifeCycle  ...
//            AppUpdateLifecycleObserver(this)
//            ChatLifecycleObserver(this)
//            DeepLinkLifecycleObserver(this)
//            ReceiverLifecycleObserver(this)
//        }
    }


    open fun onErrorRetry() {
        onRefresh(LoadingType.PageLoading)
    }


    override fun handleProgressLoading(loading: Boolean) {
        super.handleProgressLoading(loading)
        if (loading) {
            LoadingDialog.show(this)
        } else {
            LoadingDialog.dismiss()
        }
    }

    override fun initRefreshView(rootView: View) {
        super.initRefreshView(rootView)
//        refreshLayout = findRefreshView(rootView)
//        refreshLayout?.setEnableLoadMore(false)
//        refreshLayout?.setOnRefreshListener {
//            onRefresh(LoadingType.RefreshLoading)
//        }

    }

//    open fun findRefreshView(rootView: View): SmartRefreshLayout? {
//        val view = rootView.findViewById<SmartRefreshLayout>(R.id.refresh_layout)
//        if (view is SmartRefreshLayout) {
//            return view
//        }
//        return null
//    }


    override fun handleRefreshing(refreshing: Boolean) {
//        super.handleRefreshing(refreshing)
//        if (!refreshing && refreshLayout?.isRefreshing == true) {
//            refreshLayout?.finishRefresh()
//        } else if (refreshing && refreshLayout?.isRefreshing == false) {
//            refreshLayout?.autoRefreshAnimationOnly()
//        }
    }

    override fun handleToast(toast: ToastMsg) {
        if (toast.msg != null) {
            showToast(toast.msg)
        }

        if (toast.msgId != null) {
            showToast(toast.msgId!!)
        }

    }


    // 兼容淘口令逻辑
    override fun onWindowFocusChanged(hasFocus: Boolean) {
//        super.onWindowFocusChanged(hasFocus)
//        // B端用的功能
//        if (!ModulePersonalUtil.isBusiness()) return
//
//        if (hasFocus && (!CommonConstant.sIsForeground || CommonConstant.sIsMainForeground)) { // app 从后台唤醒，进入前台
//            CommonConstant.sIsForeground = true
//            try {
//                TaoTokenHelper.getIns().checkClipboard()
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }
    }


}
