package com.mooveit.android.androidtemplateproject.editpet.presenter;

import com.mooveit.android.androidtemplateproject.common.model.entities.Pet;
import com.mooveit.android.androidtemplateproject.common.presenter.ViewModel;
import com.mooveit.android.androidtemplateproject.editpet.domain.EditPetInteractor;

import rx.SingleSubscriber;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
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
