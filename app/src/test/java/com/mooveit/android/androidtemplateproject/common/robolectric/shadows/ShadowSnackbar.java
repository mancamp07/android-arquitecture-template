package com.mooveit.android.androidtemplateproject.common.robolectric.shadows;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.robolectric.annotation.RealObject;
import org.robolectric.internal.ShadowExtractor;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"UnusedDeclaration", "Unchecked"})
@Implements(Snackbar.class)
public class ShadowSnackbar {

    static List<ShadowSnackbar> shadowSnackbars = new ArrayList<>();

    @RealObject
    Snackbar snackbar;

    String text;
    private int duration;
    private int gravity;
    private int xOffset;
    private int yOffset;
    private View view;

    @Implementation
    public static Snackbar make(@NonNull View view, @NonNull CharSequence text, int duration) {
        Snackbar snackbar = null;

        try {
            Constructor<Snackbar> constructor = Snackbar.class.getDeclaredConstructor(ViewGroup.class);

            if (null == constructor)
                throw new IllegalArgumentException("Seems like the constructor was not found!");


            if (Modifier.isPrivate(constructor.getModifiers())) {
                constructor.setAccessible(true);
            }

            snackbar = constructor.newInstance(findSuitableParent(view));
            snackbar.setText(text);
            snackbar.setDuration(duration);
        } catch (Exception e) {
            e.printStackTrace();
        }

        shadowOf(snackbar).text = text.toString();

        shadowSnackbars.add(shadowOf(snackbar));

        return snackbar;
    }

    private static ViewGroup findSuitableParent(View view) {
        ViewGroup fallback = null;

        do {
            if (view instanceof CoordinatorLayout) {
                return (ViewGroup) view;
            }

            if (view instanceof FrameLayout || view instanceof RecyclerView ) {
                fallback = (ViewGroup) view;
            }

            if (view != null) {
                ViewParent parent = view.getParent();
                view = parent instanceof View ? (View) parent : null;
            }
        } while (view != null);

        return fallback;
    }

    @Implementation
    public static Snackbar make(@NonNull View view, @StringRes int resId, int duration) {
        return make(view, view.getResources().getText(resId), duration);
    }


    static ShadowSnackbar shadowOf(Snackbar bar) {
        return (ShadowSnackbar) ShadowExtractor.extract(bar);
    }

    public static void reset() {
        shadowSnackbars.clear();
    }

    public static int shownSnackbarCount() {
        return shadowSnackbars.isEmpty() ? 0 : shadowSnackbars.size();

    }

    public static String getTextOfLatestSnackbar() {
        if (!shadowSnackbars.isEmpty())
            return shadowSnackbars.get(shadowSnackbars.size() - 1).text;

        return null;
    }

    public static Snackbar getLatestSnackbar() {
        if (!shadowSnackbars.isEmpty())
            return shadowSnackbars.get(shadowSnackbars.size() - 1).snackbar;

        return null;
    }
}
