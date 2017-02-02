package com.mooveit.android.androidtemplateproject.editpet;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import com.mooveit.android.androidtemplateproject.R;
import com.mooveit.android.androidtemplateproject.common.BaseRobolectricTest;
import com.mooveit.android.androidtemplateproject.common.model.entities.Pet;
import com.mooveit.android.androidtemplateproject.common.robolectric.shadows.ShadowSnackbar;
import com.mooveit.android.androidtemplateproject.editpet.presentation.EditPetActivity;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@Config(
        shadows = {ShadowSnackbar.class}
)
@Ignore
public class EditPetActivityTest extends BaseRobolectricTest {

    private final Throwable ERROR = new Throwable("Error!");
    private final String PET_NAME = "I'm a pet";

    @BindView(R.id.submit_edit_fab) FloatingActionButton mSubmitEditFab;
    @BindString(R.string.pet_edit_success) String mPetEditSuccessMessage;

    @Mock
    Pet mMockPet;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        when(mMockPet.getName()).thenReturn(PET_NAME);
    }

    @Test
    public void clickEditButton_showsSuccessMessage() {

        setupActivity();

        mSubmitEditFab.performClick();

        Snackbar snackbar = ShadowSnackbar.getLatestSnackbar();
        String successMessage = ShadowSnackbar.getTextOfLatestSnackbar();

        assertThat(snackbar, is(notNullValue()));
        assertThat(snackbar.isShown(), is(true));
        assertThat(successMessage, equalTo(mPetEditSuccessMessage));
    }

    @Test
    public void clickEditButton_showsErrorMessage() {

        setupActivity();

        mSubmitEditFab.performClick();

        Snackbar snackbar = ShadowSnackbar.getLatestSnackbar();
        String successMessage = ShadowSnackbar.getTextOfLatestSnackbar();

        assertThat(snackbar, is(notNullValue()));
        assertThat(snackbar.isShown(), is(true));
        assertThat(successMessage, equalTo(ERROR.getMessage()));
    }

    private void setupActivity() {

        AppCompatActivity activity = Robolectric.buildActivity(EditPetActivity.class)
                .withIntent(createEditActivityIntent())
                .create()
                .start()
                .resume()
                .get();

        ButterKnife.bind(this, activity);

    }

    private Intent createEditActivityIntent() {
        Intent intent = new Intent();
        intent.putExtra(EditPetActivity.ARG_PET_EXTRAS, mMockPet);
        return intent;
    }
}
