package com.sumavision.talktv4.ui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
/**
 *  desc  添加加载更多功能
 *  @author  yangjh
 *  created at  16-5-23 下午4:36
 */
public class LMRecyclerView extends RecyclerView {

    private boolean isScrollingToBottom = true;
    private LoadMoreListener listener;

    public LMRecyclerView(Context context) {
        super(context);
    }

    public LMRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public LMRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setLoadMoreListener(LoadMoreListener loadMoreListener){
        this.listener = loadMoreListener;
    }


    @Override
    public void onScrolled(int dx, int dy) {
        isScrollingToBottom = dy > 0;
    }

    @Override
    public void onScrollStateChanged(int state) {
       LinearLayoutManager layoutManager = (LinearLayoutManager) getLayoutManager();
        if (state == RecyclerView.SCROLL_STATE_IDLE) {
            int lastVisibleItem = layoutManager.findLastCompletelyVisibleItemPosition();
            int totalItemCount = layoutManager.getItemCount();
            if (lastVisibleItem == (totalItemCount - 1) && isScrollingToBottom) {
                if (listener != null)
                    listener.loadMore();
            }
        }
    }

    public interface LoadMoreListener {
        void loadMore();
    }
}
