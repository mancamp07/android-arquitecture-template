package com.mooveit.android.androidtemplateproject.common.mock.di.modules;

import com.mooveit.android.androidtemplateproject.common.mock.api.MockPetStoreService;
import com.mooveit.android.androidtemplateproject.network.PetStoreService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MockNetworkModule {

    @Provides
    @Singleton
    protected PetStoreService providePetStoreService() {
        return new MockPetStoreService();
    }
}
