package com.mooveit.android.androidtemplateproject.common.rx;

import rx.Scheduler;
import rx.schedulers.Schedulers;

public class ImmediateSchedulerProvider implements SchedulerProvider {

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
