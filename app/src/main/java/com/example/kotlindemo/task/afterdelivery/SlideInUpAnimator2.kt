package com.example.kotlindemo.task.afterdelivery

import android.view.animation.Interpolator
import androidx.recyclerview.widget.RecyclerView
import com.zhaopin.social.module_common_util.ext.dp
import jp.wasabeef.recyclerview.animators.BaseItemAnimator

/**
 * @Description
 * @Author LuoJia
 * @Date 2024/06/21
 */
open class SlideInUpAnimator2 : BaseItemAnimator {
    constructor()
    constructor(interpolator: Interpolator) {
        this.interpolator = interpolator
    }

    override fun animateRemoveImpl(holder: RecyclerView.ViewHolder) {
        holder.itemView.animate().apply {
            translationY(holder.itemView.height.toFloat() + 60.dp)
            alpha(0f)
            duration = 500
            interpolator = interpolator
            setListener(DefaultRemoveAnimatorListener(holder))
            startDelay = getRemoveDelay(holder)
        }.start()
    }

    override fun preAnimateAddImpl(holder: RecyclerView.ViewHolder) {
        holder.itemView.translationY = holder.itemView.height.toFloat() + 60.dp
        holder.itemView.alpha = 0f
    }

    override fun animateAddImpl(holder: RecyclerView.ViewHolder) {
        holder.itemView.animate().apply {
            translationY(0f)
            alpha(1f)
            duration = 300
            interpolator = interpolator
            setListener(DefaultAddAnimatorListener(holder))
            startDelay = getAddDelay(holder)
        }.start()
    }
}