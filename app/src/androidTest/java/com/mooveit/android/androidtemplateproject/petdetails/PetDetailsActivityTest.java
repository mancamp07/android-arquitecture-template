package com.mooveit.android.androidtemplateproject.petdetails;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.mooveit.android.androidtemplateproject.R;
import com.mooveit.android.androidtemplateproject.common.TestApplication;
import com.mooveit.android.androidtemplateproject.common.model.entities.Pet;
import com.mooveit.android.androidtemplateproject.common.model.repository.PetsRepository;
import com.mooveit.android.androidtemplateproject.petdetails.presentation.PetDetailsActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import rx.Observable;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class PetDetailsActivityTest {

    private static final long PET_ID = 12345;

    @Rule
    public ActivityTestRule<PetDetailsActivity> mActivityTestRule = new ActivityTestRule<>(
            PetDetailsActivity.class,
            true,
            false
    );

    private Pet mPet = buildMockPet();

    @Inject
    PetsRepository mPetsRepository;

    @Before
    public void setup() {
        ((TestApplication) InstrumentationRegistry.getTargetContext().getApplicationContext())
                .getMockApplicationComponent()
                .inject(this);


        when(mPetsRepository.getPet(PET_ID))
                .thenReturn(Observable.just(mPet));
    }

    private Pet buildMockPet() {
        Pet pet = new Pet();
        pet.setExternalId(PET_ID);
        pet.setName("Pepe");
        pet.setStatus("available");

        return pet;
    }

    @Test
    public void test_petDetailsDisplaysCorrectlyInViews() {

        Intent intent = new Intent();
        intent.putExtra(PetDetailsActivity.ARG_PET_ID_EXTRA, PET_ID);

        mActivityTestRule.launchActivity(intent);

        onView(withId(R.id.pet_name))
                .check(matches(withText("Pepe")));
        onView(withId(R.id.pet_status))
                .check(matches(withText("available")));
    }

}
