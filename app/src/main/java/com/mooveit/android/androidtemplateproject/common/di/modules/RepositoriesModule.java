package com.mooveit.android.androidtemplateproject.common.di.modules;

import com.mooveit.android.androidtemplateproject.common.model.datasource.PetsDataSource;
import com.mooveit.android.androidtemplateproject.common.model.repository.PetsRepository;
import com.mooveit.android.androidtemplateproject.common.model.repository.impl.PetsRepositoryImpl;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoriesModule {

    @Provides
    @Singleton
    PetsRepository providePetsRepository(@Named("local") PetsDataSource localPetsDataSource,
                                         @Named("remote") PetsDataSource remotePetsDataSource) {
        return new PetsRepositoryImpl(localPetsDataSource, remotePetsDataSource);
    }
}
