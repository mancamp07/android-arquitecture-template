package com.mooveit.android.androidtemplateproject.common.rx;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@Singleton
public class SchedulerProviderImpl implements SchedulerProvider {

    @Inject
    public SchedulerProviderImpl() {
    }

    @Override
    public Scheduler io() {
        return Schedulers.io();
    }

    @Override
    public Scheduler computation() {
        return Schedulers.computation();
    }

    @Override
    public Scheduler main() {
        return AndroidSchedulers.mainThread();
    }
}
