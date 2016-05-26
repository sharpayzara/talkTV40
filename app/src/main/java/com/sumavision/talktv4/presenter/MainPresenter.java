package com.sumavision.talktv4.presenter;

import android.content.Context;
import com.sumavision.talktv4.presenter.base.BasePresenter;
import com.sumavision.talktv4.ui.iview.IMainView;

/**
 * Created by sharpay on 16-5-23.
 */
public class MainPresenter extends BasePresenter<IMainView> {

    public MainPresenter(Context context, IMainView iView) {
        super(context, iView);
    }

    @Override
    public void release() {

    }
}
