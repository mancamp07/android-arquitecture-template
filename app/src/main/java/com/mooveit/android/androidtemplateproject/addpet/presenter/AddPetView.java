package com.mooveit.android.androidtemplateproject.addpet.presenter;

public interface AddPetView {

    void showPetCreated();

    void hideProgress();

    void showProgress();

    void showError(String message);
}
