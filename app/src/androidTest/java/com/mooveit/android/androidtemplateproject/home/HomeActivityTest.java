package com.mooveit.android.androidtemplateproject.home;


import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import com.mooveit.android.androidtemplateproject.R;
import com.mooveit.android.androidtemplateproject.addpet.presentation.AddPetActivity;
import com.mooveit.android.androidtemplateproject.common.BaseAndroidTest;
import com.mooveit.android.androidtemplateproject.common.TestApplication;
import com.mooveit.android.androidtemplateproject.common.model.entities.Pet;
import com.mooveit.android.androidtemplateproject.common.model.repository.PetsRepository;
import com.mooveit.android.androidtemplateproject.editpet.presentation.EditPetActivity;
import com.mooveit.android.androidtemplateproject.home.presenter.HomeActivity;
import com.mooveit.android.androidtemplateproject.petdetails.presentation.PetDetailsActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.mooveit.android.androidtemplateproject.common.util.CustomViewActions.actionOnItemChildViewAtPosition;
import static com.mooveit.android.androidtemplateproject.common.util.CustomViewMatchers.isContentLoadingProgressBarDisplayed;
import static org.hamcrest.Matchers.allOf;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class HomeActivityTest extends BaseAndroidTest {

    private List<Pet> mPets = new ArrayList<>();

    @Rule
    public IntentsTestRule<HomeActivity> mActivityRule = new IntentsTestRule<>(HomeActivity.class, true, false);

    @Inject
    PetsRepository mPetsRepository;

    @Before
    public void setup() {
        super.setup();

        ((TestApplication) InstrumentationRegistry.getTargetContext().getApplicationContext())
                .getMockApplicationComponent()
                .inject(this);

        mPets = new ArrayList<Pet>() {{
            add(new Pet(1, "Pepe", "available", 12345, 3));
            add(new Pet(2, "Jorge", "available", 67890, 2));
            add(new Pet(3, "Jack", "available", 934857, 1));
        }};

        Pet pet = new Pet();
        when(mPetsRepository.getPet(anyLong()))
                .thenReturn(Observable.just(pet));

        when(mPetsRepository.getPets())
                .thenReturn(Observable.just(mPets));
    }

    @Test
    public void test_noPetsShowsEmptyState() {

        when(mPetsRepository.getPets())
                .thenReturn(Observable.just(new ArrayList<>()));

        mActivityRule.launchActivity(null);

        onView(withId(R.id.empty_pets_list))
                .check(matches(isDisplayed()));
    }

    @Test
    public void test_fetchPetsShowsLoadingProgressBar() {

        List<Pet> pets = new ArrayList<>();
        when(mPetsRepository.getPets())
                .thenReturn(
                        Observable.just(pets)
                                .delay(2, TimeUnit.SECONDS)
                );


        mActivityRule.launchActivity(null);

        onView(withId(R.id.progressbar))
                .check(matches(isContentLoadingProgressBarDisplayed()));
    }

    @Test
    public void test_addPetButtonOpensAddPetActivity() {

        mActivityRule.launchActivity(null);

        onView(withId(R.id.add_pet_fab))
                .perform(click());

        intended(
                hasComponent(AddPetActivity.class.getName())
        );
    }

    @Test
    public void test_clickPetOpensPetDetailsActivity() {

        mActivityRule.launchActivity(null);

        onView(withId(R.id.pets_list))
                .perform(actionOnItemAtPosition(0, click()));

        intended(allOf(
                hasComponent(PetDetailsActivity.class.getName()),
                hasExtraWithKey(PetDetailsActivity.ARG_PET_ID_EXTRA),
                hasExtra(PetDetailsActivity.ARG_PET_ID_EXTRA, mPets.get(0).getExternalId())
        ));
    }

    @Test
    public void test_clickEditPetOpensEditPetActivity() {

        mActivityRule.launchActivity(null);

        onView(withId(R.id.pets_list))
                .perform(actionOnItemChildViewAtPosition(0, R.id.edit_button, click()));

        intended(allOf(
                hasComponent(EditPetActivity.class.getName()),
                hasExtraWithKey(EditPetActivity.ARG_PET_EXTRAS),
                hasExtra(EditPetActivity.ARG_PET_EXTRAS, mPets.get(0))
        ));
    }
}
