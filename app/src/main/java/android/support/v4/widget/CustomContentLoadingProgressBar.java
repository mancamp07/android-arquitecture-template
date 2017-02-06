package android.support.v4.widget;

import android.content.Context;
import android.util.AttributeSet;

public class CustomContentLoadingProgressBar extends ContentLoadingProgressBar {
    public CustomContentLoadingProgressBar(Context context) {
        super(context);
    }

    public CustomContentLoadingProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public boolean getPostedHide() {
        return mPostedHide;
    }

    public boolean getPostedShow() {
        return mPostedShow;
    }
}
