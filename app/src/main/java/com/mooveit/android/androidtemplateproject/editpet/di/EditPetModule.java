package com.mooveit.android.androidtemplateproject.editpet.di;

import com.mooveit.android.androidtemplateproject.common.di.scopes.PerActivity;
import com.mooveit.android.androidtemplateproject.common.rx.SchedulerProvider;
import com.mooveit.android.androidtemplateproject.editpet.domain.EditPetInteractor;
import com.mooveit.android.androidtemplateproject.editpet.domain.EditPetInteractorImpl;
import com.mooveit.android.androidtemplateproject.editpet.presentation.EditPetView;
import com.mooveit.android.androidtemplateproject.editpet.presentation.EditPetViewModel;
import com.mooveit.android.androidtemplateproject.common.model.repository.PetsRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class EditPetModule {

    private final EditPetView mEditPetView;

    public EditPetModule(EditPetView editPetView) {
        this.mEditPetView = editPetView;
    }

    @Provides
    @PerActivity
    EditPetInteractor provideEditPetInteractor(PetsRepository petsRepository) {
        return new EditPetInteractorImpl(petsRepository);
    }

    @Provides
    @PerActivity
    EditPetViewModel provideEditPetViewModel(SchedulerProvider schedulerProvider,
                                             EditPetInteractor editPetInteractor) {
        return new EditPetViewModel(mEditPetView, schedulerProvider, editPetInteractor);
    }
}
