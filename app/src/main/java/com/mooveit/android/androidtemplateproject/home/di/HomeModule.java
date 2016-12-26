package com.mooveit.android.androidtemplateproject.home.di;

import com.mooveit.android.androidtemplateproject.common.di.scopes.PerActivity;
import com.mooveit.android.androidtemplateproject.home.HomeView;
import com.mooveit.android.androidtemplateproject.home.HomeViewModel;
import com.mooveit.android.androidtemplateproject.model.repository.PetsRepository;

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
    HomeViewModel provideHomeViewModel(PetsRepository petsRepository) {
        return new HomeViewModel(mHomeView, petsRepository);
    }
}
