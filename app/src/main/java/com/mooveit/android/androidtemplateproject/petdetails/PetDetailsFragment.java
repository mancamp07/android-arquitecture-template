package com.mooveit.android.androidtemplateproject.petdetails;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mooveit.android.androidtemplateproject.R;

public class PetDetailsFragment extends Fragment {

    public PetDetailsFragment() {
    }

    public static PetDetailsFragment newInstance() {
        return new PetDetailsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pet_details, container, false);
    }
}
