package com.mooveit.android.androidtemplateproject.activities.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewFlipper;

import com.mooveit.android.androidtemplateproject.R;
import com.mooveit.android.androidtemplateproject.activities.editpet.EditPetActivity;
import com.mooveit.android.androidtemplateproject.activities.home.di.DaggerHomeComponent;
import com.mooveit.android.androidtemplateproject.activities.home.di.HomeComponent;
import com.mooveit.android.androidtemplateproject.activities.home.di.HomeModule;
import com.mooveit.android.androidtemplateproject.common.AndroidTemplateApplication;
import com.mooveit.android.androidtemplateproject.common.ui.recyclerview.VerticalSpacingItemDecoration;
import com.mooveit.android.androidtemplateproject.model.entities.Pet;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.BindInt;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment
        implements HomeView,
        PetsListAdapter.OnItemClickListener,
        PetsListAdapter.OnItemDeletedListener {

    private HomeComponent mHomeComponent;

    @BindView(R.id.view_flipper)
    ViewFlipper mViewFlipper;

    @BindView(R.id.pets_list)
    RecyclerView mPetsListRV;

    @BindView(R.id.progressbar)
    ContentLoadingProgressBar mProgressBar;

    @BindView(R.id.empty_pets_list)
    View mPetsListEmptyView;

    @BindInt(R.integer.list_item_vertical_spacing)
    int mItemVerticalSpacing;

    @Inject
    HomeViewModel mViewModel;

    PetsListAdapter mPetsListAdapter;

    @Inject
    DefaultItemAnimator mDefaultItemAnimator;

    @Inject
    DividerItemDecoration mDividerItemDecoration;

    @Inject
    RecyclerView.LayoutManager mLayoutManager;

    private final RecyclerView.AdapterDataObserver mAdapterDataObserver = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onChanged() {
            onPetsListChanged();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            onPetsListChanged();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            onPetsListChanged();
        }
    };

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
    public void onResume() {
        super.onResume();

        mViewModel.fetchPets();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ButterKnife.bind(this, view);

        setupRecyclerView();
    }

    @Override
    public void onDestroyView() {
        mPetsListAdapter.unregisterAdapterDataObserver(mAdapterDataObserver);
        super.onDestroyView();
    }

    private void setupRecyclerView() {

        mPetsListAdapter = new PetsListAdapter(new ArrayList<>(0), this, this);

        mPetsListRV.setItemAnimator(new DefaultItemAnimator());
        mPetsListRV.addItemDecoration(new VerticalSpacingItemDecoration(mItemVerticalSpacing));
        mPetsListRV.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false)
        );
        mPetsListRV.setHasFixedSize(true);
        mPetsListRV.setAdapter(mPetsListAdapter);
        mPetsListAdapter.registerAdapterDataObserver(mAdapterDataObserver);
    }

    public void showPets(List<Pet> pets) {
        mPetsListAdapter.setPets(pets);
    }

    @Override
    public void showPet(Pet pet) {
    }

    @Override
    public void showEditPet(Pet pet) {
        Intent intent = new Intent(getContext(), EditPetActivity.class);
        intent.putExtra(EditPetActivity.ARG_PET_EXTRAS, pet);
        startActivity(intent);
    }

    @Override
    public void showPets() {
        mPetsListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String message) {
        Snackbar.make(mPetsListRV, message, Snackbar.LENGTH_SHORT)
                .show();
    }

    @Override
    public void onItemClick(Pet pet) {
        showPet(pet);
    }

    @Override
    public void onEditItemClick(Pet pet) {
        showEditPet(pet);
    }

    @Override
    public void onPetItemDeleted(Pet pet) {
        mViewModel.onDeletePet(pet);
    }

    public void showEmptyView() {
        mViewFlipper.setDisplayedChild(mViewFlipper.indexOfChild(mPetsListEmptyView));
    }

    public void showListView() {
        mViewFlipper.setDisplayedChild(mViewFlipper.indexOfChild(mPetsListRV));
    }

    public void showProgress() {
        mViewFlipper.setDisplayedChild(mViewFlipper.indexOfChild(mProgressBar));
        mProgressBar.show();
    }

    public void hideProgress() {
        mProgressBar.hide();
    }

    private void onPetsListChanged() {
        if (mPetsListAdapter.getItemCount() == 0) {
            showEmptyView();
        } else {
            showListView();
        }
    }
}
