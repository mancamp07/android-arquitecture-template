package com.mooveit.android.androidtemplateproject.activities.addpet;

public interface AddPetView {

    void onPetCreated();

    void hideProgress();

    void showProgress();

    void showError(String message);
}
