package com.mooveit.android.androidtemplateproject.activities.home;

import com.mooveit.android.androidtemplateproject.common.views.ProgressView;
import com.mooveit.android.androidtemplateproject.model.entities.Pet;

import java.util.List;

public interface HomeView extends ProgressView {

    void showPets(List<Pet> pets);

    void showPet(Pet pet);

    void showEditPet(Pet pet);

    void showPets();

    void showError(String message);
}
