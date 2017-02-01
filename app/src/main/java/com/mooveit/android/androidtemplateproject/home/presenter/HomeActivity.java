package com.mooveit.android.androidtemplateproject.home.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mooveit.android.androidtemplateproject.R;
import com.mooveit.android.androidtemplateproject.addpet.presenter.AddPetActivity;
import com.mooveit.android.androidtemplateproject.common.AndroidTemplateApplication;
import com.mooveit.android.androidtemplateproject.common.model.entities.Pet;
import com.mooveit.android.androidtemplateproject.common.ui.activity.BaseActivity;
import com.mooveit.android.androidtemplateproject.common.ui.recyclerview.VerticalSpacingItemDecoration;
import com.mooveit.android.androidtemplateproject.editpet.presenter.EditPetActivity;
import com.mooveit.android.androidtemplateproject.home.di.DaggerHomeComponent;
import com.mooveit.android.androidtemplateproject.home.di.HomeComponent;
import com.mooveit.android.androidtemplateproject.home.di.HomeModule;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity implements HomeView,
        PetsListAdapter.OnItemClickListener,
        PetsListAdapter.OnItemDeletedListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.pets_list)
    RecyclerView mPetsListRV;

    @BindView(R.id.progressbar)
    ContentLoadingProgressBar mProgressBar;

    @BindView(R.id.empty_pets_list)
    View mPetsListEmptyView;

    @BindInt(R.integer.list_item_vertical_spacing)
    int mItemVerticalSpacing;

    @Inject
    HomeViewModel mHomeViewModel;

    private PetsListAdapter mPetsListAdapter;

    private Snackbar mSyncPetsSnackbar;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        HomeComponent homeComponent = DaggerHomeComponent.builder()
                .applicationComponent(AndroidTemplateApplication
                        .getInstance(this).getApplicationComponent())
                .homeModule(new HomeModule(this))
                .build();

        homeComponent.inject(this);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        setupRecyclerView();
        setupSwipeRefresh();

    }


    @Override
    protected void onResume() {
        super.onResume();

        mHomeViewModel.fetchPets(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHomeViewModel.onViewDetached();
        mPetsListAdapter.unregisterAdapterDataObserver(mAdapterDataObserver);
    }

    private void setupRecyclerView() {

        mPetsListAdapter = new PetsListAdapter(new ArrayList<>(0), this, this);

        mPetsListRV.setItemAnimator(new DefaultItemAnimator());
        mPetsListRV.addItemDecoration(new VerticalSpacingItemDecoration(mItemVerticalSpacing));
        mPetsListRV.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        );
        mPetsListRV.setHasFixedSize(true);
        mPetsListRV.setAdapter(mPetsListAdapter);
        mPetsListAdapter.registerAdapterDataObserver(mAdapterDataObserver);
    }

    private void setupSwipeRefresh() {
        mSwipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(this, R.color.colorAccent),
                ContextCompat.getColor(this, R.color.colorPrimary),
                ContextCompat.getColor(this, R.color.colorPrimaryDark)
        );
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    public void showPets(List<Pet> pets) {
        mPetsListAdapter.setPets(pets);
    }

    @Override
    public void showPet(Pet pet) {
        Intent intent = PetDetailsActivity.startActivityIntent(this, pet.getExternalId());
        startActivity(intent);
    }

    @Override
    public void showEditPet(Pet pet) {
        Intent intent = new Intent(this, EditPetActivity.class);
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
        mHomeViewModel.onDeletePet(pet);
    }

    public void showEmptyView() {
        mPetsListEmptyView.setVisibility(View.VISIBLE);
        mPetsListRV.setVisibility(View.GONE);
    }

    public void showListView() {
        mPetsListEmptyView.setVisibility(View.GONE);
        mPetsListRV.setVisibility(View.VISIBLE);
    }

    public void showProgress() {
        if (!mSwipeRefreshLayout.isRefreshing()) {
            mPetsListEmptyView.setVisibility(View.GONE);
            mPetsListRV.setVisibility(View.GONE);
            mProgressBar.show();
        } else {
            mSyncPetsSnackbar = Snackbar.make(mToolbar, R.string.synching_pets_message, Snackbar.LENGTH_INDEFINITE);
            mSyncPetsSnackbar.show();
        }
    }

    public void hideProgress() {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        if (mSyncPetsSnackbar != null && mSyncPetsSnackbar.isShownOrQueued()) {
            mSyncPetsSnackbar.dismiss();
        }
        mProgressBar.hide();
    }

    private void onPetsListChanged() {
        if (mPetsListAdapter.getItemCount() == 0) {
            showEmptyView();
        } else {
            showListView();
        }
    }

    @OnClick(R.id.add_pet_fab)
    public void onAddButtonClicked() {
        startActivity(new Intent(HomeActivity.this, AddPetActivity.class));
    }

    @Override
    public void onRefresh() {
        mHomeViewModel.fetchPets(true);
    }
}
