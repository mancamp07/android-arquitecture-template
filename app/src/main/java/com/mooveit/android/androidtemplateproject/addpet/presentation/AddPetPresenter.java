package com.mooveit.android.androidtemplateproject.addpet.presentation;

import com.mooveit.android.androidtemplateproject.addpet.domain.AddPetInteractor;
import com.mooveit.android.androidtemplateproject.common.model.entities.Pet;
import com.mooveit.android.androidtemplateproject.common.presentation.Presenter;
import com.mooveit.android.androidtemplateproject.common.rx.SchedulerProvider;

import rx.Subscriber;

public class AddPetPresenter extends Presenter {

    private static final String TAG = AddPetPresenter.class.getSimpleName();

    private final AddPetView mAddPetView;
    private final SchedulerProvider mSchedulerProvider;
    private final AddPetInteractor mAddPetInteractor;

    public AddPetPresenter(AddPetView addPetView,
                           SchedulerProvider schedulerProvider, AddPetInteractor addPetInteractor) {
        this.mAddPetView = addPetView;
        this.mSchedulerProvider = schedulerProvider;
        this.mAddPetInteractor = addPetInteractor;
    }

    public void createPet(Pet pet) {

        mAddPetView.showProgress();

        subscribe(
                mAddPetInteractor.addPet(pet)
                        .subscribeOn(mSchedulerProvider.io())
                        .observeOn(mSchedulerProvider.main())
                        .subscribe(new Subscriber<Pet>() {

                            @Override
                            public void onCompleted() {
                            }

                            @Override
                            public void onError(Throwable error) {
                                mAddPetView.hideProgress();
                                mAddPetView.showError(error.getMessage());
                            }

                            @Override
                            public void onNext(Pet pet) {
                                mAddPetView.hideProgress();
                                mAddPetView.showPetCreated();
                            }
                        })
        );

    }
}
