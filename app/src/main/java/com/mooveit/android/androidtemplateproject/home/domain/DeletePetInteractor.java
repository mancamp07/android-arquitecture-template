package com.mooveit.android.androidtemplateproject.home.domain;

import com.mooveit.android.androidtemplateproject.common.model.entities.Pet;

import rx.Observable;

public interface DeletePetInteractor {

    Observable<Void> deletePet(Pet pet);
}
