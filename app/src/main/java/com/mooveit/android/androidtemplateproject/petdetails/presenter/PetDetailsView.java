package com.mooveit.android.androidtemplateproject.petdetails.presenter;

import com.mooveit.android.androidtemplateproject.common.model.entities.Pet;

public interface PetDetailsView {
    void showPet(Pet pet);

    void showError(String message);
}
