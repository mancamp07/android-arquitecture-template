package com.mooveit.android.androidtemplateproject.common;

import android.app.Application;
import android.content.Context;

import com.mooveit.android.androidtemplateproject.BuildConfig;
import com.mooveit.android.androidtemplateproject.common.di.components.ApplicationComponent;
import com.mooveit.android.androidtemplateproject.common.di.components.DaggerApplicationComponent;
import com.mooveit.android.androidtemplateproject.common.di.modules.ApplicationModule;
import com.mooveit.android.androidtemplateproject.common.di.modules.NetworkModule;

import timber.log.Timber;

public class AndroidTemplateApplication extends Application {

    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .networkModule(new NetworkModule(Constants.Api.BASE_API_URL))
                .build();

        setupTimberLogging();
    }

    private void setupTimberLogging() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new CrashReportingTree(this));
        }
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }

    public static AndroidTemplateApplication getInstance(Context context) {
        return (AndroidTemplateApplication) context.getApplicationContext();
    }
}
