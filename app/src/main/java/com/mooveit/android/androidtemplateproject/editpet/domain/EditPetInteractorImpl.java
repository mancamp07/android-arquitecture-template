package com.mooveit.android.androidtemplateproject.editpet.domain;

import com.mooveit.android.androidtemplateproject.common.domain.interactors.BaseInteractor;
import com.mooveit.android.androidtemplateproject.common.model.entities.Pet;
import com.mooveit.android.androidtemplateproject.common.model.repository.PetsRepository;

import rx.Single;

public class EditPetInteractorImpl extends BaseInteractor implements EditPetInteractor {

    private final PetsRepository petsRepository;

    public EditPetInteractorImpl(PetsRepository petsRepository) {
        this.petsRepository = petsRepository;
    }

    @Override
    public Single<Pet> editPet(Pet pet) {
        return petsRepository.updatePet(pet);
    }
}
