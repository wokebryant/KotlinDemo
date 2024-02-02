package com.example.kotlindemo.task.ai.util;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

public class RecyclerViewPageChangeListenerHelper extends RecyclerView.OnScrollListener {
    private SnapHelper mSnapHelper;
    private OnPageChangeListener onPageChangeListener;
    private int oldPosition = -1;//防止同一Position多次触发

    public RecyclerViewPageChangeListenerHelper(OnPageChangeListener onPageChangeListener) {
        this.onPageChangeListener = onPageChangeListener;
    }

    public void setSnapHelper(SnapHelper snapHelper){
        this.mSnapHelper = snapHelper;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (onPageChangeListener != null) {
            onPageChangeListener.onScrolled(recyclerView, dx, dy);
        }
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        int position = 0;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        //获取当前选中的itemView
        View view = mSnapHelper.findSnapView(layoutManager);
        if (view != null) {
            //获取itemView的position
            position = layoutManager.getPosition(view);
        }
        if (onPageChangeListener != null) {
            onPageChangeListener.onScrollStateChanged(recyclerView, newState, position);
            //newState == RecyclerView.SCROLL_STATE_IDLE 当滚动停止时触发防止在滚动过程中不停触发
            if (newState == RecyclerView.SCROLL_STATE_IDLE && oldPosition != position) {
                oldPosition = position;
                onPageChangeListener.onPageSelected(position);
            }
        }
    }

    public interface OnPageChangeListener {
        void onScrollStateChanged(RecyclerView recyclerView, int newState, int position);

        void onScrolled(RecyclerView recyclerView, int dx, int dy);

        void onPageSelected(int position);
    }
}