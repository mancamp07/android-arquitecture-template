package com.mooveit.android.androidtemplateproject.addpet;

public interface AddPetView {

    void onPetCreated();

    void hideProgress();

    void showProgress();

    void showError(String message);
}
