package com.sumavision.talktv4.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sumavision.talktv4.model.entity.ClassifyItem;
import com.sumavision.talktv4.ui.fragment.GanHuoFragmentFragment;

import java.util.ArrayList;

/**
 * Created by zhoutao on 16-5-27.
 */
public class GanHuoPagerAdapter extends FragmentPagerAdapter {

//    String[] title = {"Android","iOS","前端","瞎推荐","拓展资源","App"};
        ArrayList<ClassifyItem> items;
    public GanHuoPagerAdapter(FragmentManager fm,ArrayList<ClassifyItem> items) {
        super(fm);
        /////////初始化数据，如果缓存中有就使用缓存中的

            this.items=items;
    }

    @Override
    public Fragment getItem(int position) {
        return GanHuoFragmentFragment.newInstance(items.get(position).txt);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return items.get(position).txt;
    }
}