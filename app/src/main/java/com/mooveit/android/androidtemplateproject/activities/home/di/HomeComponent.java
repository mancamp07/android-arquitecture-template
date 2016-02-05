package com.mooveit.android.androidtemplateproject.activities.home.di;

import com.mooveit.android.androidtemplateproject.activities.home.HomeFragment;
import com.mooveit.android.androidtemplateproject.common.di.components.ApplicationComponent;
import com.mooveit.android.androidtemplateproject.common.di.scopes.PerActivity;

import dagger.Component;

@PerActivity
@Component(
        dependencies = ApplicationComponent.class,
        modules = { HomeModule.class }
)
public interface HomeComponent {

    void inject(HomeFragment homeFragment);
}
