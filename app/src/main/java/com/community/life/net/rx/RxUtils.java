package com.community.life.net.rx;

import com.sai.framework.base.SaiActivity;
import com.sai.framework.base.SaiFragment;
import com.sai.framework.mvp.MvpView;
import com.trello.rxlifecycle2.LifecycleTransformer;
/**
 * 功能说明：
 *
 * @author: Yiheng Yan
 * @Email: yanyiheng86@163.com
 * @date: 17-8-24 上午12:12
 * @Copyright (c) 2017. Inc. All rights reserved.
 */
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
