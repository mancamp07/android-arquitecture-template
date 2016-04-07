package com.mooveit.android.androidtemplateproject.activities.addpet;

import com.mooveit.android.androidtemplateproject.model.entities.Pet;
import com.mooveit.android.androidtemplateproject.model.repository.PetsRepository;

public class AddPetViewModel implements PetsRepository.OnCreatePetCallback {

    private static final String TAG = AddPetViewModel.class.getSimpleName();

    private final AddPetView mAddPetView;
    private final PetsRepository mPetsRepository;

    public AddPetViewModel(AddPetView addPetView, PetsRepository petsRepository) {
        this.mAddPetView = addPetView;
        this.mPetsRepository = petsRepository;
    }

    public void createPet(Pet pet) {

        mAddPetView.showProgress();

        mPetsRepository.createPet(pet, this);
    }

    @Override
    public void onPetCreated(Pet pet) {
        mAddPetView.hideProgress();
        mAddPetView.onPetCreated();
    }

    @Override
    public void onCreatePetFailed(Throwable error) {
        mAddPetView.hideProgress();
        mAddPetView.showError(error.getMessage());
    }
}
