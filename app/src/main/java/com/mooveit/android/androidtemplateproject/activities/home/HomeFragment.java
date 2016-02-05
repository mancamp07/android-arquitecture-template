package com.mooveit.android.androidtemplateproject.activities.home;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mooveit.android.androidtemplateproject.R;
import com.mooveit.android.androidtemplateproject.activities.home.di.DaggerHomeComponent;
import com.mooveit.android.androidtemplateproject.activities.home.di.HomeComponent;
import com.mooveit.android.androidtemplateproject.activities.home.di.HomeModule;
import com.mooveit.android.androidtemplateproject.common.AndroidTemplateApplication;
import com.mooveit.android.androidtemplateproject.model.Pet;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment implements HomeView {

    private HomeComponent mHomeComponent;
    private Snackbar mSnackbar;

    @Bind(R.id.pets_list) RecyclerView mRecyclerView;

    @Inject
    HomeViewModel mViewModel;

    @Inject
    PetsListAdapter mPetsListAdapter;

    @Inject
    DefaultItemAnimator mDefaultItemAnimator;

    @Inject
    RecyclerView.LayoutManager mLayoutManager;

    public HomeFragment() {
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mHomeComponent = DaggerHomeComponent.builder()
                .applicationComponent(AndroidTemplateApplication
                        .getInstance(getContext()).getApplicationComponent())
                .homeModule(new HomeModule(this))
                .build();

        mHomeComponent.inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ButterKnife.bind(this, view);

        setupRecyclerView();

        mViewModel.fetchPets();
    }

    private void setupRecyclerView() {
        mRecyclerView.setItemAnimator(mDefaultItemAnimator);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mPetsListAdapter);
    }

    public void showPets(List<Pet> pets) {
        mPetsListAdapter.setPets(pets);
    }

    public void showProgress() {
        mSnackbar = Snackbar.make(mRecyclerView,
                "Cargando mascotas...", Snackbar.LENGTH_INDEFINITE);
        mSnackbar.show();
    }

    public void hideProgress() {
        if (mSnackbar != null) {
            mSnackbar.dismiss();
        }
    }
}
