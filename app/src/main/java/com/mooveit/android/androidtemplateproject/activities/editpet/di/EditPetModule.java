package com.mooveit.android.androidtemplateproject.activities.editpet.di;

import com.mooveit.android.androidtemplateproject.activities.editpet.EditPetView;
import com.mooveit.android.androidtemplateproject.activities.editpet.EditPetViewModel;
import com.mooveit.android.androidtemplateproject.model.repository.PetsRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class EditPetModule {

    private final EditPetView mEditPetView;

    public EditPetModule(EditPetView editPetView) {
        this.mEditPetView = editPetView;
    }

    @Provides
    EditPetViewModel providesEditPetViewModel(PetsRepository petsRepository) {
        return new EditPetViewModel(mEditPetView, petsRepository);
    }
}
