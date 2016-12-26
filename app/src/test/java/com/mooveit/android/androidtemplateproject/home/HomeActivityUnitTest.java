package com.mooveit.android.androidtemplateproject.home;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;

import com.mooveit.android.androidtemplateproject.R;
import com.mooveit.android.androidtemplateproject.addpet.AddPetActivity;
import com.mooveit.android.androidtemplateproject.common.BaseRobolectricUnitTest;

import org.junit.Test;
import org.robolectric.Robolectric;

import butterknife.Bind;
import butterknife.ButterKnife;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class HomeActivityUnitTest extends BaseRobolectricUnitTest {

    @Bind(R.id.add_pet_fab) FloatingActionButton mAddPetFab;

    @Test
    public void addPet_opensAddPetActivity() {

        setupActivity();

        mAddPetFab.performClick();

        assertThat(getNextStartedActivityClassName(),
                equalTo(AddPetActivity.class.getName()));
    }

    private void setupActivity() {
        AppCompatActivity activity = Robolectric.setupActivity(HomeActivity.class);
        ButterKnife.bind(this, activity);
    }
}
