package com.mooveit.android.androidtemplateproject.activities.home;

import android.content.Context;

import com.mooveit.android.androidtemplateproject.model.Pet;

import java.util.List;

public interface HomeView {

    Context getContext();

    void showProgress();

    void hideProgress();

    void showPets(List<Pet> pets);

}
