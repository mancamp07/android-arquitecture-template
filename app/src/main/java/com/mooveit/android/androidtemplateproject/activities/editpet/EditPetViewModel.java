package com.mooveit.android.androidtemplateproject.activities.editpet;

import com.mooveit.android.androidtemplateproject.model.entities.Pet;
import com.mooveit.android.androidtemplateproject.model.repository.PetsRepository;

public class EditPetViewModel implements PetsRepository.OnEditPetCallback {

    private final EditPetView mEditPetView;
    private final PetsRepository mPetsRepository;

    public EditPetViewModel(EditPetView editPetView, PetsRepository petsRepository) {
        this.mEditPetView = editPetView;
        this.mPetsRepository = petsRepository;
    }

    public void editPet(Pet pet) {
        mEditPetView.showProgress();
        mPetsRepository.updatePet(pet, this);
    }


    @Override
    public void onEditPetSuccess(Pet pet) {
        mEditPetView.hideProgress();
        mEditPetView.showSuccess();
    }

    @Override
    public void onEditPetFailed(Throwable error) {
        mEditPetView.hideProgress();
        mEditPetView.showError(error.getMessage());
    }
}
