package com.mooveit.android.androidtemplateproject.petdetails;


import com.mooveit.android.androidtemplateproject.common.BaseTest;
import com.mooveit.android.androidtemplateproject.common.model.entities.Pet;
import com.mooveit.android.androidtemplateproject.petdetails.domain.GetPetDetailsInteractor;
import com.mooveit.android.androidtemplateproject.petdetails.presentation.PetDetailsView;
import com.mooveit.android.androidtemplateproject.petdetails.presentation.PetDetailsViewModel;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import rx.Observable;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class PetDetailsViewModelTest extends BaseTest {

    private static final long PET_ID = 123456;

    @Mock
    Pet mMockPet;

    @Mock
    PetDetailsView mPetDetailsView;

    @Mock
    GetPetDetailsInteractor mGetPetDetailsInteractor;

    private Throwable mError = new Throwable("Error!");
    private PetDetailsViewModel mPetDetailsViewModel;

    @Before
    public void setup() {

        mPetDetailsViewModel = new PetDetailsViewModel(mPetDetailsView,
                mSchedulerProvider, mGetPetDetailsInteractor);
    }

    @Test
    public void getPetDetails_successTriggersSuccessOnView() {

        when(mMockPet.getExternalId())
                .thenReturn(PET_ID);

        when(mGetPetDetailsInteractor.getPet(PET_ID))
                .thenReturn(Observable.just(mMockPet));

        mPetDetailsViewModel.getPet(PET_ID);

        verify(mPetDetailsView).showProgress();
        verify(mGetPetDetailsInteractor).getPet(PET_ID);
        verify(mPetDetailsView).hideProgress();
        verify(mPetDetailsView).showPet(mMockPet);

        verifyNoMoreInteractions(mPetDetailsView);
        verifyNoMoreInteractions(mGetPetDetailsInteractor);
    }

    @Test
    public void getPetDetails_failureShowsMessage() {

        when(mGetPetDetailsInteractor.getPet(PET_ID))
                .thenReturn(Observable.error(mError));

        mPetDetailsViewModel.getPet(PET_ID);

        verify(mPetDetailsView).showProgress();
        verify(mGetPetDetailsInteractor).getPet(PET_ID);
        verify(mPetDetailsView).hideProgress();
        verify(mPetDetailsView).showError(mError.getMessage());

        verifyNoMoreInteractions(mPetDetailsView);
        verifyNoMoreInteractions(mGetPetDetailsInteractor);
    }

}
