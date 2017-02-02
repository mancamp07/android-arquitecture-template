package com.mooveit.android.androidtemplateproject.common.di.modules;

import com.mooveit.android.androidtemplateproject.common.rx.SchedulerProvider;
import com.mooveit.android.androidtemplateproject.common.rx.SchedulerProviderImpl;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class SchedulerModule {

    @Binds
    public abstract SchedulerProvider bindSchedulerProvider(SchedulerProviderImpl schedulerProvider);
}
