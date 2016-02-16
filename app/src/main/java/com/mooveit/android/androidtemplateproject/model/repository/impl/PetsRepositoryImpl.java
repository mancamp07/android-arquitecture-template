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

    private static final String TAG = PetsRepositoryImpl.class.getSimpleName();

    private final PetStoreService mPetStoreService;

    public PetsRepositoryImpl(PetStoreService petStoreService) {
        this.mPetStoreService = petStoreService;
    }

    @Override
    public void getPets(final OnGetPetsCallback callback) {
        mPetStoreService.getPets()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleSubscriber<List<Pet>>() {
                    @Override
                    public void onError(Throwable e) {
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
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleSubscriber<Pet>() {
                    @Override
                    public void onSuccess(Pet value) {
                        callback.onPetCreated(value);
                    }

                    @Override
                    public void onError(Throwable error) {
                        callback.onCreatePetFailed(error.getMessage());
                    }
                });
    }
}
