package com.sumavision.talktv4.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sumavision.talktv4.R;
import com.sumavision.talktv4.common.ACache;
import com.sumavision.talktv4.common.DividerGridItemDecoration;
import com.sumavision.talktv4.dao.MyItemTouchCallback;
import com.sumavision.talktv4.dao.OnRecyclerItemClickListener;
import com.sumavision.talktv4.model.entity.ClassifyItem;
import com.sumavision.talktv4.model.entity.decor.ClassifyData;
import com.sumavision.talktv4.ui.adapter.RecyclerAdapter;
import com.sumavision.talktv4.util.VibratorUtil;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/12.
 */
public class MyGridFragment extends Fragment implements MyItemTouchCallback.OnDragListener{

    private ClassifyData data ;
    private ArrayList<ClassifyItem> items;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化数据，如果缓存中有就使用缓存中的
        data = (ClassifyData) ACache.get(getActivity()).getAsObject("classifydata");
        if (data!=null) {
            //获取缓存中的 ArrayList<ClassifyItem>
            items =data.results;



        } else {
            items  = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                items.add(new ClassifyItem(i * 8 + 0, "收款", ""));
                items.add(new ClassifyItem(i * 8 + 1, "转账",""));
                items.add(new ClassifyItem(i * 8 + 2, "余额宝", ""));
                items.add(new ClassifyItem(i * 8 + 3, "手机充值", ""));
                items.add(new ClassifyItem(i * 8 + 4, "医疗", ""));
                items.add(new ClassifyItem(i * 8 + 5, "彩票",""));
                items.add(new ClassifyItem(i * 8 + 6, "电影", ""));
                items.add(new ClassifyItem(i * 8 + 7, "游戏", ""));
            }
        }
        items.remove(items.size() - 1);
//        results.add(new Classify(results.size(), "更多", R.mipmap.ic_launcher));
        items.add(new ClassifyItem(items.size(), "R.mipmap.ic_launcher", "更多"));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return new RecyclerView(container.getContext());
    }

    private RecyclerView recyclerView;
    private ItemTouchHelper itemTouchHelper;
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerAdapter adapter = new RecyclerAdapter(R.layout.dragsetting_item_layout,items);
        recyclerView = (RecyclerView)view;
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        recyclerView.addItemDecoration(new DividerGridItemDecoration(getActivity()));

        itemTouchHelper = new ItemTouchHelper(new MyItemTouchCallback(adapter).setOnDragListener(this));
        itemTouchHelper.attachToRecyclerView(recyclerView);

        recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(recyclerView) {
            @Override
            public void onLongClick(RecyclerView.ViewHolder vh) {
                if (vh.getLayoutPosition()!=items.size()-1) {
                    itemTouchHelper.startDrag(vh);
                    VibratorUtil.Vibrate(getActivity(), 70);   //震动70ms
                }
            }
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                ClassifyItem item = items.get(vh.getLayoutPosition());
                Toast.makeText(getActivity(),item.id+" "+item.txt,Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onFinishDrag() {
        //存入缓存
        ACache.get(getActivity()).put("classifydata",(ClassifyData)data);
    }
}