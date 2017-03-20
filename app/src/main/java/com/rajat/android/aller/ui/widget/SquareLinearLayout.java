package com.rajat.android.aller.ui.widget;

/**
 * Created by rajat on 3/20/2017.
 */

import android.content.Context;
import android.support.design.internal.ForegroundLinearLayout;
import android.util.AttributeSet;

/**
 * A square {@link ForegroundLinearLayout}.
 */
public class SquareLinearLayout extends ForegroundLinearLayout {

    public SquareLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // Enforce a square tile by passing width MS to both params.
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

}
