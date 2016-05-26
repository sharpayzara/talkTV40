package com.sumavision.talktv4.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sumavision.talktv4.ui.fragment.GanHuoFragment;

/**
 * Created by sharpay on 16-5-24.
 */
public class GanHuoPagerAdapter extends FragmentPagerAdapter {

    String[] title = {"Android","iOS","前端","瞎推荐","拓展资源","App"};

    public GanHuoPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return GanHuoFragment.newInstance(title[position]);
    }

    @Override
    public int getCount() {
        return title.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}