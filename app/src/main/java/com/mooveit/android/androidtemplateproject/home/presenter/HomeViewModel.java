package com.mooveit.android.androidtemplateproject.home.presenter;

import com.mooveit.android.androidtemplateproject.common.model.entities.Pet;
import com.mooveit.android.androidtemplateproject.common.presentation.ViewModel;
import com.mooveit.android.androidtemplateproject.common.rx.SchedulerProvider;
import com.mooveit.android.androidtemplateproject.home.domain.DeletePetInteractor;
import com.mooveit.android.androidtemplateproject.home.domain.GetPetsInteractor;

import java.util.List;

import rx.Subscriber;

public class HomeViewModel extends ViewModel {

    private final HomeView mHomeView;
    private final SchedulerProvider mSchedulerProvider;
    private final GetPetsInteractor mGetPetsInteractor;
    private final DeletePetInteractor mDeletePetInteractor;

    public HomeViewModel(HomeView homeView,
                         SchedulerProvider schedulerProvider,
                         GetPetsInteractor getPetsInteractor,
                         DeletePetInteractor deletePetInteractor) {
        this.mHomeView = homeView;
        this.mSchedulerProvider = schedulerProvider;
        this.mGetPetsInteractor = getPetsInteractor;
        this.mDeletePetInteractor = deletePetInteractor;
    }

    public void fetchPets(boolean forceRefresh) {
        mHomeView.showProgress();

        subscribe(
                mGetPetsInteractor.getPets(forceRefresh)
                        .subscribeOn(mSchedulerProvider.io())
                        .observeOn(mSchedulerProvider.main())
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
                        .subscribeOn(mSchedulerProvider.io())
                        .observeOn(mSchedulerProvider.main())
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
