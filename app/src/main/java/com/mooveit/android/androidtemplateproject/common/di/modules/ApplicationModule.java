package com.mooveit.android.androidtemplateproject.common.di.modules;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    Application mApplication;

    public ApplicationModule(Application application) {
        this.mApplication = application;
    }

    @Provides
    @Singleton
    Application providesApplication() {
        return mApplication;
    }
}
