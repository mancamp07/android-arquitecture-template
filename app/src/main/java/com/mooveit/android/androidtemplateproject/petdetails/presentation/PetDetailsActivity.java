package com.mooveit.android.androidtemplateproject.petdetails.presentation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.mooveit.android.androidtemplateproject.R;
import com.mooveit.android.androidtemplateproject.common.AndroidTemplateApplication;
import com.mooveit.android.androidtemplateproject.common.model.entities.Pet;
import com.mooveit.android.androidtemplateproject.common.ui.activity.BaseActivity;
import com.mooveit.android.androidtemplateproject.petdetails.di.DaggerPetDetailsComponent;
import com.mooveit.android.androidtemplateproject.petdetails.di.PetDetailsComponent;
import com.mooveit.android.androidtemplateproject.petdetails.di.PetDetailsModule;

import javax.inject.Inject;

import butterknife.BindView;

public class PetDetailsActivity extends BaseActivity implements PetDetailsView {

    private static final String ARG_PET_ID_EXTRA = "ARG:PET_ID:Long";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.pet_name)
    TextView mPetNameTV;

    @BindView(R.id.pet_status)
    TextView mPetStatusTV;

    @Inject
    PetDetailsViewModel mPetDetailsViewModel;

    private long mPetId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_details);

        PetDetailsComponent petDetailsComponent = DaggerPetDetailsComponent.builder()
                .applicationComponent(AndroidTemplateApplication.getInstance(this)
                        .getApplicationComponent())
                .petDetailsModule(new PetDetailsModule(this))
                .build();

        petDetailsComponent.inject(this);

        retrieveExtras();
        setupToolbar();

        mPetDetailsViewModel.getPet(mPetId);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPetDetailsViewModel.onViewDetached();
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void retrieveExtras() {
        mPetId = getIntent().getLongExtra(ARG_PET_ID_EXTRA, -1);
    }

    public static Intent startActivityIntent(Context context, long petId) {
        Intent intent = new Intent(context, PetDetailsActivity.class);
        intent.putExtra(ARG_PET_ID_EXTRA, petId);
        return intent;
    }

    @Override
    public void showPet(Pet pet) {
        mToolbar.setTitle(pet.getName());
        mPetNameTV.setText(pet.getName());
        mPetStatusTV.setText(pet.getStatus());
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }
}
