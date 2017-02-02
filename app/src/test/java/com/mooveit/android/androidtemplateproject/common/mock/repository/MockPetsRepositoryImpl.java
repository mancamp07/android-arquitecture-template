package com.mooveit.android.androidtemplateproject.common.mock.repository;

import com.mooveit.android.androidtemplateproject.common.model.entities.Pet;
import com.mooveit.android.androidtemplateproject.common.model.repository.PetsRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Single;

public class MockPetsRepositoryImpl implements PetsRepository {

    @Inject
    public MockPetsRepositoryImpl() {
    }

    @Override
    public Observable<List<Pet>> getPets() {

        List<Pet> pets = new ArrayList<Pet>() {{
            add(new Pet(1, "Pepe", "available", 12345, 12345));
            add(new Pet(2, "Jorge", "available", 67890, 67890));
            add(new Pet(2, "Jack", "available", 934857, 28321));
        }};

        return Observable.just(pets);
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

    @Override
    public Observable<Void> deleteAll() {
        return null;
    }

    @Override
    public Observable<Pet> getPet(long petId) {
        return null;
    }

    @Override
    public void invalidateCache() {
    }
}
