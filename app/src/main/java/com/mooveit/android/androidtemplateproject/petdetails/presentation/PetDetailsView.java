package com.mooveit.android.androidtemplateproject.petdetails.presentation;

import com.mooveit.android.androidtemplateproject.common.model.entities.Pet;
import com.mooveit.android.androidtemplateproject.common.views.ProgressView;

public interface PetDetailsView extends ProgressView {

    void showPet(Pet pet);

    void showError(String message);
}
