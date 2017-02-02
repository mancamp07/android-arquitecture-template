package com.mooveit.android.androidtemplateproject.common.model.datasource;


import com.mooveit.android.androidtemplateproject.common.model.entities.Pet;

import java.util.List;

import rx.Observable;

public interface PetsDataSource {

    Observable<List<Pet>> getPets();

    Observable<Pet> createPet(Pet pet);

    Observable<Pet> updatePet(Pet pet);

    Observable<Void> deletePet(Pet pet);

    Observable<Void> deleteAll();

    Observable<Pet> getPet(long petId);
}
