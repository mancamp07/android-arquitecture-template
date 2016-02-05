package com.mooveit.android.androidtemplateproject.activities.home;

import android.util.Log;

import com.mooveit.android.androidtemplateproject.model.Pet;
import com.mooveit.android.androidtemplateproject.network.PetStoreService;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HomeViewModel {

    private static final String TAG = HomeViewModel.class.getSimpleName();

    private final HomeView mHomeView;
    private final PetStoreService mPetStoreService;

    public HomeViewModel(HomeView homeView, PetStoreService petStoreService) {
        this.mHomeView = homeView;
        this.mPetStoreService = petStoreService;
    }

    public void fetchPets() {

        mHomeView.showProgress();
        mPetStoreService.getPets()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Pet>>() {
                    @Override
                    public void onCompleted() {
                        mHomeView.hideProgress();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, e.getMessage());
                    }

                    @Override
                    public void onNext(List<Pet> pets) {
                        mHomeView.showPets(pets);
                    }
                });
    }
}
