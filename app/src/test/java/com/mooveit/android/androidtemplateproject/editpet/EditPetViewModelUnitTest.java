package com.mooveit.android.androidtemplateproject.editpet;

import com.mooveit.android.androidtemplateproject.model.entities.Pet;
import com.mooveit.android.androidtemplateproject.model.repository.PetsRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

public class EditPetViewModelUnitTest {

    @Mock
    Pet mMockPet;

    @Mock
    Pet mMockEditPet;

    Throwable mError = new Throwable("Error!");

    @Mock
    EditPetView mEditPetView;

    EditPetViewModel mEditPetViewModel;

    @Mock
    PetsRepository mPetsRepository;

    @Captor
    private ArgumentCaptor<PetsRepository.OnEditPetCallback> mOnEditPetCallbackCaptor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        mEditPetViewModel = new EditPetViewModel(mEditPetView, mPetsRepository);
    }

    @Test
    public void editPet_successShowsMessage() {
        mEditPetViewModel.editPet(mMockPet);

        verify(mEditPetView).showProgress();

        verify(mPetsRepository).updatePet(any(Pet.class), mOnEditPetCallbackCaptor.capture());

        mOnEditPetCallbackCaptor.getValue().onEditPetSuccess(mMockEditPet);

        verify(mEditPetView).hideProgress();

        verify(mEditPetView).showSuccess();
    }

    @Test
    public void editPet_failureShowsMessage() {
        mEditPetViewModel.editPet(mMockPet);

        verify(mEditPetView).showProgress();

        verify(mPetsRepository).updatePet(any(Pet.class), mOnEditPetCallbackCaptor.capture());

        mOnEditPetCallbackCaptor.getValue().onEditPetFailed(mError);

        verify(mEditPetView).hideProgress();

        verify(mEditPetView).showError(mError.getMessage());
    }
}