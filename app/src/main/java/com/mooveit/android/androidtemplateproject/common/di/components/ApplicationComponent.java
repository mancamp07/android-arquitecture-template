package com.mooveit.android.androidtemplateproject.common.di.components;

import android.app.Application;
import android.content.Context;

import com.mooveit.android.androidtemplateproject.common.di.modules.ApplicationModule;
import com.mooveit.android.androidtemplateproject.common.di.modules.DataSourceModule;
import com.mooveit.android.androidtemplateproject.common.di.modules.NetworkModule;
import com.mooveit.android.androidtemplateproject.common.di.modules.RepositoriesModule;
import com.mooveit.android.androidtemplateproject.common.model.repository.PetsRepository;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
        modules = {ApplicationModule.class,
                NetworkModule.class,
                RepositoriesModule.class,
                DataSourceModule.class}
)
public interface ApplicationComponent {

    Application application();

    Context context();

    PetsRepository petsRepository();
}
