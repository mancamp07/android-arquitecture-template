package com.mooveit.android.androidtemplateproject.model.repository;

import com.mooveit.android.androidtemplateproject.model.entities.Pet;

import java.util.List;

public interface PetsRepository {

    void getPets(OnGetPetsCallback callback);
    void createPet(Pet pet, OnCreatePetCallback callback);
    void updatePet(Pet pet, OnEditPetCallback callback);
    void deletePet(Pet pet, OnDeletePetCallback callback);

    interface OnGetPetsCallback {
        void onGetPetsLoaded(List<Pet> pets);
        void onGetPetsFailed(Throwable throwable);
    }

    interface OnCreatePetCallback {
        void onPetCreated(Pet pet);
        void onCreatePetFailed(Throwable error);
    }

    interface OnEditPetCallback {
        void onEditPetSuccess(Pet pet);
        void onEditPetFailed(Throwable error);
    }

    interface OnDeletePetCallback {
        void onDeletePetSuccess();
        void onDeletePetFailed(Throwable error);
    }
}
