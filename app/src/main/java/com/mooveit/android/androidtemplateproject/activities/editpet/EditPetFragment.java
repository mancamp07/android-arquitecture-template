package com.mooveit.android.androidtemplateproject.activities.editpet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mooveit.android.androidtemplateproject.R;
import com.mooveit.android.androidtemplateproject.activities.editpet.di.DaggerEditPetComponent;
import com.mooveit.android.androidtemplateproject.activities.editpet.di.EditPetComponent;
import com.mooveit.android.androidtemplateproject.activities.editpet.di.EditPetModule;
import com.mooveit.android.androidtemplateproject.common.AndroidTemplateApplication;
import com.mooveit.android.androidtemplateproject.model.entities.Pet;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

import static butterknife.ButterKnife.findById;

public class EditPetFragment extends Fragment implements EditPetView {

    public static final String TAG = EditPetFragment.class.getSimpleName();
    public static final String ARG_PET = ":Arg:Pet:Parcelable";

    private EditPetComponent mEditPetComponent;
    private Pet mPet;
    private Snackbar mSnackbar;

    @BindView(R.id.name_text_input_layout) TextInputLayout mPetNameTIL;
    @BindView(R.id.name_edit_text) TextInputEditText mPetNameET;
    @BindString(R.string.empty_name_error) String mEmptyNameErrorMessage;

    @Inject
    EditPetViewModel mEditPetViewModel;

    public EditPetFragment() {}

    public static EditPetFragment newInstance(Pet pet) {
        EditPetFragment fragment = new EditPetFragment();

        Bundle args = new Bundle();
        args.putParcelable(ARG_PET, pet);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mEditPetComponent = DaggerEditPetComponent.builder()
                .applicationComponent(AndroidTemplateApplication.getInstance(getContext()).getApplicationComponent())
                .editPetModule(new EditPetModule(this))
                .build();

        mEditPetComponent.inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_pet, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);

        retrieveArgs();

        setupSubmitEditFab();

        mPetNameET.setText(mPet.getName());
    }

    private void setupSubmitEditFab() {
        FloatingActionButton editFab = findById(getActivity(), R.id.submit_edit_fab);
        editFab.setOnClickListener(v -> onEditPet());
    }

    private void onEditPet() {
        String newName = mPetNameET.getText().toString();
        if (newName.isEmpty()) {
            mPetNameTIL.setError(mEmptyNameErrorMessage);
        } else {
            mPet.setName(newName);
            mEditPetViewModel.editPet(mPet);
        }
    }

    private void retrieveArgs() {
        Bundle args = getArguments();
        if (args != null) {
            mPet = args.getParcelable(ARG_PET);
        }
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
    public void showSuccess() {
        mSnackbar = Snackbar.make(mPetNameET, R.string.pet_edit_success, Snackbar.LENGTH_SHORT);
        mSnackbar.show();
    }

    @Override
    public void showError(String message) {
        mSnackbar = Snackbar.make(mPetNameET, message, Snackbar.LENGTH_SHORT);
        mSnackbar.show();
    }
}
