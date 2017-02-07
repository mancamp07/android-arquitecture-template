package com.mooveit.android.androidtemplateproject.common.repository;

import com.mooveit.android.androidtemplateproject.common.model.datasource.PetsDataSource;
import com.mooveit.android.androidtemplateproject.common.model.entities.Pet;
import com.mooveit.android.androidtemplateproject.common.model.repository.PetsRepository;
import com.mooveit.android.androidtemplateproject.common.model.repository.impl.PetsRepositoryImpl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.observers.TestSubscriber;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PetsRepositoryTest {

    private static final long MOCK_PET_ID = 12345;

    @Mock
    PetsDataSource mLocalPetsDataSource;

    @Mock
    PetsDataSource mRemotePetsDataSource;

    @Mock
    Pet mMockPet;

    private static final List<Pet> PETS = new ArrayList<Pet>() {{
        add(new Pet(1, "Pepe", "available", 65756, 1));
        add(new Pet(1, "John", "available", 23432, 2));
        add(new Pet(1, "Juana", "available", 345, 3));
    }};

    private PetsRepository mPetsRepository;
    private TestSubscriber<List<Pet>> mPetsSubscriber;

    @Before
    public void setup() {
        mPetsRepository = new PetsRepositoryImpl(mLocalPetsDataSource, mRemotePetsDataSource);
        mPetsSubscriber = new TestSubscriber<>();
    }

    @Test
    public void isCacheDirty_returnsTrueWhenInvalidated() {
        mPetsRepository.invalidateCache();

        assertThat("Cache is not dirty", mPetsRepository.isCacheDirty(), is(true));
    }

    @Test
    public void getPets_getsFromRemoteAndUpdatesLocalCacheWhenItsDirty() {
        when(mRemotePetsDataSource.getPets())
                .thenReturn(Observable.just(PETS));

        mPetsRepository.invalidateCache();

        mPetsRepository.getPets().subscribe(mPetsSubscriber);

        InOrder inOrder = inOrder(mRemotePetsDataSource, mLocalPetsDataSource);
        inOrder.verify(mRemotePetsDataSource).getPets();
        inOrder.verify(mLocalPetsDataSource).deleteAll();
        inOrder.verify(mLocalPetsDataSource, times(PETS.size())).createPet(any(Pet.class));

        verifyNoMoreInteractions(mRemotePetsDataSource);
        verifyNoMoreInteractions(mLocalPetsDataSource);

        assertThat("Cache is dirty", mPetsRepository.isCacheDirty(), is(false));
    }

    @Test
    public void getPets_getsFromLocalCacheWhenNotDirty() {
        when(mLocalPetsDataSource.getPets())
                .thenReturn(Observable.just(PETS));
        when(mRemotePetsDataSource.getPets())
                .thenReturn(Observable.just(PETS));

        mPetsRepository.getPets().subscribe(mPetsSubscriber);

        verify(mLocalPetsDataSource).getPets();

        verifyNoMoreInteractions(mLocalPetsDataSource);

        assertThat("Cache is dirty", mPetsRepository.isCacheDirty(), is(false));
    }

    @Test
    public void createPet_savesToLocalCacheAndThenRemoteUpdatingCacheWithResults() {
        when(mLocalPetsDataSource.createPet(mMockPet))
                .thenReturn(Observable.just(mMockPet));
        when(mRemotePetsDataSource.createPet(mMockPet))
                .thenReturn(Observable.just(mMockPet));


        TestSubscriber<Pet> subscriber = new TestSubscriber<>();
        mPetsRepository.createPet(mMockPet).subscribe(subscriber);

        InOrder inOrder = inOrder(mLocalPetsDataSource, mRemotePetsDataSource);
        inOrder.verify(mLocalPetsDataSource).createPet(mMockPet);
        inOrder.verify(mRemotePetsDataSource).createPet(mMockPet);
        inOrder.verify(mLocalPetsDataSource).updatePet(mMockPet);

        verifyNoMoreInteractions(mLocalPetsDataSource);
        verifyNoMoreInteractions(mRemotePetsDataSource);

        assertThat("Cache is dirty", mPetsRepository.isCacheDirty(), is(false));
    }

    @Test
    public void updatePet_updatesLocalCacheAndThenRemote() {
        when(mLocalPetsDataSource.updatePet(mMockPet))
                .thenReturn(Observable.just(mMockPet));
        when(mRemotePetsDataSource.updatePet(mMockPet))
                .thenReturn(Observable.just(mMockPet));


        TestSubscriber<Pet> subscriber = new TestSubscriber<>();
        mPetsRepository.updatePet(mMockPet).subscribe(subscriber);

        InOrder inOrder = inOrder(mLocalPetsDataSource, mRemotePetsDataSource);
        inOrder.verify(mLocalPetsDataSource).updatePet(mMockPet);
        inOrder.verify(mRemotePetsDataSource).updatePet(mMockPet);

        verifyNoMoreInteractions(mLocalPetsDataSource);
        verifyNoMoreInteractions(mRemotePetsDataSource);

        assertThat("Cache is dirty", mPetsRepository.isCacheDirty(), is(false));
    }

    @Test
    public void deletePet_deletesFromLocalCacheAndThenRemote() {
        when(mLocalPetsDataSource.deletePet(mMockPet))
                .thenReturn(Observable.just(null));
        when(mRemotePetsDataSource.deletePet(mMockPet))
                .thenReturn(Observable.just(null));


        TestSubscriber<Void> subscriber = new TestSubscriber<>();
        mPetsRepository.deletePet(mMockPet).subscribe(subscriber);

        InOrder inOrder = inOrder(mLocalPetsDataSource, mRemotePetsDataSource);
        inOrder.verify(mLocalPetsDataSource).deletePet(mMockPet);
        inOrder.verify(mRemotePetsDataSource).deletePet(mMockPet);

        verifyNoMoreInteractions(mLocalPetsDataSource);
        verifyNoMoreInteractions(mRemotePetsDataSource);

        assertThat("Cache is dirty", mPetsRepository.isCacheDirty(), is(false));
    }

    @Test
    public void deleteAllPets_deletesFromLocalCache() {
        when(mLocalPetsDataSource.deleteAll())
                .thenReturn(Observable.just(null));

        TestSubscriber<Void> subscriber = new TestSubscriber<>();
        mPetsRepository.deleteAll().subscribe(subscriber);

        verify(mLocalPetsDataSource).deleteAll();
        verifyNoMoreInteractions(mLocalPetsDataSource);

        assertThat("Cache is dirty", mPetsRepository.isCacheDirty(), is(false));
    }

    @Test
    public void getPet_getsFromLocalCache() {
        when(mLocalPetsDataSource.getPet(MOCK_PET_ID))
                .thenReturn(Observable.just(null));

        TestSubscriber<Pet> subscriber = new TestSubscriber<>();
        mPetsRepository.getPet(MOCK_PET_ID).subscribe(subscriber);

        verify(mLocalPetsDataSource).getPet(MOCK_PET_ID);
        verifyNoMoreInteractions(mLocalPetsDataSource);

        assertThat("Cache is dirty", mPetsRepository.isCacheDirty(), is(false));
    }
}