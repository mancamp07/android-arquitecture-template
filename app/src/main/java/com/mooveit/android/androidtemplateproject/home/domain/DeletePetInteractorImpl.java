package com.mooveit.android.androidtemplateproject.home.domain;

import com.mooveit.android.androidtemplateproject.common.model.entities.Pet;
import com.mooveit.android.androidtemplateproject.common.model.repository.PetsRepository;

import rx.Observable;
import rx.Single;

public class DeletePetInteractorImpl implements DeletePetInteractor {

    private final PetsRepository mPetsRepository;

    public DeletePetInteractorImpl(PetsRepository petsRepository) {
        this.mPetsRepository = petsRepository;
    }

    @Override
    public Observable<Void> deletePet(Pet pet) {
        return mPetsRepository.deletePet(pet);
    }
}
