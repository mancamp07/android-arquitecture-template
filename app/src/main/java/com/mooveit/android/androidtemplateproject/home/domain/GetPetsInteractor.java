package com.mooveit.android.androidtemplateproject.home.domain;

import com.mooveit.android.androidtemplateproject.common.model.entities.Pet;

import java.util.List;

import rx.Single;

public interface GetPetsInteractor {

    Single<List<Pet>> getPets();

}
