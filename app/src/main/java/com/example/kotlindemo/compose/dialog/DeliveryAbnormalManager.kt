package com.example.kotlindemo.compose.dialog

import com.example.kotlindemo.compose.ext.showComposeDialog
import com.example.kotlindemo.task.linkage.KeepProtocol
import com.google.gson.Gson
import com.zhaopin.social.appbase.util.currentActivity
import com.zhaopin.social.module_common_util.log.GsonUtils
import com.zhaopin.social.module_common_util.log.JsonUtils
import org.json.JSONObject

/**
 * @Description
 * @Author LuoJia
 * @Date 2024/8/21
 * https://wiki.zhaopin.com/pages/viewpage.action?pageId=177282381
 */
object DeliveryAbnormalManager {

    fun showDeliveryAbnormalDialog(data: Any?) {
        try {
            (data as? JSONObject)?.let {
                val bean = Gson().fromJson(it.toString(), DeliveryAbnormalBean::class.java)
                val dialogState = DeliveryAbnormalState(
                    title = bean.title.orEmpty(),
                    content = bean.content.orEmpty(),
                    showCountDown = bean.punishState == 1,
                    unLockTime = bean.unlockTime ?: 0,
                    isCallService = bean.buttonType == 2
                )
//                currentActivity()?.showComposeDialog {
//                    DeliveryAbnormalDialog(
//                        show = true,
//                        state = dialogState
//                    )
//                }
                currentActivity()?.let { activity ->
                    val dialog = DeliveryAbnormalDialog2(activity, dialogState)
                    dialog.show()
                }

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    data class DeliveryAbnormalBean (
        val punishState: Int?,
        val unlockTime: Long?,
        val uncivilizedCount: Int?,
        val title: String?,
        val content: String?,
        val buttonType: Int?
    ) : KeepProtocol

}

data class DeliveryAbnormalState(
    val title: String,
    val content: String,
    val showCountDown: Boolean,
    val unLockTime: Long,
    val isCallService: Boolean,
)

