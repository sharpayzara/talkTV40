package com.sumavision.talktv4.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.sumavision.talktv4.R;
import com.sumavision.talktv4.ui.fragment.DragSettingFragment;
import com.sumavision.talktv4.ui.fragment.MyGridFragment;
import com.sumavision.talktv4.ui.fragment.MyListFragment;

import butterknife.Bind;

public class DragSettingActivity extends AppCompatActivity implements View.OnClickListener {


  /*  @Bind(R.id.dragsetting_btn_list)
    Button dragsetting_btn_list;
    @Bind(R.id.dragsetting_btn_grid)
    Button dragsetting_btn_grid;*/
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    public DragSettingActivity(){
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dragsetting);
        setSupportActionBar(toolbar);

        if (savedInstanceState==null){
            DragSettingFragment dragSettingFragment= new DragSettingFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment,dragSettingFragment)
                    .commit();
        }
    }

    @Override
    public void onClick(View v) {
        Fragment fragment = null;
        switch (v.getId()){
            case R.id.dragsetting_btn_list:
                fragment = new MyListFragment();
                break;
            case R.id.dragsetting_btn_grid:
                fragment = new MyGridFragment();
                break;
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment, fragment)
                .addToBackStack(null)
                .commit();
    }

}
