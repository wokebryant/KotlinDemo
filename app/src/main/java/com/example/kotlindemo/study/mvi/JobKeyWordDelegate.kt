package com.example.kotlindemo.study.mvi

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlindemo.R
import com.example.kotlindemo.activity.linkage.KeepProtocol
import com.example.kotlindemo.activity.linkage.origin.FlowLayoutOrigin1
import com.example.kotlindemo.activity.linkage.origin.TagAdapterOrigin1
import com.example.kotlindemo.databinding.BHomeItemJobKeywordBinding
import com.example.kotlindemo.databinding.BHomeItemJobKeywordTagBinding
import com.example.kotlindemo.task.negavition.ContentRecommendDetailDialog
import com.example.kotlindemo.task.negavition.PositionNegativeFeedbackPanel
import com.example.kotlindemo.task.negavition.getMockData
import com.example.kotlindemo.utils.copyOf
import com.example.kotlindemo.utils.getColor
import com.example.kotlindemo.utils.setGone
import com.zhaopin.list.multitype.adapter.MultiTypeAdapter
import com.zhaopin.list.multitype.adapter.setList
import com.zhaopin.list.multitype.binder.BindingViewDelegate
import com.zhaopin.social.appbase.util.curContext
import com.zhaopin.social.background.util.Bovb
import com.zhaopin.social.common.extension.setVisible
import com.zhaopin.social.module_common_util.ext.dp
import com.zhaopin.social.module_common_util.ext.onClick
import com.zhaopin.toast.showToast

/**
 * @Description 关键词入口Item
 * @Author LuoJia
 * @Date 2023/7/28
 */
class JobKeyWordDelegate(private val context: Context) : BindingViewDelegate<JobKeyWordBean, BHomeItemJobKeywordBinding>() {

    private val tagsAdapter by lazy {
        MultiTypeAdapter().apply {
            register(TagViewDelegate())
        }
    }

    override fun onBindViewHolder(
        binding: BHomeItemJobKeywordBinding,
        item: JobKeyWordBean,
        position: Int
    ) {
        with(binding) {
            ivDelete.onClick {

            }

            llEdit.onClick {
                (context as? FragmentActivity)?.supportFragmentManager?.let {
//                    PositionNegativeFeedbackPanel.newInstance(getMockData()).show(it)
                    ContentRecommendDetailDialog.newInstance().show(it)
                }
            }

            // 设置适配器
            val tagStringList = mutableListOf("农副产品加工", "1", "234", "1", "2344", "...")
            val newTagStringList = tagStringList.map {
                if (it.length > 6) {
                    it.substring(0, 6) + "..."
                } else {
                    it
                }
            }
            val tagAdapter = object : TagAdapterOrigin1<String>(newTagStringList) {
                override fun getView(parent: FlowLayoutOrigin1, position: Int, str: Any): View {
                    val tagView: View
                    if (str == "...") {
                        tagView = LayoutInflater.from(context)
                            .inflate(R.layout.b_home_item_keyword_tag_img, parent, false) as ImageView
                    } else {
                        tagView = LayoutInflater.from(context)
                            .inflate(R.layout.b_home_item_keyword_tag_text, parent, false) as TextView
                        tagView.text = str as String
                    }

                    return tagView.apply {
                        background = Bovb.with().color(getColor(R.color.C_B7)).radius(8.dp.toFloat()).build()
                    }
                }
            }
            rvTag.adapter = tagAdapter

            rvTag1.run {
                if (item.keywords.isNullOrEmpty()) {
                    return
                }
//                alpha = 0f
//                setInvisible()
//                viewMask.setVisible()
                setHasFixedSize(true)
                itemAnimator = null

                adapter = tagsAdapter
                // 转换Tag信息
                val tagInfoList = mutableListOf<Any>()
                item.keywords.forEach {
                    it.tags?.forEach { tag ->
                        val tagInfo = Pair(tag.id, tag.name)
                        tagInfoList.add(tagInfo)
                    }
                }
                tagsAdapter.setList(tagInfoList)
                // 添加滑动监听
                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        val manger = layoutManager as LinearLayoutManager
                        val lastVisibleItemPosition = manger.findLastCompletelyVisibleItemPosition()
                        val itemCount = manger.itemCount
                        if (lastVisibleItemPosition < itemCount - 1) {
                            // 最后一项没有完全显示
                            val newList = (tagInfoList.copyOf() as MutableList).apply {
                                removeLast()
                                add(R.drawable.resume_recommend_ellipsis)
                            }
                            post {
                                tagsAdapter.setList(newList)
//                                ObjectAnimator.ofFloat(rvTag, "Alpha", 0f, 1f).apply {
//                                    duration = 500
//                                    start()
//                                    addListener(onEnd = {
//                                        rvTag.setVisible()
//                                    })
//                                }
//                                postDelayed({ viewMask.setGone() }, 100)
                            }
                        } else {
                            setVisible()
                        }
                    }

                })
            }
        }
    }

    inner class TagViewDelegate : BindingViewDelegate<Any, BHomeItemJobKeywordTagBinding>() {

        override fun onBindViewHolder(
            binding: BHomeItemJobKeywordTagBinding,
            item: Any,
            position: Int
        ) {
            with(binding) {
                root.run {
                    background = Bovb.with().color(getColor(R.color.C_B7)).radius(8.dp.toFloat()).build()
                }
                when(item) {
                    is Pair<*, *> -> {
                        ivTag.setGone()
                        tvTag.setVisible()
                        tvTag.text= item.second as String
                    }

                    is Int -> {
                        ivTag.setVisible()
                        tvTag.setGone()
                    }
                }
            }
        }

    }

}

data class JobKeyWordBean (
    val guide: Boolean,
    val title: String,
    val subTitle: String,
    val keywords: List<JobKeyWordItem>?
) : KeepProtocol

data class JobKeyWordItem(
    val pathId: String,
    val type: Int,
    val tags: List<JobKeyWordTagItem>?
) : KeepProtocol

data class JobKeyWordTagItem(
    val id: String,
    val name: String,
    val standard: Boolean
) : KeepProtocol