package com.sumavision.talktv4.ui.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.sumavision.talktv4.R;
import com.sumavision.talktv4.common.ACache;
import com.sumavision.talktv4.model.entity.ClassifyItem;
import com.sumavision.talktv4.model.entity.ClassifyUpdataItem;
import com.sumavision.talktv4.model.entity.Gank;
import com.sumavision.talktv4.model.entity.decor.ClassifyData;
import com.sumavision.talktv4.presenter.GanHuoPresenter;
import com.sumavision.talktv4.ui.activity.base.ToolBarActivity;
import com.sumavision.talktv4.ui.adapter.GanHuoPagerAdapter;
import com.sumavision.talktv4.ui.iview.IGanHuoView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by sharpay on 16-5-24.
 */
public class GanHuoActivity extends ToolBarActivity<GanHuoPresenter> implements IGanHuoView{

    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.container)
    ViewPager container;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.invigation_btn)
    Button invigation_btn;
    ClassifyData items;
    GanHuoPagerAdapter pagerAdapter;
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_ganhuo;
    }


    @Override
    protected void initPresenter() {
        presenter = new GanHuoPresenter(this, this);
        presenter.init();
    }

    @Override
    public void initView() {
        //先查询本地数据是否存在
        items = (ClassifyData) ACache.get(this).getAsObject("classifydata");
        if(items==null){
           /* items = new ArrayList<>();
            //请求接口3
            Classify c1 = new Classify(1,"android",2);
            items.add(c1);
            Classify c2 = new Classify(2,"IOS",2);
            items.add(c2);
            Classify c3 = new Classify(3,"java",2);
            items.add(c3);
            Classify c4 = new Classify(4,".net",2);
            items.add(c4);
            Classify c5 = new Classify(5,"h5",2);
            items.add(c5);*/
            //不存在,那么去请求接口2
            presenter.getClassifyData();
        }else{
            //本地数据如果存在
            //请求服务器获取更新分类
            presenter.getClassifyUpdataData();
        }
//        initDragData();
        invigation_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(GanHuoActivity.this, DragSettingActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initDragData() {
            items = (ClassifyData) ACache.get(this).getAsObject("classifydata");
     /*   if(items==null){
            items = new ClassifyData();
            items.results = new ArrayList<>();
        }*/
        pagerAdapter = new GanHuoPagerAdapter(getSupportFragmentManager(),items.results);
        container.setAdapter(pagerAdapter);
        container.setOffscreenPageLimit(3);
        setSupportActionBar(toolbar);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setupWithViewPager(container);
        tabLayout.invalidate();
        pagerAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.release();
    }

    @Override
    protected boolean canBack() {
        return false;
    }
    int n = 0;
    @Override
    protected void onResume() {
        super.onResume();
        if(n>0){
            initDragData();
        }
        n++;
    }

    @Override
    public void showGankList(List<Gank> gankList) {

    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    public void showErrorView() {

    }

    @Override
    public void showClassifyView(ArrayList<ClassifyItem> classifies) {
        if(items==null){
            items = new ClassifyData();
            items.results = new ArrayList<>();
        }
        items.results.addAll(classifies);
        //存入缓存
        ACache.get(this).put("classifydata", items);
        initDragData();

    }

    @Override
    public void updataClassifyView(ArrayList<ClassifyUpdataItem> classifyUpdates) {
        if(classifyUpdates!=null){
            //这里获取服务器更新的数据了
            for (int i = 0;i<classifyUpdates.size();i++){
                //把ClassifyDpada的数据拿出来
                int id = classifyUpdates.get(i).id;
                String img = classifyUpdates.get(i).img;
                String txt = classifyUpdates.get(i).txt;
                int isshow = classifyUpdates.get(i).isshow;
                int position = classifyUpdates.get(i).position;
                int isadd = classifyUpdates.get(i).isadd;
                items.results.add(position, new ClassifyItem(id, img, txt));
            }
        }

        //存入缓存
        ACache.get(this).put("classifydata", items);
        initDragData();

    }
}
