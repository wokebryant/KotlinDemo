package com.example.kotlindemo.task.microenterprises.view.page

import androidx.lifecycle.viewModelScope
import com.example.kotlindemo.study.mvi.core.MviBaseViewModel
import com.example.kotlindemo.task.microenterprises.MicroResumeListEvent
import com.example.kotlindemo.task.microenterprises.MicroResumeListState
import com.example.kotlindemo.task.microenterprises.data.page.MicroResumeListRepository
import com.zhaopin.social.appbase.util.curContext
import com.zhaopin.social.module_common_util.log.LogKitty
import com.zhaopin.toast.showToast
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

/**
 * @Description 小微企业简历列表ViewModel
 * @Author LuoJia
 * @Date 2023/8/29
 */
class MicroResumeListViewModel(
    private val repository: MicroResumeListRepository = MicroResumeListRepository()
) : MviBaseViewModel<MicroResumeListState, MicroResumeListEvent>() {

    override fun createInitialState() = MicroResumeListState()

    fun requestList() {
        val state = viewModelScope.isActive
        curContext.showToast(state.toString())

        viewModelScope.launch {
            // TODO 网络请求
            LogKitty.i("MicroResumeListViewModel", "doRequest")
        }
    }

}