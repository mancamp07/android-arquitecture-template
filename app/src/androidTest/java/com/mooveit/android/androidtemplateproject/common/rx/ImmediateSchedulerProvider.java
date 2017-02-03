package com.mooveit.android.androidtemplateproject.common.rx;

import javax.inject.Inject;

import rx.Scheduler;
import rx.schedulers.Schedulers;

public class ImmediateSchedulerProvider implements SchedulerProvider {

    @Inject
    public ImmediateSchedulerProvider() {
    }

    @Override
    public Scheduler io() {
        return Schedulers.immediate();
    }

    @Override
    public Scheduler computation() {
        return Schedulers.immediate();
    }

    @Override
    public Scheduler main() {
        return Schedulers.immediate();
    }
}
