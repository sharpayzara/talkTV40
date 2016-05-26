package com.sumavision.talktv4.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.sumavision.talktv4.R;
import com.sumavision.talktv4.model.entity.Gank;
import com.sumavision.talktv4.presenter.GanHuoFragmentPresenter;
import com.sumavision.talktv4.ui.adapter.GanHuoAdapter;
import com.sumavision.talktv4.ui.fragment.Base.BaseFragment;
import com.sumavision.talktv4.ui.iview.IGanHuoView;
import com.sumavision.talktv4.ui.widget.LMRecyclerView;
import com.sumavision.talktv4.util.TipUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 *  desc  干货fragment
 *  @author  yangjh
 *  created at  16-5-24 下午9:01 
 */
public class GanHuoFragment extends BaseFragment<GanHuoFragmentPresenter> implements IGanHuoView,
        LMRecyclerView.LoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    private static final String TYPE = "type";
    private String type;
    private GanHuoAdapter adapter;
    private List<Gank> gankList;
    private int page = 1;
    private boolean isRefresh = true;
    private boolean canLoading = true;
    private boolean isLoadingMore = false;


    @Bind(R.id.recycler_view)
    LMRecyclerView recyclerView;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    public GanHuoFragment() {
    }

    public static GanHuoFragment newInstance(String type) {
        GanHuoFragment fragment = new GanHuoFragment();
        Bundle args = new Bundle();
        args.putString(TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getString(TYPE);
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_ganhuo;
    }

    @Override
    protected void initPresenter() {
        presenter = new GanHuoFragmentPresenter(getContext(),this);
        presenter.init();
    }

    @Override
    public void showProgressBar() {
        if (!swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(true);
        }
        isLoadingMore = false;
    }

    @Override
    public void hideProgressBar() {
        if (swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void showErrorView() {
        canLoading = true;
        TipUtil.showTipWithAction(recyclerView, getString(R.string.load_failed), getString(R.string.retry), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.loadGank(type,page);
            }
        });
    }

    @Override
    public void showNoMoreData() {
        canLoading = false;
        TipUtil.showSnackTip(recyclerView, getString(R.string.no_more_data));
    }

    @Override
    public void showListView(List<Gank> gankList) {
        canLoading = true;
        if(isLoadingMore){
            ++page;
            isLoadingMore = false;
        }
        if (isRefresh) {
            this.gankList.clear();
            this.gankList.addAll(gankList);
            adapter.notifyDataSetChanged();
            isRefresh = false;
        } else {
            this.gankList.addAll(gankList);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void initView() {
        gankList = new ArrayList<>();
        adapter = new GanHuoAdapter(gankList,getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.setLoadMoreListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent, R.color.blue);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                presenter.loadGank(type,page);
            }
        });
    }

    @Override
    public void loadMore() {
        if (canLoading){
            presenter.loadGank(type,page);
            canLoading = false;
            isLoadingMore = true;
        }
    }

    @Override
    public void onRefresh() {
        isRefresh = true;
        page = 1;
        presenter.loadGank(type,page);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.release();
    }
}
