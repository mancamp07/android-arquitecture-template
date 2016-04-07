package com.mooveit.android.androidtemplateproject.common.ui.recyclerview;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class VerticalSpacingItemDecoration extends RecyclerView.ItemDecoration {

    private final int mVerticalSpacing;

    public VerticalSpacingItemDecoration(int verticalSpacing) {
        super();
        this.mVerticalSpacing = verticalSpacing;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.bottom = mVerticalSpacing;
    }
}
