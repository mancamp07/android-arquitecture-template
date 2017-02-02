package com.mooveit.android.androidtemplateproject.common.mock.di.components;

import com.mooveit.android.androidtemplateproject.common.di.components.ApplicationComponent;
import com.mooveit.android.androidtemplateproject.common.di.modules.ApplicationModule;
import com.mooveit.android.androidtemplateproject.common.di.modules.SchedulerModule;
import com.mooveit.android.androidtemplateproject.common.mock.di.modules.MockRepositoriesModule;
import com.mooveit.android.androidtemplateproject.home.HomeActivityTest;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
        modules = {ApplicationModule.class, MockRepositoriesModule.class, SchedulerModule.class}
)
public interface MockApplicationComponent extends ApplicationComponent {

    void inject(HomeActivityTest homeActivityTest);
}
