package com.mooveit.android.androidtemplateproject.activities.addpet;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mooveit.android.androidtemplateproject.R;
import com.mooveit.android.androidtemplateproject.activities.addpet.di.AddPetComponent;
import com.mooveit.android.androidtemplateproject.activities.addpet.di.AddPetModule;
import com.mooveit.android.androidtemplateproject.activities.addpet.di.DaggerAddPetComponent;
import com.mooveit.android.androidtemplateproject.common.AndroidTemplateApplication;
import com.mooveit.android.androidtemplateproject.model.Pet;
import com.mooveit.android.androidtemplateproject.model.Tag;

import java.util.Arrays;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AddPetFragment extends Fragment implements AddPetView {

    private AddPetComponent mAddPetComponent;

    @Bind(R.id.pet_name)
    EditText mPetNameET;

    @Bind(R.id.pet_tag)
    EditText mPetTagET;

    @Bind(R.id.create_button)
    Button mCreateButton;

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

        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAddPetViewModel.createPet(createPet());
            }
        });

    }

    private Pet createPet() {

        Pet pet = new Pet();
        pet.setName(mPetNameET.getText().toString());
        Tag tag = new Tag();
        tag.setName(mPetTagET.getText().toString());
        pet.setTags(Arrays.asList(tag));

        return pet;
    }

    @Override
    public void onPetCreated() {

        Toast.makeText(getContext(), "Mascota creada!", Toast.LENGTH_SHORT)
                .show();

        getActivity().finish();
    }

    @Override
    public void hideProgress() {
        if (mSnackbar != null && mSnackbar.isShownOrQueued()) {
            mSnackbar.dismiss();
        }
    }

    @Override
    public void showProgress() {
        mSnackbar = Snackbar.make(mCreateButton, "Enviando...", Snackbar.LENGTH_INDEFINITE);
        mSnackbar.show();
    }
}
