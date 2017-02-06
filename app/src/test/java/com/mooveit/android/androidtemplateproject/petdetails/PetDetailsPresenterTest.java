package com.mooveit.android.androidtemplateproject.petdetails;


import com.mooveit.android.androidtemplateproject.common.BaseTest;
import com.mooveit.android.androidtemplateproject.common.model.entities.Pet;
import com.mooveit.android.androidtemplateproject.petdetails.domain.GetPetDetailsInteractor;
import com.mooveit.android.androidtemplateproject.petdetails.presentation.PetDetailsPresenter;
import com.mooveit.android.androidtemplateproject.petdetails.presentation.PetDetailsView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;

import rx.Observable;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class PetDetailsPresenterTest extends BaseTest {

    private static final long PET_ID = 123456;

    @Mock
    Pet mMockPet;

    @Mock
    PetDetailsView mPetDetailsView;

    @Mock
    GetPetDetailsInteractor mGetPetDetailsInteractor;

    private Throwable mError = new Throwable("Error!");
    private PetDetailsPresenter mPetDetailsPresenter;

    @Before
    public void setup() {
        mPetDetailsPresenter = new PetDetailsPresenter(mPetDetailsView,
                mSchedulerProvider, mGetPetDetailsInteractor);
    }

    @Test
    public void getPetDetails_successTriggersSuccessOnView() {

        when(mMockPet.getExternalId())
                .thenReturn(PET_ID);

        when(mGetPetDetailsInteractor.getPet(PET_ID))
                .thenReturn(Observable.just(mMockPet));

        mPetDetailsPresenter.getPet(PET_ID);

        InOrder inOrder = inOrder(mPetDetailsView, mGetPetDetailsInteractor);
        inOrder.verify(mPetDetailsView).showProgress();
        inOrder.verify(mGetPetDetailsInteractor).getPet(PET_ID);
        inOrder.verify(mPetDetailsView).hideProgress();
        inOrder.verify(mPetDetailsView).showPet(mMockPet);

        verifyNoMoreInteractions(mPetDetailsView);
        verifyNoMoreInteractions(mGetPetDetailsInteractor);
    }

    @Test
    public void getPetDetails_failureShowsMessage() {

        when(mGetPetDetailsInteractor.getPet(PET_ID))
                .thenReturn(Observable.error(mError));

        mPetDetailsPresenter.getPet(PET_ID);

        InOrder inOrder = inOrder(mPetDetailsView, mGetPetDetailsInteractor);
        inOrder.verify(mPetDetailsView).showProgress();
        inOrder.verify(mGetPetDetailsInteractor).getPet(PET_ID);
        inOrder.verify(mPetDetailsView).hideProgress();
        inOrder.verify(mPetDetailsView).showError(mError.getMessage());

        verifyNoMoreInteractions(mPetDetailsView);
        verifyNoMoreInteractions(mGetPetDetailsInteractor);
    }

}
