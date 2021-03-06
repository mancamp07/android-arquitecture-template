package com.mooveit.android.androidtemplateproject.addpet.di;

import com.mooveit.android.androidtemplateproject.addpet.presentation.AddPetActivity;
import com.mooveit.android.androidtemplateproject.common.di.components.ApplicationComponent;
import com.mooveit.android.androidtemplateproject.common.di.scopes.PerActivity;

import dagger.Component;

@PerActivity
@Component(
        dependencies = ApplicationComponent.class,
        modules = AddPetModule.class
)
public interface AddPetComponent {

    void inject(AddPetActivity addPetActivity);
}
