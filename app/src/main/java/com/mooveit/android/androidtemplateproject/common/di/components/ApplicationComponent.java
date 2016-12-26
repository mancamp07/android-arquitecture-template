package com.mooveit.android.androidtemplateproject.common.di.components;

import android.app.Application;
import android.content.Context;

import com.mooveit.android.androidtemplateproject.common.di.modules.ApplicationModule;
import com.mooveit.android.androidtemplateproject.common.di.modules.NetworkModule;
import com.mooveit.android.androidtemplateproject.model.repository.PetsRepository;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
        modules = {ApplicationModule.class, NetworkModule.class}
)
public interface ApplicationComponent {

    Application application();

    Context context();

    PetsRepository petsRepository();
}
