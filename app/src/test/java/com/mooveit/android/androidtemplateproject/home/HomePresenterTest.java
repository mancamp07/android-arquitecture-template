package com.mooveit.android.androidtemplateproject.home;

import com.mooveit.android.androidtemplateproject.common.BaseTest;
import com.mooveit.android.androidtemplateproject.common.model.entities.Pet;
import com.mooveit.android.androidtemplateproject.home.domain.DeletePetInteractor;
import com.mooveit.android.androidtemplateproject.home.domain.GetPetsInteractor;
import com.mooveit.android.androidtemplateproject.home.presenter.HomePresenter;
import com.mooveit.android.androidtemplateproject.home.presenter.HomeView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;

import java.util.List;

import rx.Observable;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class HomePresenterTest extends BaseTest {

    @Mock
    List<Pet> mMockPets;

    @Mock
    Pet mMockPet;

    @Mock
    HomeView mHomeView;

    private Throwable mError = new Throwable("Error!");
    private HomePresenter mHomePresenter;

    @Mock
    GetPetsInteractor mGetPetsInteractor;

    @Mock
    DeletePetInteractor mDeletePetInteractor;

    @Before
    public void setup() {
        mHomePresenter = new HomePresenter(mHomeView, mSchedulerProvider,
                mGetPetsInteractor, mDeletePetInteractor);
    }

    @Test
    public void getPetsForceRefresh_successCallsShowPets() {

        when(mGetPetsInteractor.getPets(true))
                .thenReturn(Observable.just(mMockPets));

        mHomePresenter.fetchPets(true);

        InOrder inOrder = inOrder(mHomeView, mGetPetsInteractor);
        inOrder.verify(mHomeView).showProgress();
        inOrder.verify(mGetPetsInteractor).getPets(true);
        inOrder.verify(mHomeView).hideProgress();
        inOrder.verify(mHomeView).showPets(mMockPets);
    }

    @Test
    public void getPets_successCallsShowPets() {

        when(mGetPetsInteractor.getPets(false))
                .thenReturn(Observable.just(mMockPets));

        mHomePresenter.fetchPets(false);

        InOrder inOrder = inOrder(mHomeView, mGetPetsInteractor);
        inOrder.verify(mHomeView).showProgress();
        inOrder.verify(mGetPetsInteractor).getPets(false);
        inOrder.verify(mHomeView).hideProgress();
        inOrder.verify(mHomeView).showPets(mMockPets);

        verifyNoMoreInteractions(mHomeView);
        verifyNoMoreInteractions(mGetPetsInteractor);
    }

    @Test
    public void getPets_errorHidesProgressAndShowsMessage() {

        when(mGetPetsInteractor.getPets(true))
                .thenReturn(Observable.error(mError));

        mHomePresenter.fetchPets(true);

        InOrder inOrder = inOrder(mHomeView, mGetPetsInteractor);
        inOrder.verify(mHomeView).showProgress();
        inOrder.verify(mGetPetsInteractor).getPets(true);
        inOrder.verify(mHomeView).hideProgress();
        inOrder.verify(mHomeView).showError(mError.getMessage());
        inOrder.verify(mHomeView).showPets();

        verifyNoMoreInteractions(mHomeView);
        verifyNoMoreInteractions(mGetPetsInteractor);
    }

    @Test
    public void deletePet_successCallsShowPets() {

        when(mDeletePetInteractor.deletePet(mMockPet))
                .thenReturn(Observable.just(null));

        mHomePresenter.onDeletePet(mMockPet);

        InOrder inOrder = inOrder(mHomeView, mDeletePetInteractor);
        inOrder.verify(mDeletePetInteractor).deletePet(mMockPet);
        inOrder.verify(mHomeView).showPets();

        verifyNoMoreInteractions(mHomeView);
        verifyNoMoreInteractions(mGetPetsInteractor);
    }

    @Test
    public void deletePet_errorShowsMessage() {
        when(mDeletePetInteractor.deletePet(mMockPet))
                .thenReturn(Observable.error(mError));

        mHomePresenter.onDeletePet(mMockPet);

        InOrder inOrder = inOrder(mHomeView, mDeletePetInteractor);
        inOrder.verify(mDeletePetInteractor).deletePet(mMockPet);
        inOrder.verify(mHomeView).showError(mError.getMessage());

        verifyNoMoreInteractions(mHomeView);
        verifyNoMoreInteractions(mGetPetsInteractor);
    }
}