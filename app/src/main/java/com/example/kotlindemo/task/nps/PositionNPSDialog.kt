package com.example.kotlindemo.task.nps

import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import com.example.kotlindemo.R
import com.example.kotlindemo.databinding.LayoutNpsFaceItemBinding
import com.example.kotlindemo.databinding.LayoutPositionNpsBinding
import com.example.kotlindemo.utils.SizeUtils
import com.example.kotlindemo.utils.setVisible
import com.example.kotlindemo.widget.BaseBottomSheetDialogFragment
import com.zhaopin.social.appbase.util.curContext
import com.zhaopin.social.appbase.util.currentActivity
import com.zhaopin.social.common.extension.setGone
import com.zhaopin.social.module_common_util.common.StatusBarUtils
import com.zhaopin.social.module_common_util.ext.dp
import com.zhaopin.social.module_common_util.ext.onClick

/**
 * @Description 职位NPS弹窗
 * @Author LuoJia
 * @Date 2024/07/05
 */
class PositionNPSDialog : BaseBottomSheetDialogFragment<LayoutPositionNpsBinding>() {

    companion object {
        fun newInstance(state: PositionNPSState) = PositionNPSDialog().apply {
            arguments = Bundle().apply {
                putSerializable("data", state)
            }
        }
    }

    private var state: PositionNPSState? = null

    private val tagAdapter by lazy { PositionNPSTagAdapter() }
    /** 未被选中的Face图片 */
    private val unSelectedFaceList = listOf(
        R.drawable.ic_nps_gray_face_1,
        R.drawable.ic_nps_gray_face_2,
        R.drawable.ic_nps_gray_face_3,
        R.drawable.ic_nps_gray_face_4,
        R.drawable.ic_nps_gray_face_5,
    )
    /** 被选中的Face图片 */
    private val selectedFaceList = listOf(
        R.drawable.ic_nps_light_face_1,
        R.drawable.ic_nps_light_face_2,
        R.drawable.ic_nps_light_face_3,
        R.drawable.ic_nps_light_face_4,
        R.drawable.ic_nps_light_face_5,
    )
    /** 背景图片 */
    private val bgList = listOf(
        R.drawable.ic_nps_bg_very_bad,
        R.drawable.ic_nps_bg_bad,
        0,
        R.drawable.ic_nps_bg_satisfy,
        R.drawable.ic_nps_bg_very_satisfy
    )
    /** Face binding */
    private val faceBindingList by lazy {
        listOf(binding.inFace1, binding.inFace2, binding.inFace3, binding.inFace4, binding.inFace5)
    }
    /** Face 文案 */
    private val faceDesc = listOf("非常差", "不满意", "一般", "满意", "非常好")
    /** 被选中的下标 */
    private var selectedIndex = -1
    /** 按钮是否可点 */
    private var buttonEnable = false
        set(value) {
            field = value
            setButtonState()
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        state = arguments?.getSerializable("data") as? PositionNPSState
        selectedIndex = state?.selectedIndex ?: -1
        // 设置弹窗属性
        setDialogParams()
        // 初始化分数
        initFaceLayout()
        // 设置被选择的分数状态
        setInitSelectedFace()
        // 按钮状态
        setButtonState()
        // 监听
        listener()
    }

    private fun setDialogParams() {
        outsideClickClose = true
        disableBackClose = false
        val screenHeight = SizeUtils.getMetricsFull().heightPixels
        val statusBarHeight = StatusBarUtils.getStatusBarHeight()
        val navBarHeight = if (SizeUtils.isNavigationBarExist(currentActivity())) SizeUtils.getNavBarHeight(curContext) else 0
        val topMargin = 44.dp
        val maxHeight = screenHeight - statusBarHeight - navBarHeight - topMargin
        setMaxHeight(maxHeight)

        binding.root.clipToOutline = true
        binding.root.outlineProvider = TopRoundRectOutlineProvider(16.dp.toFloat())
    }

    private fun initFaceLayout() {
        faceBindingList.indices.forEach {
            setFace(index = it)
        }
    }

    private fun setInitSelectedFace() {
        if (selectedIndex != -1) {
            setFace(index = selectedIndex, isSelected = true)
        }
    }

    private fun setFace(
        index: Int,
        isSelected: Boolean = false,
    ) {
        val faceBinding = faceBindingList[index]

        val drawable = if (isSelected) selectedFaceList[index] else unSelectedFaceList[index]
        val typeface = if (isSelected) Typeface.DEFAULT_BOLD else Typeface.DEFAULT
        faceBinding.ivFace.setImageResource(drawable)
        faceBinding.tvDesc.text = faceDesc[index]
        faceBinding.tvDesc.typeface = typeface
        if (selectedIndex != -1) {
            binding.ivBg.setBackgroundResource(bgList[index])
        }
    }

    private fun setTagLayout() {
        binding.rvTag.run {
            adapter = tagAdapter
            itemAnimator = null
        }
        val tagList = state?.faceList?.getOrNull(selectedIndex)?.tagList
        if (selectedIndex != -1 && !tagList.isNullOrEmpty()) {
            binding.llTag.setVisible()
            tagAdapter.submitList(tagList)
        } else {
            binding.llTag.setGone()
        }
    }

    private fun setButtonState() {
        binding.flSubmit.isEnabled = buttonEnable
    }

    private fun listener() {
        // 关闭点击
        binding.ivClose.onClick {
            dismissAllowingStateLoss()
        }
        // 分数点击
        binding.inFace1.root.onClick {
            onFaceClick(index = 0)
        }
        binding.inFace2.root.onClick {
            onFaceClick(index = 1)
        }
        binding.inFace3.root.onClick {
            onFaceClick(index = 2)
        }
        binding.inFace4.root.onClick {
            onFaceClick(index = 3)
            buttonEnable = true
        }
        binding.inFace5.root.onClick {
            onFaceClick(index = 4)
            buttonEnable = true
        }
    }

    /**
     * 分数点击
     */
    private fun onFaceClick(index: Int) {
        selectedIndex = index
        initFaceLayout()
        setFace(index = index, isSelected = true)
        setTagLayout()
    }

}