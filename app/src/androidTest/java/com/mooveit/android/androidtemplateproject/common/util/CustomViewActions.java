package com.mooveit.android.androidtemplateproject.common.util;

import android.support.annotation.IdRes;
import android.support.test.espresso.PerformException;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.util.HumanReadables;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.hamcrest.Matcher;

import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static org.hamcrest.Matchers.allOf;


public class CustomViewActions {

    public static <VH extends RecyclerView.ViewHolder> ViewAction actionOnItemChildViewAtPosition(final int position,
                                                                                                  @IdRes final int childViewId,
                                                                                                  final ViewAction viewAction) {
        return new ActionOnItemChildViewAtPosition<VH>(position, childViewId, viewAction);
    }

    private static final class ActionOnItemChildViewAtPosition<VH extends RecyclerView.ViewHolder> implements
            ViewAction {
        private final int position;
        @IdRes
        private final int childViewId;
        private final ViewAction viewAction;

        private ActionOnItemChildViewAtPosition(int position, int childViewId, ViewAction viewAction) {
            this.position = position;
            this.childViewId = childViewId;
            this.viewAction = viewAction;
        }

        @SuppressWarnings("unchecked")
        @Override
        public Matcher<View> getConstraints() {
            return allOf(isAssignableFrom(RecyclerView.class), isDisplayed());
        }

        @Override
        public String getDescription() {
            return "actionOnItemChildViewAtPosition performing ViewAction: " + viewAction.getDescription()
                    + " on item at position: " + position;
        }

        @Override
        public void perform(UiController uiController, View view) {
            RecyclerView recyclerView = (RecyclerView) view;

            new ScrollToPositionViewAction(position).perform(uiController, view);
            uiController.loopMainThreadUntilIdle();

            @SuppressWarnings("unchecked")
            VH viewHolderForPosition = (VH) recyclerView.findViewHolderForAdapterPosition(position);
            if (null == viewHolderForPosition) {
                throw new PerformException.Builder().withActionDescription(this.toString())
                        .withViewDescription(HumanReadables.describe(view))
                        .withCause(new IllegalStateException("No view holder at position: " + position))
                        .build();
            }

            View viewAtPosition = viewHolderForPosition.itemView;
            if (null == viewAtPosition) {
                throw new PerformException.Builder().withActionDescription(this.toString())
                        .withViewDescription(HumanReadables.describe(viewAtPosition))
                        .withCause(new IllegalStateException("No view at position: " + position)).build();
            }

            View childView = viewAtPosition.findViewById(childViewId);

            viewAction.perform(uiController, childView);
        }
    }

    private static final class ScrollToPositionViewAction implements ViewAction {
        private final int position;

        private ScrollToPositionViewAction(int position) {
            this.position = position;
        }

        @SuppressWarnings("unchecked")
        @Override
        public Matcher<View> getConstraints() {
            return allOf(isAssignableFrom(RecyclerView.class), isDisplayed());
        }

        @Override
        public String getDescription() {
            return "scroll RecyclerView to position: " + position;
        }

        @Override
        public void perform(UiController uiController, View view) {
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.scrollToPosition(position);
        }
    }
}
