package com.mooveit.android.androidtemplateproject.editpet.presentation;

import com.mooveit.android.androidtemplateproject.common.model.entities.Pet;
import com.mooveit.android.androidtemplateproject.common.presentation.ViewModel;
import com.mooveit.android.androidtemplateproject.common.rx.SchedulerProvider;
import com.mooveit.android.androidtemplateproject.editpet.domain.EditPetInteractor;

import rx.Subscriber;

public class EditPetViewModel extends ViewModel {

    private final EditPetView mEditPetView;
    private final SchedulerProvider mSchedulerProvider;
    private final EditPetInteractor mEditPetInteractor;

    public EditPetViewModel(EditPetView editPetView,
                            SchedulerProvider schedulerProvider,
                            EditPetInteractor editPetInteractor) {
        this.mEditPetView = editPetView;
        this.mSchedulerProvider = schedulerProvider;
        this.mEditPetInteractor = editPetInteractor;
    }

    public void editPet(Pet pet) {
        mEditPetView.showProgress();

        subscribe(
                mEditPetInteractor.editPet(pet)
                        .subscribeOn(mSchedulerProvider.io())
                        .observeOn(mSchedulerProvider.main())
                        .subscribe(new Subscriber<Pet>() {

                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable error) {
                                mEditPetView.hideProgress();
                                mEditPetView.showError(error.getMessage());
                            }

                            @Override
                            public void onNext(Pet pet) {
                                mEditPetView.hideProgress();
                                mEditPetView.showSuccess();
                            }
                        })
        );
    }

}
