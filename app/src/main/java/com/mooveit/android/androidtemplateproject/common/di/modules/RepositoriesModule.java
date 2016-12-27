package com.mooveit.android.androidtemplateproject.common.di.modules;

import com.mooveit.android.androidtemplateproject.common.model.repository.PetsRepository;
import com.mooveit.android.androidtemplateproject.common.model.repository.impl.PetsRepositoryImpl;
import com.mooveit.android.androidtemplateproject.common.network.PetStoreService;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoriesModule {

    @Provides
    protected PetsRepository providesPetsRepository(PetStoreService petStoreService) {
        return getPetsRepository(petStoreService);
    }

    protected PetsRepository getPetsRepository(PetStoreService petStoreService) {
        return new PetsRepositoryImpl(petStoreService);
    }
}
