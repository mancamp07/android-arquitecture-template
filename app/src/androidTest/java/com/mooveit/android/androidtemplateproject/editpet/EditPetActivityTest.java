package com.mooveit.android.androidtemplateproject.editpet;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.mooveit.android.androidtemplateproject.R;
import com.mooveit.android.androidtemplateproject.common.TestApplication;
import com.mooveit.android.androidtemplateproject.common.model.entities.Pet;
import com.mooveit.android.androidtemplateproject.common.model.repository.PetsRepository;
import com.mooveit.android.androidtemplateproject.editpet.presentation.EditPetActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class EditPetActivityTest {

    private static final String PET_NAME = "Pepe";

    @Rule
    public ActivityTestRule<EditPetActivity> mActivityRule = new ActivityTestRule<>(EditPetActivity.class, true, false);

    @Inject
    PetsRepository mPetsRepository;

    @Before
    public void setup() {
        ((TestApplication) InstrumentationRegistry.getTargetContext().getApplicationContext())
                .getMockApplicationComponent()
                .inject(this);
    }

    @Test
    public void test_receivedPetShowsRightDataInViews() {

        Intent intent = getIntent();

        mActivityRule.launchActivity(intent);

        onView(withId(R.id.name_edit_text))
                .check(matches(withText(PET_NAME)));
    }

    @Test
    public void test_onEditPetShowsSnackbar() {

        Pet pet = new Pet();

        when(mPetsRepository.updatePet(pet))
                .thenReturn(Observable.just(pet)
                        .delay(1, TimeUnit.SECONDS));

        Intent intent = getIntent();

        mActivityRule.launchActivity(intent);

        onView(withId(R.id.submit_edit_fab))
                .perform(click());

        onView(withText(R.string.sending))
                .check(matches(isDisplayed()));
    }

    @Test
    public void test_onEditPetShowsSuccessMessage() {

        Pet pet = new Pet();

        when(mPetsRepository.updatePet(pet))
                .thenReturn(Observable.just(pet));

        Intent intent = getIntent();

        mActivityRule.launchActivity(intent);

        onView(withId(R.id.submit_edit_fab))
                .perform(click());

        onView(withText(R.string.pet_edit_success))
                .check(matches(isDisplayed()));
    }

    @Test
    public void test_onEditPetShowsErrorMessage() {

        Pet pet = new Pet();

        when(mPetsRepository.updatePet(pet))
                .thenReturn(Observable.error(new Throwable("Error")));

        Intent intent = getIntent();

        mActivityRule.launchActivity(intent);

        onView(withId(R.id.submit_edit_fab))
                .perform(click());

        onView(withText("Error"))
                .check(matches(isDisplayed()));
    }

    @NonNull
    private Intent getIntent() {
        Pet pet = new Pet();
        pet.setName(PET_NAME);

        Intent intent = new Intent();
        intent.putExtra(EditPetActivity.ARG_PET_EXTRAS, pet);
        return intent;
    }

}
