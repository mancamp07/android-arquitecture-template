package com.mooveit.android.androidtemplateproject.addpet;

import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.mooveit.android.androidtemplateproject.R;
import com.mooveit.android.androidtemplateproject.addpet.presentation.AddPetActivity;
import com.mooveit.android.androidtemplateproject.common.TestApplication;
import com.mooveit.android.androidtemplateproject.common.model.entities.Pet;
import com.mooveit.android.androidtemplateproject.common.model.repository.PetsRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class AddPetActivityTest {

    private static final long PET_ID = 12345;
    @Rule
    public ActivityTestRule<AddPetActivity> mActivityTestRule = new ActivityTestRule<>(
            AddPetActivity.class,
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


        when(mPetsRepository.createPet(any(Pet.class)))
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
    public void test_emptyPetNameShowsError() {

        mActivityTestRule.launchActivity(null);

        onView(withId(R.id.add_pet_fab))
                .perform(click());

        onView(withText(R.string.empty_name_error))
                .check(matches(isDisplayed()));
    }


    @Test
    public void test_addPetShowsProgressSnackbar() {

        when(mPetsRepository.createPet(any(Pet.class)))
                .thenReturn(Observable.just(mPet)
                        .delay(1, TimeUnit.SECONDS));

        mActivityTestRule.launchActivity(null);

        onView(withId(R.id.pet_name))
                .perform(typeText("Lola"), closeSoftKeyboard());

        onView(withId(R.id.add_pet_fab))
                .perform(click());

        onView(withText(R.string.sending))
                .check(matches(isDisplayed()));
    }

    @Test
    public void test_addPetShowsSuccessMessage() {

        mActivityTestRule.launchActivity(null);

        onView(withId(R.id.pet_name))
                .perform(typeText("Lola"), closeSoftKeyboard());

        onView(withId(R.id.add_pet_fab))
                .perform(click());

        onView(withText(R.string.created_pet_success))
                .check(matches(isDisplayed()));
    }

    @Test
    public void test_addPetShowsErrorMessage() {

        when(mPetsRepository.createPet(any(Pet.class)))
                .thenReturn(Observable.error(new Throwable("Error")));

        mActivityTestRule.launchActivity(null);

        onView(withId(R.id.pet_name))
                .perform(typeText("Lola"), closeSoftKeyboard());

        onView(withId(R.id.add_pet_fab))
                .perform(click());

        onView(withText("Error"))
                .check(matches(isDisplayed()));
    }

}
