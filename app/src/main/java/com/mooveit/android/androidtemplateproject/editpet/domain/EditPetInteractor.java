package com.mooveit.android.androidtemplateproject.editpet.domain;

import com.mooveit.android.androidtemplateproject.common.model.entities.Pet;

import rx.Observable;

public interface EditPetInteractor {

    Observable<Pet> editPet(Pet pet);

}
