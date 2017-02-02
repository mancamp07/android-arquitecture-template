package com.mooveit.android.androidtemplateproject.home.di;

import com.mooveit.android.androidtemplateproject.common.di.scopes.PerActivity;
import com.mooveit.android.androidtemplateproject.common.rx.SchedulerProvider;
import com.mooveit.android.androidtemplateproject.home.domain.DeletePetInteractor;
import com.mooveit.android.androidtemplateproject.home.domain.DeletePetInteractorImpl;
import com.mooveit.android.androidtemplateproject.home.domain.GetPetsInteractor;
import com.mooveit.android.androidtemplateproject.home.domain.GetPetsInteractorImpl;
import com.mooveit.android.androidtemplateproject.home.presenter.HomeView;
import com.mooveit.android.androidtemplateproject.home.presenter.HomeViewModel;
import com.mooveit.android.androidtemplateproject.common.model.repository.PetsRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class HomeModule {

    private HomeView mHomeView;

    public HomeModule(HomeView homeView) {
        this.mHomeView = homeView;
    }

    @Provides
    @PerActivity
    GetPetsInteractor provideGetPetsInteractor(PetsRepository petsRepository) {
        return new GetPetsInteractorImpl(petsRepository);
    }

    @Provides
    @PerActivity
    DeletePetInteractor provideDeletePetInteractor(PetsRepository petsRepository) {
        return new DeletePetInteractorImpl(petsRepository);
    }

    @Provides
    @PerActivity
    HomeViewModel provideHomeViewModel(SchedulerProvider schedulerProvider,
                                       GetPetsInteractor getPetsInteractor,
                                       DeletePetInteractor deletePetInteractor) {
        return new HomeViewModel(mHomeView, schedulerProvider,
                getPetsInteractor, deletePetInteractor);
    }
}
