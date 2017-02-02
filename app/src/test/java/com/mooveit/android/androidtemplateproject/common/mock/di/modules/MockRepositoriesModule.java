package com.mooveit.android.androidtemplateproject.common.mock.di.modules;

import com.mooveit.android.androidtemplateproject.common.mock.repository.MockPetsRepositoryImpl;
import com.mooveit.android.androidtemplateproject.common.model.repository.PetsRepository;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class MockRepositoriesModule {

    @Binds
    public abstract PetsRepository providePetsRepository(MockPetsRepositoryImpl mockPetsRepository);
}
