package com.mooveit.android.androidtemplateproject.addpet.domain;

import com.mooveit.android.androidtemplateproject.common.model.entities.Pet;

import rx.Observable;
import rx.Single;

public interface AddPetInteractor {

    Observable<Pet> addPet(Pet pet);
}
