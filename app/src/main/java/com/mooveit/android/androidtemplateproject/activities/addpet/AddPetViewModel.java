package com.mooveit.android.androidtemplateproject.activities.addpet;

import android.util.Log;

import com.mooveit.android.androidtemplateproject.model.Pet;
import com.mooveit.android.androidtemplateproject.network.PetStoreService;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AddPetViewModel {

    private static final String TAG = AddPetViewModel.class.getSimpleName();

    private final AddPetView mAddPetView;
    private final PetStoreService mPetStoreService;

    public AddPetViewModel(AddPetView addPetView, PetStoreService petStoreService) {
        this.mAddPetView = addPetView;
        this.mPetStoreService = petStoreService;
    }

    public void createPet(Pet pet) {

        mAddPetView.showProgress();
        mPetStoreService.createPet(pet)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Pet>() {
                    @Override
                    public void onCompleted() {
                        mAddPetView.hideProgress();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, e.getMessage());
                    }

                    @Override
                    public void onNext(Pet pet) {
                        mAddPetView.onPetCreated();
                    }
                });

    }

}
