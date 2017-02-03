package com.mooveit.android.androidtemplateproject.common.mock.di.modules;

import android.support.test.espresso.idling.CountingIdlingResource;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class IdlingResourceModule {

    @Singleton
    @Binds
    public abstract CountingIdlingResource bindIdlingResource(CountingIdlingResource countingIdlingResource);
}
