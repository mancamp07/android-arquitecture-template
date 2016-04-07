package com.mooveit.android.androidtemplateproject.activities.editpet;

import com.mooveit.android.androidtemplateproject.common.views.ProgressView;

public interface EditPetView extends ProgressView {

    void showSuccess();
    void showError(String message);

}
