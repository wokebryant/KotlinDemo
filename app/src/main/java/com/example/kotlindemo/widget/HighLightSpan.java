package com.example.kotlindemo.widget;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.style.ReplacementSpan;

/**
 * 自定义高亮文本ReplacementSpan
 * 解决系统提供高亮包含行高问题
 */
public class HighLightSpan extends ReplacementSpan {
    private final int mColor;
    private final int mTextHeight;

    public HighLightSpan(int color, int textHeight) {
        mColor = color;
        mTextHeight = textHeight;
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        return Math.round(measureText(paint, text, start, end));
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int b, Paint paint) {
        int bottom = top + mTextHeight;
        float right = x + measureText(paint, text, start, end);
        // 画高亮背景色
        int paintColor = paint.getColor();
        RectF rect = new RectF(x, top, right, bottom);
        RectF oval = new RectF(x, y + paint.ascent(), right, y + paint.descent());
        paint.setColor(mColor);
//        canvas.drawRect(rect, paint);//绘制矩形
        canvas.drawRoundRect(oval,2,2,paint);//绘制圆角矩形
        paint.setColor(paintColor);
        canvas.drawText(text, start, end, x, y, paint);


    }

    private float measureText(Paint paint, CharSequence text, int start, int end) {
        return paint.measureText(text, start, end);
    }
}
