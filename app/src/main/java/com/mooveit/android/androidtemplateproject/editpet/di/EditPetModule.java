package com.mooveit.android.androidtemplateproject.editpet.di;

import com.mooveit.android.androidtemplateproject.editpet.EditPetView;
import com.mooveit.android.androidtemplateproject.editpet.EditPetViewModel;
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
