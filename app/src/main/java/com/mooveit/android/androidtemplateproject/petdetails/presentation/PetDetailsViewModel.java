package com.mooveit.android.androidtemplateproject.petdetails.presentation;

import com.mooveit.android.androidtemplateproject.common.model.entities.Pet;
import com.mooveit.android.androidtemplateproject.common.presentation.ViewModel;
import com.mooveit.android.androidtemplateproject.common.rx.SchedulerProvider;
import com.mooveit.android.androidtemplateproject.petdetails.domain.GetPetDetailsInteractor;

import rx.Subscriber;

public class PetDetailsViewModel extends ViewModel {

    private final GetPetDetailsInteractor mGetPetDetailsInteractor;
    private final SchedulerProvider mSchedulerProvider;
    private final PetDetailsView mPetDetailsView;

    public PetDetailsViewModel(PetDetailsView petDetailsView,
                               SchedulerProvider schedulerProvider, GetPetDetailsInteractor getPetDetailsInteractor) {
        this.mPetDetailsView = petDetailsView;
        this.mSchedulerProvider = schedulerProvider;
        this.mGetPetDetailsInteractor = getPetDetailsInteractor;
    }

    public void getPet(long petId) {

        mPetDetailsView.showProgress();

        subscribe(
                mGetPetDetailsInteractor.getPet(petId)
                        .subscribeOn(mSchedulerProvider.io())
                        .observeOn(mSchedulerProvider.main())
                        .subscribe(new Subscriber<Pet>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                mPetDetailsView.hideProgress();
                                mPetDetailsView.showError(e.getMessage());
                            }

                            @Override
                            public void onNext(Pet pet) {
                                mPetDetailsView.hideProgress();
                                mPetDetailsView.showPet(pet);
                            }
                        })
        );
    }
}
