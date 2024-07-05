package com.example.kotlindemo.task.jobdetail

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.FragmentActivity
import com.example.kotlindemo.compose.ui.ZlTheme
import com.example.kotlindemo.databinding.LayoutJobDetailTopBarBinding
import com.example.kotlindemo.task.afterdelivery.AfterDeliveryDialog
import com.example.kotlindemo.task.afterdelivery.LoginQuestionDialog2
import com.example.kotlindemo.task.login.dialog.NoVerifyCodeDialog
import com.example.kotlindemo.task.login.dialog.NoVerifyCodeDialogState
import com.example.kotlindemo.task.nps.PositionNPSDialog
import com.example.kotlindemo.task.nps.testNPSState
import com.example.kotlindemo.utils.setVisible
import com.zhaopin.social.appbase.util.currentActivity
import com.zhaopin.social.common.extension.isGone
import com.zhaopin.social.common.extension.isVisible
import com.zhaopin.social.common.extension.setGone
import com.zhaopin.social.module_common_util.ext.binding
import com.zhaopin.social.module_common_util.ext.onClick
import com.zhaopin.toast.showToast

/**
 * @Description
 * @Author LuoJia
 * @Date 2023/11/24
 */
class JobDetailTopBar @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attr, defStyleAttr) {

    private val binding: LayoutJobDetailTopBarBinding by binding()

    init {
        setView()
    }

    @OptIn(ExperimentalMaterialApi::class)
    private fun setView() {
        with(binding) {
            ivBack.onClick {
                currentActivity()?.finish()
            }
            ivCollect.onJClick {
                (currentActivity() as? FragmentActivity)?.let {
                    val dialog = AfterDeliveryDialog.newInstance()
                    dialog.show(it.supportFragmentManager, dialog.bottomSheetDialogListener)
                }
            }
//            ivReport.onClick {
//                val dialog = LoginQuestionDialog2(currentActivity()!!)
//                dialog.show()
//            }
            ivReport.onClick {
                (currentActivity() as? FragmentActivity)?.let {
                    val dialog = PositionNPSDialog.newInstance(testNPSState)
                    dialog.show(it.supportFragmentManager)
                }
            }
        }
    }

    fun Activity.showComposeDialog(content: @Composable () -> Unit) {
        val composeView = ComposeView(this)
        val rootView = this.window?.decorView?.findViewById<ViewGroup>((android.R.id.content))
        rootView?.addView(composeView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        composeView.run {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                ZlTheme {
                    content()
                }
            }
        }
    }

    /**
     * 展示标题
     */
    fun showTitle(title: String) {
        if (binding.tvTitle.isVisible()) {
            return
        }
        binding.tvTitle.text = title
        binding.tvTitle.setVisible()
    }

    /**
     * 隐藏标题
     */
    fun hideTitle() {
        if (binding.tvTitle.isGone()) {
            return
        }
        binding.tvTitle.setGone()
    }
}