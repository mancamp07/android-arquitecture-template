package com.mooveit.android.androidtemplateproject.common.mock.repository;

import com.mooveit.android.androidtemplateproject.common.model.entities.Pet;
import com.mooveit.android.androidtemplateproject.common.model.repository.PetsRepository;

import java.util.List;

import rx.Observable;
import rx.Single;

public abstract class MockPetsRepositoryImpl implements PetsRepository {

    @Override
    public Observable<List<Pet>> getPets() {
        return null;
    }

    @Override
    public Observable<Pet> createPet(Pet pet) {
        return null;
    }

    @Override
    public Observable<Pet> updatePet(Pet pet) {
        return null;
    }

    @Override
    public Observable<Void> deletePet(Pet pet) {
        return null;
    }
}
