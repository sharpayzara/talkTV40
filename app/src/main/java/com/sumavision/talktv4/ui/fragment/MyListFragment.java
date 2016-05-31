package com.sumavision.talktv4.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sumavision.talktv4.R;
import com.sumavision.talktv4.dao.MyItemTouchCallback;
import com.sumavision.talktv4.dao.OnRecyclerItemClickListener;
import com.sumavision.talktv4.model.entity.ClassifyItem;
import com.sumavision.talktv4.ui.adapter.RecyclerAdapter;
import com.sumavision.talktv4.util.VibratorUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/12.
 */
public class MyListFragment extends Fragment {

    private List<ClassifyItem> results = new ArrayList<ClassifyItem>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for (int i=0;i<3;i++){
            results.add(new ClassifyItem(i*8+0,"收款", ""));
            results.add(new ClassifyItem(i*8+1,"转账", ""));
            results.add(new ClassifyItem(i*8+2,"余额宝", ""));
            results.add(new ClassifyItem(i*8+3,"手机充值",""));
            results.add(new ClassifyItem(i*8+4,"医疗", ""));
            results.add(new ClassifyItem(i*8+5,"彩票", ""));
            results.add(new ClassifyItem(i*8+6,"电影", ""));
            results.add(new ClassifyItem(i*8+7,"游戏", ""));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return new RecyclerView(container.getContext());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerAdapter adapter = new RecyclerAdapter(R.layout.draglistsetting_item_layout,results);

        RecyclerView recyclerView = (RecyclerView)view;
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new MyItemTouchCallback(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);

        recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(recyclerView) {
            @Override
            public void onLongClick(RecyclerView.ViewHolder vh) {
                if (vh.getLayoutPosition()!=results.size()-1) {
                    itemTouchHelper.startDrag(vh);
                    VibratorUtil.Vibrate(getActivity(), 70);   //震动70ms
                }
            }
        });
    }
}
