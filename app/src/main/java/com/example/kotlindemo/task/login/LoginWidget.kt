package com.example.kotlindemo.task.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kotlindemo.R
import com.example.kotlindemo.compose.ext.noRipple
import com.example.kotlindemo.compose.widget.SimpleImage
import com.zhaopin.social.compose.ui.ZlColor

/**
 * @Description
 * @Author LuoJia
 * @Date 2024/05/28
 */

/**
 * 登录遇到问题联系客服
 */
@Composable
internal fun LoginCallCustomServiceText(
    onClick: () -> Unit
) {
    val annotatedString = buildAnnotatedString {
        withStyle(style = SpanStyle(color = ZlColor.C_B3, fontSize = 14.sp)) {
            append("如需帮助，可")
        }

        pushStringAnnotation(
            tag = "tag1",
            annotation = "联系客服"
        )
        withStyle(style = SpanStyle(color = ZlColor.C_P1, fontSize = 14.sp)) {
            append("联系客服")
        }
        pop()
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        ClickableText(
            text = annotatedString,
            onClick = { offset ->
                annotatedString.getStringAnnotations(
                    tag = "tag1",
                    start = offset,
                    end = offset
                ).firstOrNull()?.let {
                    onClick.invoke()
                }
            },
        )
    }
}

@Composable
fun P1StyleButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    paddingHorizontal: Dp = 60.dp,
    shapeSize: Dp = 22.dp,
    enable: Boolean = true
) {
    Button(
        onClick = { if (enable) onClick.invoke() },
        modifier = Modifier
            .then(modifier)
            .fillMaxWidth()
            .height(44.dp)
            .padding(horizontal = paddingHorizontal),
        elevation = null,
        shape = RoundedCornerShape(size = shapeSize),
        colors = ButtonDefaults.buttonColors(
            backgroundColor =if (enable) ZlColor.C_P1 else ZlColor.C_P5
        ),
        interactionSource = noRipple
    ) {
        SimpleImage(
            id = R.drawable.ic_delivery_abnormal_call,
            modifier = Modifier.padding(end = 4.dp).size(20.dp)
        )
        Text(
            text = text,
            color = ZlColor.C_W1,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}