package com.mooveit.android.androidtemplateproject.home.domain;

import com.mooveit.android.androidtemplateproject.common.model.entities.Pet;
import com.mooveit.android.androidtemplateproject.common.model.repository.PetsRepository;

import java.util.List;

import rx.Single;

public class GetPetsInteractorImpl implements GetPetsInteractor {

    private final PetsRepository mPetsRepository;

    public GetPetsInteractorImpl(PetsRepository petsRepository) {
        this.mPetsRepository = petsRepository;
    }

    @Override
    public Single<List<Pet>> getPets() {
        return mPetsRepository.getPets();
    }
}
