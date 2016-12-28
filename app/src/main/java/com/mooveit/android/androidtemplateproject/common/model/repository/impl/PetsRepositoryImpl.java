package com.mooveit.android.androidtemplateproject.common.model.repository.impl;

import com.mooveit.android.androidtemplateproject.common.model.entities.Pet;
import com.mooveit.android.androidtemplateproject.common.model.repository.PetsRepository;
import com.mooveit.android.androidtemplateproject.common.network.PetStoreService;

import java.util.List;

import rx.Single;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PetsRepositoryImpl implements PetsRepository {

    private final PetStoreService mPetStoreService;

    public PetsRepositoryImpl(PetStoreService petStoreService) {
        this.mPetStoreService = petStoreService;
    }

    @Override
    public Single<List<Pet>> getPets() {
        return mPetStoreService.getPets()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<Pet> createPet(Pet pet) {
        return mPetStoreService.createPet(pet)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<Pet> updatePet(Pet pet) {
        return mPetStoreService.updatePet(pet)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<Void> deletePet(Pet pet) {
        return mPetStoreService.deletePet(pet.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
