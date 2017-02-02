package com.mooveit.android.androidtemplateproject.addpet.di;

import com.mooveit.android.androidtemplateproject.addpet.domain.AddPetInteractor;
import com.mooveit.android.androidtemplateproject.addpet.domain.AddPetInteractorImpl;
import com.mooveit.android.androidtemplateproject.addpet.presenter.AddPetView;
import com.mooveit.android.androidtemplateproject.addpet.presenter.AddPetViewModel;
import com.mooveit.android.androidtemplateproject.common.di.scopes.PerActivity;
import com.mooveit.android.androidtemplateproject.common.model.repository.PetsRepository;
import com.mooveit.android.androidtemplateproject.common.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class AddPetModule {

    private final AddPetView mAddPetView;

    public AddPetModule(AddPetView mAddPetView) {
        this.mAddPetView = mAddPetView;
    }

    @Provides
    @PerActivity
    AddPetInteractor provideAddPetInteractor(PetsRepository petsRepository) {
        return new AddPetInteractorImpl(petsRepository);
    }

    @Provides
    @PerActivity
    AddPetViewModel provideAddPetViewModel(SchedulerProvider schedulerProvider,
                                           AddPetInteractor addPetInteractor) {
        return new AddPetViewModel(mAddPetView, schedulerProvider, addPetInteractor);
    }

}
