package com.mooveit.android.androidtemplateproject.editpet.presentation;

import com.mooveit.android.androidtemplateproject.common.views.ProgressView;

public interface EditPetView extends ProgressView {

    void showSuccess();

    void showError(String message);
}
