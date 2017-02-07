package com.mooveit.android.androidtemplateproject.home;

import com.mooveit.android.androidtemplateproject.common.model.entities.Pet;
import com.mooveit.android.androidtemplateproject.common.model.repository.PetsRepository;
import com.mooveit.android.androidtemplateproject.home.domain.DeletePetInteractor;
import com.mooveit.android.androidtemplateproject.home.domain.DeletePetInteractorImpl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class DeletePetInteractorTest {

    @Mock
    Pet mMockPet;

    @Mock
    PetsRepository mPetsRepository;

    private DeletePetInteractor mDeletePetInteractor;

    @Before
    public void setup() {
        mDeletePetInteractor = new DeletePetInteractorImpl(mPetsRepository);
    }

    @Test
    public void deletePet_forwardsRequestToRepository() {

        mDeletePetInteractor.deletePet(mMockPet);

        verify(mPetsRepository).deletePet(mMockPet);

        verifyNoMoreInteractions(mPetsRepository);
    }
}
