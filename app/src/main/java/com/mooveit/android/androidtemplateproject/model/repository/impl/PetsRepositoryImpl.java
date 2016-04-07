package com.mooveit.android.androidtemplateproject.model.repository.impl;

import com.mooveit.android.androidtemplateproject.model.entities.Pet;
import com.mooveit.android.androidtemplateproject.model.repository.PetsRepository;
import com.mooveit.android.androidtemplateproject.network.PetStoreService;

import java.util.List;

import rx.SingleSubscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class PetsRepositoryImpl implements PetsRepository {

    private final PetStoreService mPetStoreService;

    public PetsRepositoryImpl(PetStoreService petStoreService) {
        this.mPetStoreService = petStoreService;
    }

    @Override
    public void getPets(final OnGetPetsCallback callback) {
        mPetStoreService.getPets()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleSubscriber<List<Pet>>() {
                    @Override
                    public void onError(Throwable e) {
                        callback.onGetPetsFailed(e);
                        Timber.e(e, null);
                    }

                    public void onSuccess(List<Pet> pets) {
                        callback.onGetPetsLoaded(pets);
                    }
                });
    }

    @Override
    public void createPet(final Pet pet, final OnCreatePetCallback callback) {

        mPetStoreService.createPet(pet)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleSubscriber<Pet>() {
                    @Override
                    public void onSuccess(Pet value) {
                        callback.onPetCreated(value);
                    }

                    @Override
                    public void onError(Throwable error) {
                        callback.onCreatePetFailed(error);
                    }
                });
    }

    @Override
    public void updatePet(Pet pet, final OnEditPetCallback callback) {
        mPetStoreService.updatePet(pet)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleSubscriber<Pet>() {
                    @Override
                    public void onSuccess(Pet pet) {
                        callback.onEditPetSuccess(pet);
                    }

                    @Override
                    public void onError(Throwable error) {
                        callback.onEditPetFailed(error);
                    }
                });
    }

    @Override
    public void deletePet(Pet pet, OnDeletePetCallback callback) {
        mPetStoreService.deletePet(pet.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleSubscriber<Void>() {
                    @Override
                    public void onSuccess(Void value) {
                        callback.onDeletePetSuccess();
                    }

                    @Override
                    public void onError(Throwable error) {
                        callback.onDeletePetFailed(error);
                    }
                });
    }
}
