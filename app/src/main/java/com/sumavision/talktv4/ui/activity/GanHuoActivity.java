package com.sumavision.talktv4.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.sumavision.talktv4.R;
import com.sumavision.talktv4.presenter.GanHuoPresenter;
import com.sumavision.talktv4.ui.activity.base.ToolBarActivity;
import com.sumavision.talktv4.ui.adapter.GanHuoPagerAdapter;
import com.sumavision.talktv4.ui.iview.base.IBaseView;

import butterknife.Bind;

/**
 * Created by sharpay on 16-5-24.
 */
public class GanHuoActivity extends ToolBarActivity<GanHuoPresenter> implements IBaseView {

    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.container)
    ViewPager container;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

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
        GanHuoPagerAdapter pagerAdapter = new GanHuoPagerAdapter(getSupportFragmentManager());
        container.setAdapter(pagerAdapter);
        container.setOffscreenPageLimit(3);
        setSupportActionBar(toolbar);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setupWithViewPager(container);
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
}
