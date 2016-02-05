package com.mooveit.android.androidtemplateproject;

import android.content.Context;

import com.mooveit.android.androidtemplateproject.activities.home.HomeView;
import com.mooveit.android.androidtemplateproject.activities.home.HomeViewModel;
import com.mooveit.android.androidtemplateproject.model.Pet;
import com.mooveit.android.androidtemplateproject.model.Tag;
import com.mooveit.android.androidtemplateproject.network.PetStoreService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

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
    PetStoreService mPetStoreService;

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

        when(mPetStoreService.getPets())
                .thenReturn(Observable.just(mMockPets)
                        .subscribeOn(Schedulers.immediate())
                        .doOnNext(new Action1<List<Pet>>() {
                            @Override
                            public void call(List<Pet> pets) {
                                mHomeView.showPets(pets);
                            }
                        })
                        .doOnCompleted(new Action0() {
                            @Override
                            public void call() {
                                mHomeView.hideProgress();
                            }
                        }));
        mHomeViewModel = new HomeViewModel(mHomeView, mPetStoreService);
    }

    @Test
    public void getPets_successCallsShowPets() {
        mHomeViewModel.fetchPets();

        verify(mHomeView).showProgress();

        verify(mPetStoreService).getPets();

        verify(mHomeView).showPets(mMockPets);

        verify(mHomeView).hideProgress();

    }

}