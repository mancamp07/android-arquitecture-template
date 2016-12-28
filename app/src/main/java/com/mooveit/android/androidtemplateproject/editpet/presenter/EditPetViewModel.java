package com.mooveit.android.androidtemplateproject.editpet.presenter;

import com.mooveit.android.androidtemplateproject.common.model.entities.Pet;
import com.mooveit.android.androidtemplateproject.common.presenter.ViewModel;
import com.mooveit.android.androidtemplateproject.editpet.domain.EditPetInteractor;

import rx.SingleSubscriber;

public class EditPetViewModel extends ViewModel {

    private final EditPetView mEditPetView;
    private final EditPetInteractor mEditPetInteractor;

    public EditPetViewModel(EditPetView editPetView, EditPetInteractor editPetInteractor) {
        this.mEditPetView = editPetView;
        this.mEditPetInteractor = editPetInteractor;
    }

    public void editPet(Pet pet) {
        mEditPetView.showProgress();

        subscribe(
                mEditPetInteractor.editPet(pet)
                        .subscribe(new SingleSubscriber<Pet>() {
                            @Override
                            public void onSuccess(Pet pet) {
                                mEditPetView.hideProgress();
                                mEditPetView.showSuccess();
                            }

                            @Override
                            public void onError(Throwable error) {
                                mEditPetView.hideProgress();
                                mEditPetView.showError(error.getMessage());
                            }
                        })
        );
    }

}
