package com.mooveit.android.androidtemplateproject.home.di;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mooveit.android.androidtemplateproject.R;
import com.mooveit.android.androidtemplateproject.home.HomeView;
import com.mooveit.android.androidtemplateproject.home.HomeViewModel;
import com.mooveit.android.androidtemplateproject.common.di.scopes.PerActivity;
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

    @Provides
    @PerActivity
    DefaultItemAnimator provideDefaultItemAnimator() {
        return new DefaultItemAnimator();
    }

    @Provides
    @PerActivity
    RecyclerView.LayoutManager provideLayoutManager(Context context) {
        return new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
    }

    @Provides
    @PerActivity
    DividerItemDecoration provideDividerItemDecoration(Context context) {
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(context, R.drawable.divider));

        return dividerItemDecoration;
    }
}
