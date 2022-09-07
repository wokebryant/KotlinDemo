package com.example.kotlindemo.task.mutildelivery.rankmulti

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlindemo.R
import com.example.kotlindemo.databinding.AdapterPositionRankMultiBinding
import com.example.kotlindemo.utils.copyOf
import com.example.kotlindemo.utils.getDrawable

/**
 * @Description 职位榜单批投卡片
 * @Author LuoJia
 * @Date 2022/8/29
 */
class PositionRankMultiView @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attr, defStyleAttr) {

    companion object {
        private const val TAG = "PositionRankMultiView"
    }

    private val binding: AdapterPositionRankMultiBinding by lazy {
        DataBindingUtil.bind(inflate(context, R.layout.adapter_position_rank_multi, this).apply {
            tag = "layout/adapter_position_rank_multi_0"
        })!!
    }

    private lateinit var multiAdapter: PositionRankMultiAdapter

    private var jobList: ArrayList<PositionRankMultiModel> = arrayListOf()
    private var checkJobList: ArrayList<PositionRankMultiModel> = arrayListOf()
    private var checkJobListSize: Int = 0
        @SuppressLint("SetTextI18n")
        set(value) {
            field = value
            if (value == 0) {
                binding.tvMultiDelivery.run {
                    isEnabled = false
                    text = "请选择职位"
                    background = getDrawable(R.drawable.module_common_solid_bccbfc)
                }
            } else {
                binding.tvMultiDelivery.run {
                    isEnabled = true
//                    text = "投递${value}个职位"
                    text = context.getString(R.string.delivery_job, value.toString())
                    background = getDrawable(R.drawable.module_common_solid_587cf7)
                }
            }
        }


    /**
     * 复选按钮选择回调
     */
    private val onMultiItemCheckCallback = {
        if (jobList.isNotEmpty()) {
            updateSelectedJobList()
        }
    }

    /**
     * 职位详情点击
     */
    private val onMultiItemJobDetailCallback: (Int) -> Unit = {
        Toast.makeText(context, "$it", Toast.LENGTH_SHORT).show()
    }

    init {
        with(binding) {
            initData()
            initView()
        }
    }

    private fun initData() {
        jobList = getTestData()
        checkJobList = jobList.copyOf() as ArrayList
        checkJobListSize = checkJobList.size
    }

    private fun initView() {
        binding.rvMultiJob.run {
            multiAdapter = PositionRankMultiAdapter(
                jobList,
                onMultiItemCheckCallback,
                onMultiItemJobDetailCallback
            )
            adapter = multiAdapter
            layoutManager = LinearLayoutManager(context)
        }
        binding.tvMultiNext.setOnClickListener { showNext() }
        binding.tvMultiDelivery.setOnClickListener { updateAfterDelivery() }
        initExpose()
    }

    /**
     * 过滤职位列表
     */
    private fun filterJobList() {

    }

    /**
     * 更新选中的列表
     */
    private fun updateSelectedJobList() {
        if (checkJobList.isNotEmpty()) {
            checkJobList.clear()
        }
        jobList.forEach { model ->
            if (model.checkState == CheckState.Check) {
                checkJobList.add(model)
            }
        }
        checkJobListSize = checkJobList.size
    }

    /**
     * 展示下一个职位卡片
     */
    private fun showNext() {

    }

    /**
     * 更新投递后的状态
     */
    @SuppressLint("NotifyDataSetChanged")
    private fun updateAfterDelivery() {
        jobList.forEach { model ->
            if (model.checkState == CheckState.Check) {
                model.checkState = CheckState.Gray
            }
        }
        multiAdapter.isDelivered = true
        multiAdapter.notifyDataSetChanged()
        checkJobListSize = 0
    }

    private fun getTestData(): ArrayList<PositionRankMultiModel> {
        val list = ArrayList<PositionRankMultiModel>()
        for (i in 0 until 5) {
            val model = PositionRankMultiModel(
                job = "智能硬件智能硬件智能硬件",
                salary = "1.5-3.5万",
                company = "小米公司.1000人以上",
                workYear = "3-5年",
                education = "本科/研究生",
                skill = "数据分析",
                checkState = CheckState.Check
            )
            list.add(model)
        }
        return list
    }

    private fun initExpose() {
        RVItemExposureListener(binding.rvMultiJob, object : RVItemExposureListener.IOnExposureListener {
            override fun onExposure(position: Int) {
                // 滑动过程中出现的条目
                Log.d("exposure-curPosition:", position.toString())
            }

            override fun onUpload(exposureList: List<Int>?): Boolean {
                Log.d("exposure-positionList", exposureList.toString())
                // 上报成功后返回true
                return true
            }

        })
    }




}