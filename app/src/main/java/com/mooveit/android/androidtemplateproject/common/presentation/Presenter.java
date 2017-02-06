package com.mooveit.android.androidtemplateproject.common.presentation;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public abstract class Presenter {

    protected CompositeSubscription mCompositeSubscription = new CompositeSubscription();

    public void onViewAttached() {
    }

    public void onViewDetached() {
        if (mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.clear();
        }
    }

    public void subscribe(Subscription subscription) {
        mCompositeSubscription.add(subscription);
    }

}
