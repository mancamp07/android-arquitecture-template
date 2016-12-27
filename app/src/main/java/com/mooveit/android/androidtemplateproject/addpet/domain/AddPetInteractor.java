package com.mooveit.android.androidtemplateproject.addpet.domain;

import com.mooveit.android.androidtemplateproject.common.model.entities.Pet;

import rx.Single;

public interface AddPetInteractor {

    Single<Pet> addPet(Pet pet);
}
