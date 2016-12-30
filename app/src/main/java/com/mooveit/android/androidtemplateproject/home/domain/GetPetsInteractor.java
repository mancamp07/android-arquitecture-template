package com.mooveit.android.androidtemplateproject.home.domain;

import com.mooveit.android.androidtemplateproject.common.model.entities.Pet;

import java.util.List;

import rx.Observable;

public interface GetPetsInteractor {

    Observable<List<Pet>> getPets(boolean forceRefresh);

}
