package com.mooveit.android.androidtemplateproject.activities.editpet.di;

import com.mooveit.android.androidtemplateproject.activities.editpet.EditPetFragment;
import com.mooveit.android.androidtemplateproject.common.di.components.ApplicationComponent;
import com.mooveit.android.androidtemplateproject.common.di.scopes.PerActivity;

import dagger.Component;

@PerActivity
@Component(
        dependencies = ApplicationComponent.class,
        modules = EditPetModule.class
)
public interface EditPetComponent {

    void inject(EditPetFragment editPetFragment);
}
