package com.sumavision.talktv4.presenter;

import android.content.Context;

import com.sumavision.talktv4.presenter.base.BasePresenter;
import com.sumavision.talktv4.ui.iview.IDragSettingView;

/**
 * Created by sharpay on 16-5-24.
 */
public class DragSettingFragmentPresenter extends BasePresenter<IDragSettingView> {
    public DragSettingFragmentPresenter(Context context, IDragSettingView iView) {
        super(context, iView);
    }

    @Override
    public void release() {
        /*if (subscription != null) {
            subscription.unsubscribe();
        }*/
    }
}
