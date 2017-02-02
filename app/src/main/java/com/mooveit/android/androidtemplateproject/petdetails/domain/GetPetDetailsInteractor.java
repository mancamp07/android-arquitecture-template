package com.mooveit.android.androidtemplateproject.petdetails.domain;

import com.mooveit.android.androidtemplateproject.common.model.entities.Pet;

import rx.Observable;

public interface GetPetDetailsInteractor {

    Observable<Pet> getPet(long petId);
}
