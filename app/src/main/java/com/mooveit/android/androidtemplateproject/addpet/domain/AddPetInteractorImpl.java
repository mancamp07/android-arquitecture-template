package com.mooveit.android.androidtemplateproject.addpet.domain;

import com.mooveit.android.androidtemplateproject.common.Constants;
import com.mooveit.android.androidtemplateproject.common.domain.interactors.BaseInteractor;
import com.mooveit.android.androidtemplateproject.common.model.entities.Pet;
import com.mooveit.android.androidtemplateproject.common.model.repository.PetsRepository;

import rx.Observable;

public class AddPetInteractorImpl extends BaseInteractor implements AddPetInteractor {

    private final PetsRepository mPetsRepository;

    public AddPetInteractorImpl(PetsRepository petsRepository) {
        this.mPetsRepository = petsRepository;
    }

    public Observable<Pet> addPet(Pet pet) {
        pet.setStatus(Constants.PET_STATUS_AVAILABLE);
        return mPetsRepository.createPet(pet);
    }
}
