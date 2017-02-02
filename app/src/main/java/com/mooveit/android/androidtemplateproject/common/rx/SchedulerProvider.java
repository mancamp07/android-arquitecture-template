package com.mooveit.android.androidtemplateproject.common.rx;

import rx.Scheduler;

public interface SchedulerProvider {

    Scheduler io();

    Scheduler computation();

    Scheduler main();
}
