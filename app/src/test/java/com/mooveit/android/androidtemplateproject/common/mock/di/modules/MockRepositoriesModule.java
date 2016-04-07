package com.mooveit.android.androidtemplateproject.common.mock.di.modules;

import com.mooveit.android.androidtemplateproject.common.di.modules.RepositoriesModule;
import com.mooveit.android.androidtemplateproject.model.repository.PetsRepository;
import com.mooveit.android.androidtemplateproject.network.PetStoreService;

import dagger.Module;

@Module
public class MockRepositoriesModule extends RepositoriesModule {

    private PetsRepository mPetsRepository;

    public MockRepositoriesModule() {}

    @Override
    protected PetsRepository getPetsRepository(PetStoreService petStoreService) {
        return mPetsRepository != null ?
                mPetsRepository : super.getPetsRepository(petStoreService);
    }

    public void setPetsRepository(PetsRepository petsRepository) {
        this.mPetsRepository = petsRepository;
    }
}
