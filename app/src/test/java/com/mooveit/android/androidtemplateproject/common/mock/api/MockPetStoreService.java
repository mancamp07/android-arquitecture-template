package com.mooveit.android.androidtemplateproject.common.mock.api;

import com.mooveit.android.androidtemplateproject.common.model.entities.Pet;
import com.mooveit.android.androidtemplateproject.common.network.PetStoreService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.Path;
import rx.Single;

public class MockPetStoreService implements PetStoreService {

    @Override
    public Single<List<Pet>> getPets() {
        return Single.just(new ArrayList<>());
    }

    @Override
    public Single<Pet> createPet(@Body Pet pet) {
        return Single.just(new Pet(petId, petName, petStatus, externalId, updatedAt));
    }

    @Override
    public Single<Pet> updatePet(@Body Pet pet) {
        return Single.just(new Pet(petId, petName, petStatus, externalId, updatedAt));
    }

    @Override
    public Single<Void> deletePet(@Path("petId") long petId) {
        return Single.just(null);
    }
}
