package com.mooveit.android.androidtemplateproject.editpet;

import com.mooveit.android.androidtemplateproject.common.BaseTest;
import com.mooveit.android.androidtemplateproject.common.model.entities.Pet;
import com.mooveit.android.androidtemplateproject.editpet.domain.EditPetInteractor;
import com.mooveit.android.androidtemplateproject.editpet.presentation.EditPetPresenter;
import com.mooveit.android.androidtemplateproject.editpet.presentation.EditPetView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;

import rx.Observable;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class EditPetPresenterTest extends BaseTest {

    @Mock
    Pet mMockPet;

    @Mock
    EditPetView mEditPetView;

    private Throwable mError = new Throwable("Error!");
    private EditPetPresenter mEditPetPresenter;

    @Mock
    EditPetInteractor mEditPetInteractor;

    @Before
    public void setup() {
        mEditPetPresenter = new EditPetPresenter(mEditPetView, mSchedulerProvider, mEditPetInteractor);
    }

    @Test
    public void editPet_successShowsMessage() {

        when(mEditPetInteractor.editPet(mMockPet))
                .thenReturn(Observable.just(mMockPet));

        mEditPetPresenter.editPet(mMockPet);

        InOrder inOrder = inOrder(mEditPetView, mEditPetInteractor);
        inOrder.verify(mEditPetView).showProgress();
        inOrder.verify(mEditPetInteractor).editPet(mMockPet);
        inOrder.verify(mEditPetView).hideProgress();
        inOrder.verify(mEditPetView).showSuccess();

        verifyNoMoreInteractions(mEditPetView);
        verifyNoMoreInteractions(mEditPetInteractor);
    }

    @Test
    public void editPet_failureShowsMessage() {

        when(mEditPetInteractor.editPet(mMockPet))
                .thenReturn(Observable.error(mError));

        mEditPetPresenter.editPet(mMockPet);

        InOrder inOrder = inOrder(mEditPetView, mEditPetInteractor);
        inOrder.verify(mEditPetView).showProgress();
        inOrder.verify(mEditPetInteractor).editPet(mMockPet);
        inOrder.verify(mEditPetView).hideProgress();
        inOrder.verify(mEditPetView).showError(mError.getMessage());

        verifyNoMoreInteractions(mEditPetView);
        verifyNoMoreInteractions(mEditPetInteractor);
    }
}