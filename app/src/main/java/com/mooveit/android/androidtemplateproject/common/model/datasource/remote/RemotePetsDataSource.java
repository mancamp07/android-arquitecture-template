package com.mooveit.android.androidtemplateproject.common.model.datasource.remote;

import com.mooveit.android.androidtemplateproject.common.model.datasource.PetsDataSource;
import com.mooveit.android.androidtemplateproject.common.model.entities.Pet;
import com.mooveit.android.androidtemplateproject.common.network.PetStoreService;

import java.util.List;

import rx.Observable;

public class RemotePetsDataSource implements PetsDataSource {

    private final PetStoreService mPetStoreService;

    public RemotePetsDataSource(PetStoreService petStoreService) {
        this.mPetStoreService = petStoreService;
    }

    @Override
    public Observable<List<Pet>> getPets() {
        return mPetStoreService.getPets();
    }

    @Override
    public Observable<Pet> createPet(Pet pet) {
        return mPetStoreService.createPet(pet);
    }

    @Override
    public Observable<Pet> updatePet(Pet pet) {
        return mPetStoreService.updatePet(pet);
    }

    @Override
    public Observable<Void> deletePet(Pet pet) {
        return mPetStoreService.deletePet(pet.getExternalId());
    }

    @Override
    public Observable<Void> deleteAll() {
        return null;
    }
}
