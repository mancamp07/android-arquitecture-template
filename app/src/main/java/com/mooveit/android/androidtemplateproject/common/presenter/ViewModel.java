package com.mooveit.android.androidtemplateproject.common.presenter;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public abstract class ViewModel {

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
