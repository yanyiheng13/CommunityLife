package com.iot12369.easylifeandroid.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.text.Spannable;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.UnderlineSpan;
import android.util.AttributeSet;

/**
 * 功能说明：去除textView下划线
 *
 * @author： Yiheng Yan
 * @email： yanyiheng@le.com
 * @version： 1.0
 * @date： 17-8-29
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */
public class NoLineTextView extends AppCompatTextView {

    public NoLineTextView(Context context) {
        this(context, null);
    }

    public NoLineTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NoLineTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        NoUnderlineSpan mNoUnderlineSpan = new NoUnderlineSpan();
        if (getText() instanceof Spannable) {
            Spannable s = (Spannable) getText();
            s.setSpan(mNoUnderlineSpan, 0, s.length(), Spanned.SPAN_MARK_MARK);
        }
    }



    public class NoUnderlineSpan extends UnderlineSpan {
        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(0xFF727272);
            ds.setUnderlineText(false);
        }
    }
}
