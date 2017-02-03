package com.mooveit.android.androidtemplateproject.common.mock.di.modules;

import com.mooveit.android.androidtemplateproject.common.model.repository.PetsRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static org.mockito.Mockito.mock;

@Module
public class MockRepositoriesModule {

    @Singleton
    @Provides
    PetsRepository providePetsRepository() {
        return mock(PetsRepository.class);
    }
}
