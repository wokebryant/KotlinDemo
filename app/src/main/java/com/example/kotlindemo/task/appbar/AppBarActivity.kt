package com.example.kotlindemo.task.appbar

import android.graphics.Color
import android.os.Bundle
import com.example.kotlindemo.R
import com.example.kotlindemo.base.BaseActivity
import com.example.kotlindemo.databinding.ActivityAppbarBinding
import com.example.kotlindemo.utils.binding
import com.example.kotlindemo.utils.dp
import com.example.kotlindemo.utils.setVisible
import com.view.text.addTag
import com.view.text.config.Orientation
import com.view.text.config.TagConfig
import com.view.text.config.Type

/**
 * @Description
 * @Author
 * @Date
 */
class AppBarActivity : BaseActivity() {

    private val binding: ActivityAppbarBinding by binding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {
//            appbar.setVisible()
            appbar2.setViewPager()
//            appbar2.setViewPager2(lifecycle)

            //3.5 自定义显示位置
            val tv5Config = TagConfig(Type.IMAGE).apply {
                imageResource = R.drawable.icon_5
                imageAlignText = Orientation.RIGHT
//                text = "绑定"
                position = textImageTv5.text.length
//                startGradientBackgroundColor = Color.parseColor("#FFA07A")
//                endGradientBackgroundColor = Color.parseColor("#FF0040")
//                textMarginImage = 4.dp.toInt()
            }
            textImageTv5.addTag(tv5Config)
        }
    }


}