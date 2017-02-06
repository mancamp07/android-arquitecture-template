package com.mooveit.android.androidtemplateproject.common.util;

import android.support.v4.widget.CustomContentLoadingProgressBar;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class CustomViewMatchers {

    public static Matcher<View> isContentLoadingProgressBarDisplayed() {
        return new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View view) {
                return view.getClass().isAssignableFrom(CustomContentLoadingProgressBar.class)
                        && (view.getVisibility() == View.VISIBLE ||
                        ((CustomContentLoadingProgressBar) view).getPostedShow());
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("is displayed on the screen to the user");
            }
        };
    }
}
