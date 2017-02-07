package com.mooveit.android.androidtemplateproject.petdetails;

import com.mooveit.android.androidtemplateproject.common.model.repository.PetsRepository;
import com.mooveit.android.androidtemplateproject.petdetails.domain.GetPetDetailsInteractor;
import com.mooveit.android.androidtemplateproject.petdetails.domain.GetPetDetailsInteractorImpl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class GetPetDetailsInteractorTest {

    private static final long MOCK_PET_ID = 12345;

    @Mock
    PetsRepository mPetsRepository;

    private GetPetDetailsInteractor mGetPetDetailsInteractor;

    @Before
    public void setup() {
        mGetPetDetailsInteractor = new GetPetDetailsInteractorImpl(mPetsRepository);
    }

    @Test
    public void test_forwardsRequestToRepository() {
        mGetPetDetailsInteractor.getPet(MOCK_PET_ID);

        verify(mPetsRepository).getPet(MOCK_PET_ID);

        verifyNoMoreInteractions(mPetsRepository);
    }

}
