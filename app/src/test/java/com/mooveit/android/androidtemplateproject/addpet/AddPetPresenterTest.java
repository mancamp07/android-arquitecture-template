package com.mooveit.android.androidtemplateproject.addpet;

import com.mooveit.android.androidtemplateproject.addpet.domain.AddPetInteractor;
import com.mooveit.android.androidtemplateproject.addpet.presentation.AddPetPresenter;
import com.mooveit.android.androidtemplateproject.addpet.presentation.AddPetView;
import com.mooveit.android.androidtemplateproject.common.BaseTest;
import com.mooveit.android.androidtemplateproject.common.model.entities.Pet;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;

import rx.Observable;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class AddPetPresenterTest extends BaseTest {

    @Mock
    Pet mMockPet;

    @Mock
    AddPetView mAddPetView;

    @Mock
    AddPetInteractor mAddPetInteractor;

    private Throwable mError = new Throwable("Error!");
    private AddPetPresenter mAddPetPresenter;

    @Before
    public void setup() {
        mAddPetPresenter = new AddPetPresenter(mAddPetView, mSchedulerProvider, mAddPetInteractor);
    }

    @Test
    public void addPet_successTriggersSuccessOnView() {

        when(mAddPetInteractor.addPet(mMockPet))
                .thenReturn(Observable.just(mMockPet));

        mAddPetPresenter.createPet(mMockPet);

        InOrder inOrder = inOrder(mAddPetView, mAddPetInteractor);
        inOrder.verify(mAddPetView).showProgress();
        inOrder.verify(mAddPetInteractor).addPet(mMockPet);
        inOrder.verify(mAddPetView).hideProgress();
        inOrder.verify(mAddPetView).showPetCreated();

        verifyNoMoreInteractions(mAddPetView);
        verifyNoMoreInteractions(mAddPetInteractor);
    }

    @Test
    public void addPet_failureShowsMessage() {

        when(mAddPetInteractor.addPet(mMockPet))
                .thenReturn(Observable.error(mError));

        mAddPetPresenter.createPet(mMockPet);

        InOrder inOrder = inOrder(mAddPetView, mAddPetInteractor);
        inOrder.verify(mAddPetView).showProgress();
        inOrder.verify(mAddPetInteractor).addPet(mMockPet);
        inOrder.verify(mAddPetView).hideProgress();
        inOrder.verify(mAddPetView).showError(mError.getMessage());

        verifyNoMoreInteractions(mAddPetView);
        verifyNoMoreInteractions(mAddPetInteractor);
    }

}
