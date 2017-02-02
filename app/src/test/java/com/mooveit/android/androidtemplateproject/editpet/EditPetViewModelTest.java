package com.mooveit.android.androidtemplateproject.editpet;

import com.mooveit.android.androidtemplateproject.common.BaseTest;
import com.mooveit.android.androidtemplateproject.common.model.entities.Pet;
import com.mooveit.android.androidtemplateproject.editpet.domain.EditPetInteractor;
import com.mooveit.android.androidtemplateproject.editpet.presentation.EditPetView;
import com.mooveit.android.androidtemplateproject.editpet.presentation.EditPetViewModel;

import org.junit.Before;
import org.mockito.Mock;

import rx.Observable;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class EditPetViewModelTest extends BaseTest {

    @Mock
    Pet mMockPet;

    @Mock
    EditPetView mEditPetView;

    private Throwable mError = new Throwable("Error!");
    private EditPetViewModel mEditPetViewModel;

    @Mock
    EditPetInteractor mEditPetInteractor;

    @Before
    public void setup() {
        mEditPetViewModel = new EditPetViewModel(mEditPetView, mSchedulerProvider, mEditPetInteractor);
    }

    @org.junit.Test
    public void editPet_successShowsMessage() {

        when(mEditPetInteractor.editPet(mMockPet))
                .thenReturn(Observable.just(mMockPet));

        mEditPetViewModel.editPet(mMockPet);

        verify(mEditPetView).showProgress();
        verify(mEditPetInteractor).editPet(mMockPet);
        verify(mEditPetView).hideProgress();
        verify(mEditPetView).showSuccess();

        verifyNoMoreInteractions(mEditPetView);
        verifyNoMoreInteractions(mEditPetInteractor);
    }

    @org.junit.Test
    public void editPet_failureShowsMessage() {

        when(mEditPetInteractor.editPet(mMockPet))
                .thenReturn(Observable.error(mError));

        mEditPetViewModel.editPet(mMockPet);

        verify(mEditPetView).showProgress();
        verify(mEditPetInteractor).editPet(mMockPet);
        verify(mEditPetView).hideProgress();
        verify(mEditPetView).showError(mError.getMessage());

        verifyNoMoreInteractions(mEditPetView);
        verifyNoMoreInteractions(mEditPetInteractor);
    }
}