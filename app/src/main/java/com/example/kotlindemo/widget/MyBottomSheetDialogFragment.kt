package com.example.kotlindemo.widget

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.example.kotlindemo.R
import com.example.kotlindemo.activity.MainActivity
import com.example.kotlindemo.databinding.LayoutSelectScreeningBinding
import com.example.kotlindemo.utils.AppUtil
import com.example.kotlindemo.utils.binding
import com.example.kotlindemo.utils.dp
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * @Author: LuoJia
 * @Date:
 * @Description:
 */
class MyBottomSheetDialogFragment : BaseBottomSheetDialogFragment() {

    companion object {
        private const val TAG = "MyBottomSheetDialogFrag"

        fun newInstance(data: ArrayList<*>) = MyBottomSheetDialogFragment().apply {
            arguments = Bundle().apply {
                putSerializable("extra_data", data)
            }
        }
    }

    private lateinit var binding: LayoutSelectScreeningBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LayoutSelectScreeningBinding.inflate(inflater)
        allowBackClose = false
        val data = arguments?.getSerializable("extra_data") as ArrayList<*>
        val str = data[0] as String
        binding.confirmBtn.text = str
        return binding.root
    }

}