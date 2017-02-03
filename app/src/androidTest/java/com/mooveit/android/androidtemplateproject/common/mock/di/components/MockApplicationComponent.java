package com.mooveit.android.androidtemplateproject.common.mock.di.components;

import com.mooveit.android.androidtemplateproject.addpet.AddPetActivityTest;
import com.mooveit.android.androidtemplateproject.common.di.components.ApplicationComponent;
import com.mooveit.android.androidtemplateproject.common.di.modules.ApplicationModule;
import com.mooveit.android.androidtemplateproject.common.mock.di.modules.IdlingResourceModule;
import com.mooveit.android.androidtemplateproject.common.mock.di.modules.MockRepositoriesModule;
import com.mooveit.android.androidtemplateproject.common.mock.di.modules.MockSchedulerModule;
import com.mooveit.android.androidtemplateproject.editpet.EditPetActivityTest;
import com.mooveit.android.androidtemplateproject.home.HomeActivityTest;
import com.mooveit.android.androidtemplateproject.petdetails.PetDetailsActivityTest;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
        modules = {ApplicationModule.class,
                MockRepositoriesModule.class,
                MockSchedulerModule.class,
                IdlingResourceModule.class
        }
)
public interface MockApplicationComponent extends ApplicationComponent {

    void inject(EditPetActivityTest editPetActivityTest);

    void inject(HomeActivityTest homeActivityTest);

    void inject(PetDetailsActivityTest petDetailsActivityTest);

    void inject(AddPetActivityTest addPetActivityTest);
}
