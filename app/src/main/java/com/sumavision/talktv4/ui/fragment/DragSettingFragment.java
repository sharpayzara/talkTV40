package com.sumavision.talktv4.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sumavision.talktv4.R;
import com.sumavision.talktv4.presenter.DragSettingFragmentPresenter;
import com.sumavision.talktv4.ui.fragment.Base.BaseFragment;
import com.sumavision.talktv4.ui.iview.IDragSettingView;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/5/26.
 */
public class DragSettingFragment extends BaseFragment implements View.OnClickListener,IDragSettingView{
    private static final String TYPE = "type";
    private String type;

    @Bind(R.id.dragsetting_btn_list)
    Button dragsetting_btn_list;
    @Bind(R.id.dragsetting_btn_grid)
    Button dragsetting_btn_grid;

    public DragSettingFragment(){

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
        return R.layout.fragment_dragsetting;
    }

    @Override
    protected void initPresenter() {
        presenter = new DragSettingFragmentPresenter(getContext(),this);
        presenter.init();
    }

    @Override
    public void onClick(View v) {
        ((View.OnClickListener)getActivity()).onClick(v);
    }

    @Override
    public void initView() {
        dragsetting_btn_list.setOnClickListener(this);
        dragsetting_btn_grid.setOnClickListener(this);
    }
}
