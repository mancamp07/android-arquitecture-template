package com.mooveit.android.androidtemplateproject.home.presenter;

import com.mooveit.android.androidtemplateproject.common.model.entities.Pet;
import com.mooveit.android.androidtemplateproject.common.presenter.ViewModel;
import com.mooveit.android.androidtemplateproject.home.domain.DeletePetInteractor;
import com.mooveit.android.androidtemplateproject.home.domain.GetPetsInteractor;

import java.util.List;

import rx.SingleSubscriber;

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

    public void fetchPets() {
        mHomeView.showProgress();

        subscribe(
                mGetPetsInteractor.getPets()
                        .subscribe(new SingleSubscriber<List<Pet>>() {
                            @Override
                            public void onSuccess(List<Pet> pets) {
                                mHomeView.hideProgress();
                                mHomeView.showPets(pets);
                            }

                            @Override
                            public void onError(Throwable error) {
                                mHomeView.hideProgress();
                                mHomeView.showError(error.getMessage());
                                mHomeView.showPets();
                            }
                        })
        );
    }

    public void onDeletePet(Pet pet) {
        subscribe(
                mDeletePetInteractor.deletePet(pet)
                        .subscribe(new SingleSubscriber<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                mHomeView.showPets();
                            }

                            @Override
                            public void onError(Throwable error) {
                                mHomeView.showError(error.getMessage());
                            }
                        })
        );
    }
}
