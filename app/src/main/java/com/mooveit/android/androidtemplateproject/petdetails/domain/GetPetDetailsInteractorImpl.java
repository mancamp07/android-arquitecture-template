package com.mooveit.android.androidtemplateproject.petdetails.domain;

import com.mooveit.android.androidtemplateproject.common.model.entities.Pet;
import com.mooveit.android.androidtemplateproject.common.model.repository.PetsRepository;

import rx.Observable;

public class GetPetDetailsInteractorImpl implements GetPetDetailsInteractor {

    private PetsRepository petsRepository;

    public GetPetDetailsInteractorImpl(PetsRepository petsRepository) {
        this.petsRepository = petsRepository;
    }

    @Override
    public Observable<Pet> getPet(long petId) {
        return petsRepository.getPet(petId);
    }
}
