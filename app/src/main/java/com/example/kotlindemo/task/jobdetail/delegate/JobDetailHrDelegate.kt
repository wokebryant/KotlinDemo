package com.example.kotlindemo.task.jobdetail.delegate

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.kotlindemo.R
import com.example.kotlindemo.databinding.JobDetailItemHrBinding
import com.example.kotlindemo.databinding.JobDetailItemHrTagBinding
import com.example.kotlindemo.task.jobdetail.HrState
import com.zhaopin.list.multitype.adapter.MultiTypeAdapter
import com.zhaopin.list.multitype.adapter.setList
import com.zhaopin.list.multitype.binder.BindingViewDelegate
import com.zhaopin.social.background.util.Bovb
import com.zhaopin.social.common.extension.getColor
import com.zhaopin.social.common.extension.setGone
import com.zhaopin.social.common.extension.setVisible
import com.zhaopin.social.module_common_util.ext.dp

/**
 * @Description 职位详情页HR卡
 * @Author LuoJia
 * @Date 2023/11/22
 */
class JobDetailHrDelegate : BindingViewDelegate<HrState, JobDetailItemHrBinding>() {

    /** 标签适配器 */
    private val listAdapter by lazy {
        MultiTypeAdapter().apply { register(HrTagDelegate()) }
    }

    override fun onBindViewHolder(binding: JobDetailItemHrBinding, item: HrState, position: Int) {
        setView(binding, item)
    }

    @SuppressLint("SetTextI18n")
    private fun setView(binding: JobDetailItemHrBinding, item: HrState) {
        Log.i("LuoJia-RE", "JobDetailHrDelegate")

        with(binding) {
            // 加载头像和在线状态
            ivAvatar.load(item.avatarUrl) {
                placeholder(R.drawable.c_common_icon_hr_new_default)
                error(R.drawable.c_common_icon_hr_new_default)
            }
            ivOnline.visibility = if (item.isOnline) View.VISIBLE else View.GONE
            // 设置姓名和标签
            setNameAndTag(binding, item)

        }
    }

    @SuppressLint("SetTextI18n")
    private fun setHrCompanyText(binding: JobDetailItemHrBinding, item: HrState) {
        binding.tvHrJob.text = item.job
        binding.tvHrCompany.run {
            visibility = if (item.companyName.isNotEmpty()) View.VISIBLE else View.GONE
            text = " · ${item.companyName}"
//            post {
//                val hrJobPaintWidth = paint.measureText()
//            }
        }
    }

    /**
     * 设置姓名-标签
     * 没有标签时姓名全部展示
     * 只有一个标签时: 姓名过长展示...
     * 两个及以上标签：优先展示姓名，名字过长截取6个字加...
     */
    private fun setNameAndTag(binding: JobDetailItemHrBinding, item: HrState) {
        when(item.tagList.size) {
            0 -> {
                binding.tvName.text = item.name
                binding.rvHrTag.setGone()
            }
            1,2 -> {
                binding.tvName.text = item.name
                binding.tvName.run {
                    post {
                        val textPaintWidth = paint.measureText(item.name)
                        // 如果TextView宽度比文本实际宽度更宽，重新计算
                        if (width > textPaintWidth) {
                            updateLayoutParams<LinearLayout.LayoutParams> {
                                weight = 0f
                                width = textPaintWidth.toInt()
                            }
                        }
                    }
                }
                binding.rvHrTag.adapter = listAdapter
                listAdapter.setList(item.tagList)
                binding.rvHrTag.setVisible()
            }
            else -> {
                binding.tvName.updateLayoutParams<LinearLayout.LayoutParams> {
                    weight = 0f
                    width = ViewGroup.LayoutParams.WRAP_CONTENT
                }
                binding.rvHrTag.updateLayoutParams<LinearLayout.LayoutParams> {
                    weight = 1f
                    width = 0.dp
                }
                binding.tvName.text = item.name.limit6Char()
                binding.rvHrTag.run {
                    setVisible()
                    adapter = listAdapter
                    listAdapter.setList(item.tagList)
                    // 判断最后一个标签是否完全展示，没有完全展示则删除多余标签
                    addOnScrollListener(object : RecyclerView.OnScrollListener() {
                        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                            super.onScrolled(recyclerView, dx, dy)
                            val manger = binding.rvHrTag.layoutManager as LinearLayoutManager
                            val lastVisibleItemPosition = manger.findLastCompletelyVisibleItemPosition()
                            val itemCount = manger.itemCount
                            if (lastVisibleItemPosition < itemCount - 1) {
                                val newList = item.tagList.toMutableList()
                                newList.removeLast()
                                post {
                                    listAdapter.setList(newList)
                                }
                            }
                        }
                    })
                }
            }
        }
    }

    inner class HrTagDelegate : BindingViewDelegate<String, JobDetailItemHrTagBinding>() {

        override fun onBindViewHolder(
            binding: JobDetailItemHrTagBinding,
            item: String,
            position: Int
        ) {
            binding.flRoot.run {
                background = Bovb.with()
                    .radius(4f.dp)
                    .borderWidth(0.5f.dp.toInt())
                    .borderColor(getColor(R.color.C_B7))
                    .build()
            }
            binding.tvDesc.text = item
        }

    }

}

internal fun String.limit6Char() =
    if (this.isNotEmpty() && this.length > 6) this.substring(0, 6) + "..." else this