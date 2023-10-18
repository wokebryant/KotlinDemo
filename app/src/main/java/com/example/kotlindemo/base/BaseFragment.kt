package com.example.kotlindemo.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.zhaopin.social.module_common_util.binding.FragmentBinding
import com.zhaopin.social.module_common_util.binding.FragmentBindingDelegate

/**
 * @Description
 * @Author LuoJia
 * @Date 2023/8/31
 */
abstract class BaseFragment <VB : ViewBinding> : Fragment(),
    FragmentBinding<VB> by FragmentBindingDelegate() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return createViewWithBinding(inflater, container)
    }
}