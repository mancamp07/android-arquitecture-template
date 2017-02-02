package com.mooveit.android.androidtemplateproject.addpet;

import com.mooveit.android.androidtemplateproject.addpet.domain.AddPetInteractor;
import com.mooveit.android.androidtemplateproject.addpet.presenter.AddPetView;
import com.mooveit.android.androidtemplateproject.addpet.presenter.AddPetViewModel;
import com.mooveit.android.androidtemplateproject.common.BaseTest;
import com.mooveit.android.androidtemplateproject.common.model.entities.Pet;

import org.junit.Before;
import org.mockito.Mock;

import rx.Observable;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class AddPetViewModelTest extends BaseTest {

    @Mock
    Pet mMockPet;

    @Mock
    AddPetView mAddPetView;

    @Mock
    AddPetInteractor mAddPetInteractor;

    private Throwable mError = new Throwable("Error!");
    private AddPetViewModel mAddPetViewModel;

    @Before
    public void setup() {
        mAddPetViewModel = new AddPetViewModel(mAddPetView, mSchedulerProvider, mAddPetInteractor);
    }

    @org.junit.Test
    public void addPet_successTriggersSuccessOnView() {

        when(mAddPetInteractor.addPet(mMockPet))
                .thenReturn(Observable.just(mMockPet));

        mAddPetViewModel.createPet(mMockPet);

        verify(mAddPetView).showProgress();
        verify(mAddPetInteractor).addPet(mMockPet);
        verify(mAddPetView).hideProgress();
        verify(mAddPetView).showPetCreated();

        verifyNoMoreInteractions(mAddPetView);
        verifyNoMoreInteractions(mAddPetInteractor);
    }

    @org.junit.Test
    public void addPet_failureShowsMessage() {

        when(mAddPetInteractor.addPet(mMockPet))
                .thenReturn(Observable.error(mError));

        mAddPetViewModel.createPet(mMockPet);

        verify(mAddPetView).showProgress();
        verify(mAddPetInteractor).addPet(mMockPet);
        verify(mAddPetView).hideProgress();
        verify(mAddPetView).showError(mError.getMessage());

        verifyNoMoreInteractions(mAddPetView);
        verifyNoMoreInteractions(mAddPetInteractor);
    }

}
