package com.mooveit.android.androidtemplateproject.home;

import com.mooveit.android.androidtemplateproject.common.model.entities.Pet;
import com.mooveit.android.androidtemplateproject.common.model.repository.PetsRepository;
import com.mooveit.android.androidtemplateproject.home.domain.GetPetsInteractor;
import com.mooveit.android.androidtemplateproject.home.domain.GetPetsInteractorImpl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetPetsInteractorTest {

    @Mock
    PetsRepository mPetsRepository;

    private GetPetsInteractor mGetPetsInteractor;

    @Before
    public void setup() {
        mGetPetsInteractor = new GetPetsInteractorImpl(mPetsRepository);
    }

    @Test
    public void getPets_forwardsRequestToRepository() {
        when(mPetsRepository.getPets())
                .thenReturn(Observable.just(new ArrayList<>()));

        mGetPetsInteractor.getPets(false);

        verify(mPetsRepository).getPets();

        verifyNoMoreInteractions(mPetsRepository);
    }

    @Test
    public void getPets_refreshInvalidatesCacheAndForwardsRequestToRepository() {
        when(mPetsRepository.getPets())
                .thenReturn(Observable.just(new ArrayList<>()));

        mGetPetsInteractor.getPets(true);

        InOrder inOrder = inOrder(mPetsRepository);
        inOrder.verify(mPetsRepository).invalidateCache();
        inOrder.verify(mPetsRepository).getPets();

        verifyNoMoreInteractions(mPetsRepository);
    }

    @Test
    public void getPets_ordersPetsByUpdatedAtTimeStamp() {

        List<Pet> mMockPets = new ArrayList<Pet>() {{
            add(new Pet(1, "Pepe", "available", 12345, 1));
            add(new Pet(1, "Carlos", "available", 31231, 3));
            add(new Pet(1, "Jhon", "available", 12312, 2));
            add(new Pet(1, "Obdulio", "available", 2345, 5));
            add(new Pet(1, "Juana", "available", 1554234, 4));
        }};

        when(mPetsRepository.getPets())
                .thenReturn(Observable.just(mMockPets));

        Observable<List<Pet>> observable = mGetPetsInteractor.getPets(true);

        List<Pet> orderedPets = observable.toBlocking().first();
        boolean sorted = true;
        Pet previous = null;
        for (Pet pet : orderedPets) {
            if (previous != null && pet.getUpdatedAt() > previous.getUpdatedAt()) {
                sorted = false;
            }
            previous = pet;
            if (!sorted) {
                break;
            }
        }

        assertThat("Pet list is not sorted by updateAt descending", sorted, is(true));

        InOrder inOrder = inOrder(mPetsRepository);
        inOrder.verify(mPetsRepository).invalidateCache();
        inOrder.verify(mPetsRepository).getPets();

        verifyNoMoreInteractions(mPetsRepository);
    }

}