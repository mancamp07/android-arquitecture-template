package com.mooveit.android.androidtemplateproject.addpet;

import com.mooveit.android.androidtemplateproject.addpet.domain.AddPetInteractor;
import com.mooveit.android.androidtemplateproject.addpet.domain.AddPetInteractorImpl;
import com.mooveit.android.androidtemplateproject.common.Constants;
import com.mooveit.android.androidtemplateproject.common.model.entities.Pet;
import com.mooveit.android.androidtemplateproject.common.model.repository.PetsRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class AddPetInteractorTest {

    @Mock
    PetsRepository mPetsRepository;

    @Mock
    Pet mMockPet;

    private AddPetInteractor mAddPetInteractorTest;

    @Before
    public void setup() {
        mAddPetInteractorTest = new AddPetInteractorImpl(mPetsRepository);
    }

    @Test
    public void addPet_setsStatusToAvailable() {

        mAddPetInteractorTest.addPet(mMockPet);

        InOrder inOrder = inOrder(mMockPet, mPetsRepository);
        inOrder.verify(mMockPet).setStatus(Constants.PET_STATUS_AVAILABLE);
        inOrder.verify(mPetsRepository).createPet(mMockPet);

        verifyNoMoreInteractions(mMockPet);
        verifyNoMoreInteractions(mPetsRepository);
    }

}
