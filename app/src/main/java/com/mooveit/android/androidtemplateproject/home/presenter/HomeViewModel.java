package com.mooveit.android.androidtemplateproject.home.presenter;

import com.mooveit.android.androidtemplateproject.common.model.entities.Pet;
import com.mooveit.android.androidtemplateproject.common.presenter.ViewModel;
import com.mooveit.android.androidtemplateproject.home.domain.DeletePetInteractor;
import com.mooveit.android.androidtemplateproject.home.domain.GetPetsInteractor;

import java.util.List;

import rx.SingleSubscriber;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HomeViewModel extends ViewModel {

    private final HomeView mHomeView;
    private final GetPetsInteractor mGetPetsInteractor;
    private final DeletePetInteractor mDeletePetInteractor;

    public HomeViewModel(HomeView homeView,
                         GetPetsInteractor getPetsInteractor,
                         DeletePetInteractor deletePetInteractor) {
        this.mHomeView = homeView;
        this.mGetPetsInteractor = getPetsInteractor;
        this.mDeletePetInteractor = deletePetInteractor;
    }

    public void fetchPets(boolean forceRefresh) {
        mHomeView.showProgress();

        subscribe(
                mGetPetsInteractor.getPets(forceRefresh)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<List<Pet>>() {

                            @Override
                            public void onCompleted() {
                            }

                            @Override
                            public void onError(Throwable error) {
                                mHomeView.hideProgress();
                                mHomeView.showError(error.getMessage());
                                mHomeView.showPets();
                            }

                            @Override
                            public void onNext(List<Pet> pets) {
                                mHomeView.hideProgress();
                                mHomeView.showPets(pets);
                            }
                        })
        );
    }

    public void onDeletePet(Pet pet) {
        subscribe(
                mDeletePetInteractor.deletePet(pet)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<Void>() {

                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable error) {
                                mHomeView.showError(error.getMessage());
                            }

                            @Override
                            public void onNext(Void aVoid) {
                                mHomeView.showPets();
                            }
                        })
        );
    }
}
