package com.mooveit.android.androidtemplateproject.home;

import com.mooveit.android.androidtemplateproject.common.model.entities.Pet;
import com.mooveit.android.androidtemplateproject.common.model.repository.PetsRepository;
import com.mooveit.android.androidtemplateproject.home.domain.DeletePetInteractor;
import com.mooveit.android.androidtemplateproject.home.domain.GetPetsInteractor;
import com.mooveit.android.androidtemplateproject.home.presenter.HomeView;
import com.mooveit.android.androidtemplateproject.home.presenter.HomeViewModel;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import rx.Single;
import rx.observers.TestSubscriber;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class HomeViewModelUnitTest {

    private static final long PET_ID = 1;

    @Mock
    List<Pet> mMockPets;

    @Mock
    HomeView mHomeView;

    private final Throwable ERROR = new Throwable("Error!");

    HomeViewModel mHomeViewModel;

    @Mock
    GetPetsInteractor mGetPetsInteractor;

    @Mock
    DeletePetInteractor mDeletePetInteractor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        mHomeViewModel = new HomeViewModel(mHomeView, mGetPetsInteractor, mDeletePetInteractor);
    }

    @Test
    public void getPets_successCallsShowPets() {

        when(mGetPetsInteractor.getPets())
                .thenReturn(Single.just(mMockPets));

        mHomeViewModel.fetchPets();

        verify(mHomeView).showProgress();

        verify(mHomeView).hideProgress();

        verify(mHomeView).showPets(mMockPets);
    }

//    @Test
//    public void getPets_errorShowsMessage() {
//        mHomeViewModel.fetchPets();
//
//        verify(mHomeView).showProgress();
//
//        verify(mPetsRepository).getPets(mOnGetPetsCallbackCaptor.capture());
//
//        mOnGetPetsCallbackCaptor.getValue().onGetPetsFailed(ERROR);
//
//        verify(mHomeView).hideProgress();
//
//        verify(mHomeView).showError(ERROR.getMessage());
//
//        verify(mHomeView).showPets();
//    }
//
//    @Test
//    public void deletePet_successCallsShowPets() {
//        mHomeViewModel.onDeletePet(createMockPet());
//
//        verify(mPetsRepository).deletePet(any(Pet.class), mOnDeletePetCallbackCaptor.capture());
//
//        mOnDeletePetCallbackCaptor.getValue().onDeletePetSuccess();
//
//        verify(mHomeView).showPets();
//    }
//
//    @Test
//    public void deletePet_errorShowsMessage() {
//        mHomeViewModel.onDeletePet(createMockPet());
//
//        verify(mPetsRepository).deletePet(any(Pet.class), mOnDeletePetCallbackCaptor.capture());
//
//        mOnDeletePetCallbackCaptor.getValue().onDeletePetFailed(ERROR);
//
//        verify(mHomeView).showError(ERROR.getMessage());
//    }


    private Pet createMockPet() {
        Pet mockPet = new Pet();
        mockPet.setId(PET_ID);

        return mockPet;
    }
}