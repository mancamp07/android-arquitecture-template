package com.mooveit.android.androidtemplateproject.common.mock.repository;

import com.mooveit.android.androidtemplateproject.model.entities.Pet;
import com.mooveit.android.androidtemplateproject.model.repository.PetsRepository;

public abstract class MockPetsRepositoryImpl implements PetsRepository {
    @Override
    public void getPets(OnGetPetsCallback callback) {
    }

    @Override
    public void createPet(Pet pet, OnCreatePetCallback callback) {
    }

    @Override
    public void updatePet(Pet pet, OnEditPetCallback callback) {
    }

    @Override
    public void deletePet(Pet pet, OnDeletePetCallback callback) {
    }
}
