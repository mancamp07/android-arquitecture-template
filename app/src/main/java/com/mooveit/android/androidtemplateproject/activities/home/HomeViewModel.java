package com.mooveit.android.androidtemplateproject.activities.home;

import com.mooveit.android.androidtemplateproject.model.entities.Pet;
import com.mooveit.android.androidtemplateproject.model.repository.PetsRepository;

import java.util.List;

public class HomeViewModel implements PetsRepository.OnGetPetsCallback {

    private static final String TAG = HomeViewModel.class.getSimpleName();

    private final HomeView mHomeView;
    private final PetsRepository mPetsRepository;

    public HomeViewModel(HomeView homeView, PetsRepository petsRepository) {
        this.mHomeView = homeView;
        this.mPetsRepository = petsRepository;
    }

    public void fetchPets() {
        mHomeView.showProgress();
        mPetsRepository.getPets(this);
    }

    @Override
    public void onGetPetsLoaded(List<Pet> pets) {
        mHomeView.hideProgress();

        mHomeView.showPets(pets);
    }
}
