package com.mooveit.android.androidtemplateproject.petdetails.di;

import com.mooveit.android.androidtemplateproject.common.di.scopes.PerActivity;
import com.mooveit.android.androidtemplateproject.common.model.repository.PetsRepository;
import com.mooveit.android.androidtemplateproject.common.rx.SchedulerProvider;
import com.mooveit.android.androidtemplateproject.petdetails.domain.GetPetDetailsInteractor;
import com.mooveit.android.androidtemplateproject.petdetails.domain.GetPetDetailsInteractorImpl;
import com.mooveit.android.androidtemplateproject.petdetails.presentation.PetDetailsPresenter;
import com.mooveit.android.androidtemplateproject.petdetails.presentation.PetDetailsView;

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
    PetDetailsPresenter provideHomeViewModel(SchedulerProvider schedulerProvider,
                                             GetPetDetailsInteractor getPetsInteractor) {
        return new PetDetailsPresenter(mPetDetailsView, schedulerProvider, getPetsInteractor);
    }
}
