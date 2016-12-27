package com.mooveit.android.androidtemplateproject.common.model.repository;

import com.mooveit.android.androidtemplateproject.common.model.entities.Pet;

import java.util.List;

import rx.Single;

public interface PetsRepository {

    Single<List<Pet>> getPets();

    Single<Pet> createPet(Pet pet);

    Single<Pet> updatePet(Pet pet);

    Single<Void> deletePet(Pet pet);

}
