package com.mooveit.android.androidtemplateproject.addpet.presenter;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.mooveit.android.androidtemplateproject.R;
import com.mooveit.android.androidtemplateproject.addpet.di.AddPetComponent;
import com.mooveit.android.androidtemplateproject.addpet.di.AddPetModule;
import com.mooveit.android.androidtemplateproject.addpet.di.DaggerAddPetComponent;
import com.mooveit.android.androidtemplateproject.common.AndroidTemplateApplication;
import com.mooveit.android.androidtemplateproject.common.Constants;
import com.mooveit.android.androidtemplateproject.common.model.entities.Pet;
import com.mooveit.android.androidtemplateproject.common.model.entities.Tag;
import com.mooveit.android.androidtemplateproject.common.ui.activity.BaseActivity;

import java.util.Arrays;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddPetActivity extends BaseActivity implements AddPetView {

    private AddPetComponent mAddPetComponent;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.pet_name_text_input_layout)
    TextInputLayout mPetNameTIL;
    @BindView(R.id.pet_name)
    TextInputEditText mPetNameET;
    @BindString(R.string.empty_name_error)
    String mEmptyNameErrorMessage;

    private Snackbar mSnackbar;

    @Inject
    AddPetViewModel mAddPetViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet);

        ButterKnife.bind(this);

        mAddPetComponent = DaggerAddPetComponent.builder()
                .applicationComponent(AndroidTemplateApplication.getInstance(this).getApplicationComponent())
                .addPetModule(new AddPetModule(this))
                .build();

        mAddPetComponent.inject(this);

        setupToolbar();
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }


    @OnClick(R.id.add_pet_fab)
    void onCreateButtonClicked() {
        if (mPetNameET.getText().toString().isEmpty()) {
            mPetNameTIL.setError(mEmptyNameErrorMessage);
        } else {
            mAddPetViewModel.createPet(createPet());
        }
    }

    private Pet createPet() {

        Pet pet = new Pet();
        pet.setName(mPetNameET.getText().toString());
        Tag tag = new Tag();
        tag.setName(Constants.PET_TAG);
        pet.setTags(Arrays.asList(tag));

        return pet;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAddPetViewModel.onViewDetached();
    }

    @Override
    public void onPetCreated() {

        Snackbar.make(mPetNameET, R.string.created_pet_success, Snackbar.LENGTH_SHORT)
                .addCallback(new Snackbar.Callback() {
                    @Override
                    public void onDismissed(Snackbar snackbar, int event) {
                        finish();
                    }
                })
                .show();
    }

    @Override
    public void hideProgress() {
        if (mSnackbar != null && mSnackbar.isShownOrQueued()) {
            mSnackbar.dismiss();
        }
    }

    @Override
    public void showProgress() {
        mSnackbar = Snackbar.make(mPetNameET, R.string.sending, Snackbar.LENGTH_INDEFINITE);
        mSnackbar.show();
    }

    @Override
    public void showError(String message) {
        mSnackbar = Snackbar.make(mPetNameET, message, Snackbar.LENGTH_SHORT);
        mSnackbar.show();
    }

}
