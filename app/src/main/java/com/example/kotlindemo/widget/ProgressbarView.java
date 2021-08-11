package com.example.kotlindemo.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import com.example.kotlindemo.R;
import java.util.ArrayList;
import java.util.List;

public class ProgressbarView extends View {

    private final int DEFAULT_WIDTH = 450;//  长度
    private final int DEFAULT_HEIGTH = 40;// 宽度
    private final int DEFAULT_MAX = 100;
    private final int DEFAULT_PROGRESS = 0;
    private final int DEFAULT_PROGRESS_COLOR = Color.parseColor("#76B034");
    private final int DEFAULT_PROGRESS_BACK_COLOR = Color.parseColor("#EFEFEF");

    private int progress = DEFAULT_PROGRESS;// 队里不为空时为队列最后一个数据
    private int max = DEFAULT_MAX;
    private float mwidth = DEFAULT_WIDTH;
    private float mhight = DEFAULT_HEIGTH;
    private int proColor = DEFAULT_PROGRESS_COLOR;
    private int proBackColor = DEFAULT_PROGRESS_BACK_COLOR;
    private int progressSegmentColor = proColor;
    private int progressDoubleSegColor = Color.GRAY;

    private float startX;// 矩形开始的点
    private float startY;
    private Paint paint;
    private List<Integer> progressList;
    private List<Integer> progressList2;

    public ProgressbarView(Context context) {
        this(context,null);
    }

    public ProgressbarView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ProgressbarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
//        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.ProgressbarView, defStyleAttr, 0);
//        mwidth = attributes.getDimension(R.styleable.ProgressbarView_progressBar_width, DEFAULT_WIDTH);
//        mhight = attributes.getDimension(R.styleable.ProgressbarView_progressBar_height, DEFAULT_HEIGTH);
//        max = attributes.getInteger(R.styleable.ProgressbarView_progressBar_maxValue, DEFAULT_MAX);
//        progress = attributes.getInteger(R.styleable.ProgressbarView_progressBar_progressValue, DEFAULT_PROGRESS);
//
//        proColor = attributes.getColor(R.styleable.ProgressbarView_progressBar_progressColor,DEFAULT_PROGRESS_COLOR);
//        proBackColor = attributes.getColor(R.styleable.ProgressbarView_progressBar_backgroundColor,DEFAULT_PROGRESS_BACK_COLOR);
//        progressSegmentColor = attributes.getColor(R.styleable.ProgressbarView_progressBar_segmentColor,DEFAULT_PROGRESS_COLOR);
//        progressDoubleSegColor = attributes.getColor(R.styleable.ProgressbarView_progressBar_intervalColor,Color.GRAY);

        paint = new Paint();
        progressList = new ArrayList<>();
        progressList2 = new ArrayList<>();

        paint.setAntiAlias(true);// 设置是否抗锯齿
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);// 帮助消除锯齿
        paint.setColor(Color.parseColor("#EFEFEF"));// 设置画笔灰色
//        paint.setStrokeWidth(1);// 设置画笔宽度
        paint.setStyle(Paint.Style.FILL);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setColor(proBackColor);// 设置画笔灰色
//        paint.setStrokeWidth(1);// 设置画笔宽度
        startX = 0;
        startY = 0;

        //绘制背景
        float each = mwidth / 10;
        for (int i = 0; i< 10; i++) {
            canvas.drawRect(startX, startY, startX + each, mhight, paint);
            startX += each;
        }

        //绘制分段
        startX = 0;
        paint.setColor(progressSegmentColor);

        if (progressList2.size() > 0) {
            for (int pro2: progressList2) {
                canvas.drawRect(startX, startY, ((float) pro2 / max) * mwidth,
                        mhight, paint);
                startX = ((float) pro2/max) * mwidth;
            }
        }

        //绘制进度
        startX = 0;
        paint.setColor(proColor);

        if(progressList.size() > 0){
            for(int pro : progressList){
                canvas.drawRect(startX, startY, ((float) pro / max) * mwidth,
                        mhight, paint);
                startX = ((float) pro/max) * mwidth;
            }
        }

//        paint.setStrokeWidth(1);// 设置画笔宽度
        paint.setColor(progressDoubleSegColor);
        int step2 = 10;
        for(int i = 0;i<9;i++){
            canvas.drawLine(((float) step2 / max) * mwidth,startY,((float) step2 / max) * mwidth, startY + (mhight - startY),paint);
            step2 += 10;
        }
    }


    // - - - - - - - - - - - - -  public - - - - - - - - - - - - - - - - -


    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        setProgress(progress,false);
    }

    public void setProgress(int progress,boolean needDel) {
        if(progress <= max){
            this.progress = progress;
            if(needDel){
                progressList.add(progress);
            }
            invalidate();
        }
    }

    public void setProgress2(int progress,boolean needDel) {
        if(progress <= max){
//            this.progress = progress;
            if(needDel){
                progressList2.add(progress);
            }
            invalidate();
        }
    }
    public void removeProgress(int progres) {
        if(progressList.size() > 0){
            progressList.remove(progressList.size() - 1 );
            if(progressList.size() == 0){
                progress = DEFAULT_PROGRESS;
            }else {
                progress = progressList.get(progressList.size() - 1);
            }
        }else{
            progress = progres;
        }
        invalidate();
    }


}

