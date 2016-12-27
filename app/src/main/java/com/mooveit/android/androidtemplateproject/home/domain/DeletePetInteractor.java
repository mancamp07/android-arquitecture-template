package com.mooveit.android.androidtemplateproject.home.domain;

import com.mooveit.android.androidtemplateproject.common.model.entities.Pet;

import rx.Single;

public interface DeletePetInteractor {

    Single<Void> deletePet(Pet pet);
}
