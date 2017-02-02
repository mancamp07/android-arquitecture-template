package com.mooveit.android.androidtemplateproject.home;

import com.mooveit.android.androidtemplateproject.common.BaseTest;
import com.mooveit.android.androidtemplateproject.common.model.entities.Pet;
import com.mooveit.android.androidtemplateproject.home.domain.DeletePetInteractor;
import com.mooveit.android.androidtemplateproject.home.domain.GetPetsInteractor;
import com.mooveit.android.androidtemplateproject.home.presenter.HomeView;
import com.mooveit.android.androidtemplateproject.home.presenter.HomeViewModel;

import org.junit.Before;
import org.mockito.Mock;

import java.util.List;

import rx.Observable;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class HomeViewModelTest extends BaseTest {

    @Mock
    List<Pet> mMockPets;

    @Mock
    Pet mMockPet;

    @Mock
    HomeView mHomeView;

    private Throwable mError = new Throwable("Error!");
    private HomeViewModel mHomeViewModel;

    @Mock
    GetPetsInteractor mGetPetsInteractor;

    @Mock
    DeletePetInteractor mDeletePetInteractor;

    @Before
    public void setup() {
        mHomeViewModel = new HomeViewModel(mHomeView, mSchedulerProvider,
                mGetPetsInteractor, mDeletePetInteractor);
    }

    @org.junit.Test
    public void getPetsForceRefresh_successCallsShowPets() {

        when(mGetPetsInteractor.getPets(true))
                .thenReturn(Observable.just(mMockPets));

        mHomeViewModel.fetchPets(true);

        verify(mHomeView).showProgress();
        verify(mGetPetsInteractor).getPets(true);
        verify(mHomeView).hideProgress();
        verify(mHomeView).showPets(mMockPets);
    }

    @org.junit.Test
    public void getPets_successCallsShowPets() {

        when(mGetPetsInteractor.getPets(false))
                .thenReturn(Observable.just(mMockPets));

        mHomeViewModel.fetchPets(false);

        verify(mHomeView).showProgress();
        verify(mGetPetsInteractor).getPets(false);
        verify(mHomeView).hideProgress();
        verify(mHomeView).showPets(mMockPets);

        verifyNoMoreInteractions(mHomeView);
        verifyNoMoreInteractions(mGetPetsInteractor);
    }

    @org.junit.Test
    public void getPets_errorHidesProgressAndShowsMessage() {

        when(mGetPetsInteractor.getPets(true))
                .thenReturn(Observable.error(mError));

        mHomeViewModel.fetchPets(true);

        verify(mHomeView).showProgress();
        verify(mGetPetsInteractor).getPets(true);
        verify(mHomeView).hideProgress();
        verify(mHomeView).showError(mError.getMessage());
        verify(mHomeView).showPets();

        verifyNoMoreInteractions(mHomeView);
        verifyNoMoreInteractions(mGetPetsInteractor);
    }

    @org.junit.Test
    public void deletePet_successCallsShowPets() {

        when(mDeletePetInteractor.deletePet(mMockPet))
                .thenReturn(Observable.just(null));

        mHomeViewModel.onDeletePet(mMockPet);

        verify(mDeletePetInteractor).deletePet(mMockPet);
        verify(mHomeView).showPets();

        verifyNoMoreInteractions(mHomeView);
        verifyNoMoreInteractions(mGetPetsInteractor);
    }

    @org.junit.Test
    public void deletePet_errorShowsMessage() {
        when(mDeletePetInteractor.deletePet(mMockPet))
                .thenReturn(Observable.error(mError));

        mHomeViewModel.onDeletePet(mMockPet);

        verify(mDeletePetInteractor).deletePet(mMockPet);
        verify(mHomeView).showError(mError.getMessage());

        verifyNoMoreInteractions(mHomeView);
        verifyNoMoreInteractions(mGetPetsInteractor);
    }
}