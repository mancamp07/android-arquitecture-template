package com.mooveit.android.androidtemplateproject.activities.home.di;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mooveit.android.androidtemplateproject.activities.home.HomeView;
import com.mooveit.android.androidtemplateproject.activities.home.HomeViewModel;
import com.mooveit.android.androidtemplateproject.activities.home.PetsListAdapter;
import com.mooveit.android.androidtemplateproject.common.di.scopes.PerActivity;
import com.mooveit.android.androidtemplateproject.model.Pet;
import com.mooveit.android.androidtemplateproject.network.PetStoreService;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class HomeModule {

    private HomeView mHomeView;

    public HomeModule(HomeView homeView) {
        this.mHomeView = homeView;
    }

    @Provides @PerActivity
    HomeViewModel provideHomeViewModel(PetStoreService petStoreService) {
        return new HomeViewModel(mHomeView, petStoreService);
    }

    @Provides @PerActivity
    PetsListAdapter providePetsListAdapter() {
        return new PetsListAdapter(new ArrayList<Pet>(0));
    }

    @Provides @PerActivity
    DefaultItemAnimator provideDefaultItemAnimator() {
        return new DefaultItemAnimator();
    }

    @Provides @PerActivity
    RecyclerView.LayoutManager provideLayoutManager() {
        return new LinearLayoutManager(mHomeView.getContext(), LinearLayoutManager.VERTICAL, false);
    }
}
