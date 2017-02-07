package com.mooveit.android.androidtemplateproject.editpet;

import com.mooveit.android.androidtemplateproject.common.model.entities.Pet;
import com.mooveit.android.androidtemplateproject.common.model.repository.PetsRepository;
import com.mooveit.android.androidtemplateproject.editpet.domain.EditPetInteractor;
import com.mooveit.android.androidtemplateproject.editpet.domain.EditPetInteractorImpl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class EditPetInteractorTest {

    @Mock
    Pet mMockPet;

    @Mock
    PetsRepository mPetsRepository;

    private EditPetInteractor mEditPetInteractor;

    @Before
    public void setup() {
        mEditPetInteractor = new EditPetInteractorImpl(mPetsRepository);
    }

    @Test
    public void editPet_forwardsUpdateToRepository() {

        mEditPetInteractor.editPet(mMockPet);

        verify(mPetsRepository).updatePet(mMockPet);

        verifyNoMoreInteractions(mPetsRepository);
    }
}
