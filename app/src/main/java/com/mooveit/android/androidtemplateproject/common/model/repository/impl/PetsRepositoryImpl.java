package com.mooveit.android.androidtemplateproject.common.model.repository.impl;

import com.mooveit.android.androidtemplateproject.common.model.datasource.local.LocalPetsDataSource;
import com.mooveit.android.androidtemplateproject.common.model.datasource.remote.RemotePetsDataSource;
import com.mooveit.android.androidtemplateproject.common.model.entities.Pet;
import com.mooveit.android.androidtemplateproject.common.model.repository.PetsRepository;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

public class PetsRepositoryImpl implements PetsRepository {

    private final LocalPetsDataSource mLocalDatasource;
    private final RemotePetsDataSource mRemoteDatasource;

    private boolean mCacheIsDirty = false;

    public PetsRepositoryImpl(LocalPetsDataSource localDatasource,
                              RemotePetsDataSource remoteDatasource) {
        this.mLocalDatasource = localDatasource;
        this.mRemoteDatasource = remoteDatasource;
    }

    @Override
    public Observable<List<Pet>> getPets() {
        Observable<List<Pet>> remotePets = getAndSaveRemotePets();
        Observable<List<Pet>> localPets = mLocalDatasource.getPets().take(1);

        if (mCacheIsDirty) {
            return remotePets;
        } else {
            return Observable.concat(localPets, remotePets)
                    .first(pets -> !pets.isEmpty());
        }
    }

    private Observable<List<Pet>> getAndSaveRemotePets() {
        return mRemoteDatasource.getPets()
                .flatMap(new Func1<List<Pet>, Observable<List<Pet>>>() {
                    @Override
                    public Observable<List<Pet>> call(List<Pet> pets) {
                        return Observable.from(pets)
                                .doOnSubscribe(mLocalDatasource::deleteAll)
                                .flatMap(new Func1<Pet, Observable<Pet>>() {
                                    @Override
                                    public Observable<Pet> call(Pet pet) {
                                        return mLocalDatasource.createPet(pet);
                                    }
                                })
                                .toList();
                    }
                })
                .doOnCompleted(() -> mCacheIsDirty = false);
    }

    @Override
    public Observable<Pet> createPet(Pet pet) {
        return Observable.concat(
                mLocalDatasource.createPet(pet),
                mRemoteDatasource.createPet(pet)
                        .flatMap(new Func1<Pet, Observable<Pet>>() {
                            @Override
                            public Observable<Pet> call(Pet pet) {
                                return mLocalDatasource.updatePet(pet);
                            }
                        }));
    }

    @Override
    public Observable<Pet> updatePet(Pet pet) {
        return Observable.concat(
                mLocalDatasource.updatePet(pet),
                mRemoteDatasource.updatePet(pet));
    }

    @Override
    public Observable<Void> deletePet(Pet pet) {
        return Observable.concat(
                mLocalDatasource.deletePet(pet),
                mRemoteDatasource.deletePet(pet));
    }

    @Override
    public Observable<Void> deleteAll() {
        return mLocalDatasource.deleteAll();
    }

    @Override
    public Observable<Pet> getPet(long petId) {
        return mLocalDatasource.getPet(petId).take(1);
    }

    @Override
    public void invalidateCache() {
        mCacheIsDirty = true;
    }
}
