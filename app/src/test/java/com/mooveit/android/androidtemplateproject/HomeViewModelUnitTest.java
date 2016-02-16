package com.mooveit.android.androidtemplateproject;

import android.content.Context;

import com.mooveit.android.androidtemplateproject.activities.home.HomeView;
import com.mooveit.android.androidtemplateproject.activities.home.HomeViewModel;
import com.mooveit.android.androidtemplateproject.model.entities.Pet;
import com.mooveit.android.androidtemplateproject.model.entities.Tag;
import com.mooveit.android.androidtemplateproject.model.repository.PetsRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class HomeViewModelUnitTest {

    Context mContext;

    @Mock
    HomeView mHomeView;

    @Mock
    HomeViewModel mHomeViewModel;

    @Mock
    PetsRepository mPetsRepository;

    @Captor
    private ArgumentCaptor<PetsRepository.OnGetPetsCallback> mOnGetPetsCallbackCaptor;

    private static List<Pet> mMockPets = new ArrayList<>();
    private static List<Tag> mMockTags = new ArrayList<>();

    static {
        Tag tag = new Tag();
        tag.setId(1);
        tag.setName("mooveit");
        mMockTags.add(tag);

        Pet pet = new Pet();
        pet.setId(1);
        pet.setName("Lola");
        pet.setTags(mMockTags);
        pet.setStatus("available");
        mMockPets.add(pet);

        pet = new Pet();
        pet.setId(2);
        pet.setName("Rick");
        pet.setTags(mMockTags);
        pet.setStatus("available");
        mMockPets.add(pet);
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        mContext = RuntimeEnvironment.application;
        when(mHomeView.getContext()).thenReturn(mContext);

        mHomeViewModel = new HomeViewModel(mHomeView, mPetsRepository);
    }

    @Test
    public void getPets_successCallsShowPets() {
        mHomeViewModel.fetchPets();

        verify(mHomeView).showProgress();

        verify(mPetsRepository).getPets(mOnGetPetsCallbackCaptor.capture());

        mOnGetPetsCallbackCaptor.getValue().onGetPetsLoaded(mMockPets);

        verify(mHomeView).showPets(mMockPets);

        verify(mHomeView).hideProgress();

    }

}