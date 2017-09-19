package com.sai.framework.rx;

import com.sai.framework.base.SaiActivity;
import com.sai.framework.base.SaiFragment;
import com.sai.framework.mvp.MvpView;
import com.trello.rxlifecycle2.LifecycleTransformer;

public class RxUtils {

    public static LifecycleTransformer bindToLifecycle(MvpView view) {
        if (view instanceof SaiActivity) {
            return ((SaiActivity) view).bindToLifecycle();
        } else if (view instanceof SaiFragment) {
            return ((SaiFragment) view).bindToLifecycle();
        } else {
            return null;
        }

    }
}
