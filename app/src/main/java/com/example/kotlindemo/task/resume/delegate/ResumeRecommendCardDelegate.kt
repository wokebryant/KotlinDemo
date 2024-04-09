package com.example.kotlindemo.task.resume.delegate

import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import com.example.kotlindemo.R
import com.example.kotlindemo.databinding.ItemResumeRecommendCardBinding
import com.example.kotlindemo.task.resume.IResumeRecommendCallback
import com.example.kotlindemo.task.resume.ResumeRecommendCardState
import com.example.kotlindemo.task.resume.ResumeRecommendManager
import com.example.kotlindemo.task.resume.registerResumeRecommendCard
import com.zhaopin.list.multitype.adapter.MultiTypeAdapter
import com.zhaopin.list.multitype.adapter.setList
import com.zhaopin.list.multitype.binder.BindingViewDelegate
import com.zhaopin.social.appbase.util.currentActivity
import com.zhaopin.social.common.extension.setGone
import com.zhaopin.social.common.extension.setVisible
import com.zhaopin.social.module_common_util.ext.onClick
import com.zhaopin.toast.showToast

/**
 * @Description 简历点后推荐页卡片
 * @Author LuoJia
 * @Date 2024/03/19
 */
class ResumeRecommendCardDelegate(
    private val callback: IResumeRecommendCallback? = null
) : BindingViewDelegate<ResumeRecommendCardState, ItemResumeRecommendCardBinding>() {

    private var binding: ItemResumeRecommendCardBinding? = null

    override fun onBindViewHolder(
        binding: ItemResumeRecommendCardBinding,
        item: ResumeRecommendCardState,
        position: Int
    ) {
        this.binding = binding
        val delayTime = if (position == 0) 0L else 0L
        binding.root.postDelayed({
            setView(binding, item, position)
        }, delayTime)
    }

    private fun setView(
        binding: ItemResumeRecommendCardBinding,
        item: ResumeRecommendCardState,
        position: Int
    ) {
        // 设置卡片高度
        setCardHeight(binding)
        // 设置RecyclerView
        setRecyclerView(binding, item)
        // 设置底栏
        setBottomBar(binding, item)
        // 错误页
        setErrorLayout(binding, item)
    }

    /**
     * 设置卡片高度
     */
    private fun setCardHeight(
        binding: ItemResumeRecommendCardBinding
    ) {
        val cardHeight = ResumeRecommendManager.getResumeCardHeight()
        binding.root.updateLayoutParams<ViewGroup.LayoutParams> {
            height = cardHeight
        }
    }

    private fun setRecyclerView(
        binding: ItemResumeRecommendCardBinding,
        item: ResumeRecommendCardState,
    ) {
        val listAdapter = MultiTypeAdapter().apply { registerResumeRecommendCard() }
        binding.rvList.run {
            adapter = listAdapter
            itemAnimator = null
            setItemViewCacheSize(10)
        }
        listAdapter.setList(item.list)
    }

    private fun setBottomBar(
        binding: ItemResumeRecommendCardBinding,
        item: ResumeRecommendCardState,
    ) {
        val bottomBar = binding.inBottomBar
        bottomBar.run {
            if (item.isShowInappropriate) {
                llCancelNotSuite.setVisible()
                llNormalState.setGone()
            } else {
                llCancelNotSuite.setGone()
                llNormalState.setVisible()
            }
            val drawable = if (item.isCollect) R.drawable.ic_resume_collect else R.drawable.ic_resume_no_collect
            ivCollect.setImageResource(drawable)
            tvGreetOrChat.text = if (item.isGreet) "打招呼" else "继续沟通"
        }

        // 不合适点击
        bottomBar.llNotSuite.onClick {
            callback?.onInappropriateClick()
        }
        // 收藏点击
        bottomBar.llCollect.onClick {
            callback?.onCollectClick()
        }
        // 打电话点击
        bottomBar.llCall.onClick {
            callback?.onCallClick()
        }
        // 打招呼或继续沟通点击
        bottomBar.llGreetOrChat.onClick {
            callback?.onGreetOrChatClick()
        }
        // 撤销不合适点击
        bottomBar.llCancelNotSuite.onClick {
            callback?.onCancelInappropriateClick()
        }
    }

    private fun setErrorLayout(
        binding: ItemResumeRecommendCardBinding,
        item: ResumeRecommendCardState,
    ) {
        if (item.showError) {
            binding.clContent.setGone()
            binding.inError.root.setVisible()
        } else {
            binding.clContent.setVisible()
            binding.inError.root.setGone()
        }
        binding.inError.tvRefresh.onClick {
            currentActivity()?.showToast("刷新")
        }
    }

}