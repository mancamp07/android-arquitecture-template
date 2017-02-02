package com.mooveit.android.androidtemplateproject.addpet.presentation;

public interface AddPetView {

    void showPetCreated();

    void hideProgress();

    void showProgress();

    void showError(String message);
}
