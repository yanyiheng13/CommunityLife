package com.sai.framework.mvp;

import android.content.Context;

public interface MvpView {
    public void showLoadDialog();
    public void hideLoadDialog();
    public Context getContext();

}
