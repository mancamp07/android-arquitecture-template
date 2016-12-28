package com.mooveit.android.androidtemplateproject.editpet.presenter;

import com.mooveit.android.androidtemplateproject.common.views.ProgressView;

public interface EditPetView extends ProgressView {

    void showSuccess();

    void showError(String message);
}
