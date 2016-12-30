package com.mooveit.android.androidtemplateproject.home.domain;

import com.mooveit.android.androidtemplateproject.common.model.entities.Pet;
import com.mooveit.android.androidtemplateproject.common.model.repository.PetsRepository;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

public class GetPetsInteractorImpl implements GetPetsInteractor {

    private final PetsRepository mPetsRepository;

    public GetPetsInteractorImpl(PetsRepository petsRepository) {
        this.mPetsRepository = petsRepository;
    }

    @Override
    public Observable<List<Pet>> getPets(boolean forceRefresh) {
        if (forceRefresh) {
            mPetsRepository.invalidateCache();
        }
        return mPetsRepository.getPets()
                .flatMap(new Func1<List<Pet>, Observable<List<Pet>>>() {
                    @Override
                    public Observable<List<Pet>> call(List<Pet> pets) {
                        return Observable.from(pets)
                                .toSortedList((pet, pet2) ->
                                        Long.valueOf(pet2.getUpdatedAt())
                                        .compareTo(pet.getUpdatedAt()));
                    }
                });

    }
}
