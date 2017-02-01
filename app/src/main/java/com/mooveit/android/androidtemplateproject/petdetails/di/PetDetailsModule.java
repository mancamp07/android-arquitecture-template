package com.mooveit.android.androidtemplateproject.petdetails.di;

import com.mooveit.android.androidtemplateproject.common.di.scopes.PerActivity;
import com.mooveit.android.androidtemplateproject.common.model.repository.PetsRepository;
import com.mooveit.android.androidtemplateproject.home.domain.DeletePetInteractor;
import com.mooveit.android.androidtemplateproject.home.domain.DeletePetInteractorImpl;
import com.mooveit.android.androidtemplateproject.home.domain.GetPetsInteractor;
import com.mooveit.android.androidtemplateproject.home.domain.GetPetsInteractorImpl;
import com.mooveit.android.androidtemplateproject.home.presenter.HomeView;
import com.mooveit.android.androidtemplateproject.home.presenter.HomeViewModel;
import com.mooveit.android.androidtemplateproject.petdetails.domain.GetPetDetailsInteractor;
import com.mooveit.android.androidtemplateproject.petdetails.domain.GetPetDetailsInteractorImpl;
import com.mooveit.android.androidtemplateproject.petdetails.presenter.PetDetailsView;
import com.mooveit.android.androidtemplateproject.petdetails.presenter.PetDetailsViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class PetDetailsModule {

    private final PetDetailsView mPetDetailsView;

    public PetDetailsModule(PetDetailsView petDetailsView) {
        this.mPetDetailsView = petDetailsView;
    }

    @Provides
    @PerActivity
    GetPetDetailsInteractor provideGetPetDetailsInteractor(PetsRepository petsRepository) {
        return new GetPetDetailsInteractorImpl(petsRepository);
    }

    @Provides
    @PerActivity
    PetDetailsViewModel provideHomeViewModel(GetPetDetailsInteractor getPetsInteractor) {
        return new PetDetailsViewModel(mPetDetailsView, getPetsInteractor);
    }
}
