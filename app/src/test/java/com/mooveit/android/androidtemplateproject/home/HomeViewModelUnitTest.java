package com.mooveit.android.androidtemplateproject.home;

import com.mooveit.android.androidtemplateproject.activities.home.HomeView;
import com.mooveit.android.androidtemplateproject.activities.home.HomeViewModel;
import com.mooveit.android.androidtemplateproject.model.entities.Pet;
import com.mooveit.android.androidtemplateproject.model.repository.PetsRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

public class HomeViewModelUnitTest {

    private static final long PET_ID = 1;

    @Mock
    List<Pet> mMockPets;

    @Mock
    HomeView mHomeView;

    private final Throwable ERROR = new Throwable("Error!");

    HomeViewModel mHomeViewModel;

    @Mock
    PetsRepository mPetsRepository;

    @Captor
    private ArgumentCaptor<PetsRepository.OnGetPetsCallback> mOnGetPetsCallbackCaptor;

    @Captor
    private ArgumentCaptor<PetsRepository.OnDeletePetCallback> mOnDeletePetCallbackCaptor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        mHomeViewModel = new HomeViewModel(mHomeView, mPetsRepository);
    }

    @Test
    public void getPets_successCallsShowPets() {
        mHomeViewModel.fetchPets();

        verify(mHomeView).showProgress();

        verify(mPetsRepository).getPets(mOnGetPetsCallbackCaptor.capture());

        mOnGetPetsCallbackCaptor.getValue().onGetPetsLoaded(mMockPets);

        verify(mHomeView).hideProgress();

        verify(mHomeView).showPets(mMockPets);
    }

    @Test
    public void getPets_errorShowsMessage() {
        mHomeViewModel.fetchPets();

        verify(mHomeView).showProgress();

        verify(mPetsRepository).getPets(mOnGetPetsCallbackCaptor.capture());

        mOnGetPetsCallbackCaptor.getValue().onGetPetsFailed(ERROR);

        verify(mHomeView).hideProgress();

        verify(mHomeView).showError(ERROR.getMessage());

        verify(mHomeView).showPets();
    }

    @Test
    public void deletePet_successCallsShowPets() {
        mHomeViewModel.onDeletePet(createMockPet());

        verify(mPetsRepository).deletePet(any(Pet.class), mOnDeletePetCallbackCaptor.capture());

        mOnDeletePetCallbackCaptor.getValue().onDeletePetSuccess();

        verify(mHomeView).showPets();
    }

    @Test
    public void deletePet_errorShowsMessage() {
        mHomeViewModel.onDeletePet(createMockPet());

        verify(mPetsRepository).deletePet(any(Pet.class), mOnDeletePetCallbackCaptor.capture());

        mOnDeletePetCallbackCaptor.getValue().onDeletePetFailed(ERROR);

        verify(mHomeView).showError(ERROR.getMessage());
    }


    private Pet createMockPet() {
        Pet mockPet = new Pet();
        mockPet.setId(PET_ID);

        return mockPet;
    }
}