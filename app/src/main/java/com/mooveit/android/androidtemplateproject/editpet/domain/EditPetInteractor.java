package com.mooveit.android.androidtemplateproject.editpet.domain;

import com.mooveit.android.androidtemplateproject.common.model.entities.Pet;

import rx.Single;

public interface EditPetInteractor {

    Single<Pet> editPet(Pet pet);

}
