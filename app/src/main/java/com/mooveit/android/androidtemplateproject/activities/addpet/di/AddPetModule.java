package com.mooveit.android.androidtemplateproject.activities.addpet.di;

import com.mooveit.android.androidtemplateproject.activities.addpet.AddPetView;
import com.mooveit.android.androidtemplateproject.activities.addpet.AddPetViewModel;
import com.mooveit.android.androidtemplateproject.model.repository.PetsRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class AddPetModule {

    private final AddPetView mAddPetView;

    public AddPetModule(AddPetView mAddPetView) {
        this.mAddPetView = mAddPetView;
    }

    @Provides
    AddPetViewModel providesAddPetViewModel(PetsRepository petsRepository) {
        return new AddPetViewModel(mAddPetView, petsRepository);
    }

}
