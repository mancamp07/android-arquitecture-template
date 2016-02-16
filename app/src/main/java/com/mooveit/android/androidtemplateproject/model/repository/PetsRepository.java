package com.mooveit.android.androidtemplateproject.model.repository;

import com.mooveit.android.androidtemplateproject.model.entities.Pet;

import java.util.List;

public interface PetsRepository {

    void getPets(OnGetPetsCallback callback);
    void createPet(Pet pet, OnCreatePetCallback callback);

    interface OnGetPetsCallback {
        void onGetPetsLoaded(List<Pet> pets);
    }

    interface OnCreatePetCallback {
        void onPetCreated(Pet pet);
        void onCreatePetFailed(String message);
    }
}
