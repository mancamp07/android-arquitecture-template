package com.mooveit.android.androidtemplateproject.common.mock.di.modules;

import com.mooveit.android.androidtemplateproject.common.rx.ImmediateSchedulerProvider;
import com.mooveit.android.androidtemplateproject.common.rx.SchedulerProvider;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class MockSchedulerModule {

    @Binds
    public abstract SchedulerProvider bindSchedulerProvider(ImmediateSchedulerProvider schedulerProvider);
}
