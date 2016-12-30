package com.mooveit.android.androidtemplateproject.common.di.modules;

import com.mooveit.android.androidtemplateproject.common.model.datasource.local.LocalPetsDataSource;
import com.mooveit.android.androidtemplateproject.common.model.datasource.remote.RemotePetsDataSource;
import com.mooveit.android.androidtemplateproject.common.model.repository.PetsRepository;
import com.mooveit.android.androidtemplateproject.common.model.repository.impl.PetsRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoriesModule {

    @Provides
    @Singleton
    PetsRepository providePetsRepository(LocalPetsDataSource localPetsDataSource,
                                         RemotePetsDataSource remotePetsDataSource) {
        return new PetsRepositoryImpl(localPetsDataSource, remotePetsDataSource);
    }
}
