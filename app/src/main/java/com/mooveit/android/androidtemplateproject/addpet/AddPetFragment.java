package com.mooveit.android.androidtemplateproject.addpet;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mooveit.android.androidtemplateproject.R;
import com.mooveit.android.androidtemplateproject.addpet.di.AddPetComponent;
import com.mooveit.android.androidtemplateproject.addpet.di.AddPetModule;
import com.mooveit.android.androidtemplateproject.addpet.di.DaggerAddPetComponent;
import com.mooveit.android.androidtemplateproject.common.AndroidTemplateApplication;
import com.mooveit.android.androidtemplateproject.common.Constants;
import com.mooveit.android.androidtemplateproject.model.entities.Pet;
import com.mooveit.android.androidtemplateproject.model.entities.Tag;

import java.util.Arrays;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

import static butterknife.ButterKnife.findById;

public class AddPetFragment extends Fragment implements AddPetView {

    private AddPetComponent mAddPetComponent;

    @BindView(R.id.pet_name_text_input_layout)
    TextInputLayout mPetNameTIL;
    @BindView(R.id.pet_name)
    TextInputEditText mPetNameET;
    @BindView(R.id.create_button)
    FloatingActionButton mCreateButton;
    @BindString(R.string.empty_name_error)
    String mEmptyNameErrorMessage;

    private Snackbar mSnackbar;

    @Inject
    AddPetViewModel mAddPetViewModel;

    public AddPetFragment() {
    }

    public static AddPetFragment newInstance() {
        return new AddPetFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAddPetComponent = DaggerAddPetComponent.builder()
                .applicationComponent(AndroidTemplateApplication.getInstance(getContext()).getApplicationComponent())
                .addPetModule(new AddPetModule(this))
                .build();

        mAddPetComponent.inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_pet, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ButterKnife.bind(this, view);

        setupSubmitCreateFab();
    }

    private void setupSubmitCreateFab() {
        FloatingActionButton createFab = findById(getActivity(), R.id.create_button);
        createFab.setOnClickListener(v -> onCreateButtonClicked());
    }

    public void onCreateButtonClicked() {
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
    public void onPetCreated() {

        Snackbar.make(mPetNameET, R.string.created_pet_success, Snackbar.LENGTH_SHORT)
                .addCallback(new Snackbar.Callback() {
                    @Override
                    public void onDismissed(Snackbar snackbar, int event) {
                        getActivity().finish();
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
