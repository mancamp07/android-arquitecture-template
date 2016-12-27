package com.mooveit.android.androidtemplateproject.editpet;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

import com.mooveit.android.androidtemplateproject.R;
import com.mooveit.android.androidtemplateproject.common.BaseRobolectricUnitTest;
import com.mooveit.android.androidtemplateproject.common.robolectric.shadows.ShadowSnackbar;
import com.mooveit.android.androidtemplateproject.common.model.entities.Pet;
import com.mooveit.android.androidtemplateproject.editpet.presenter.EditPetActivity;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;

import static butterknife.ButterKnife.findById;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

@Config(
        shadows = {ShadowSnackbar.class}
)
public class EditPetFragmentUnitTest extends BaseRobolectricUnitTest {

    private static final String ERROR_MESSAGE = "Error!";
    private static final String PET_NAME = "I'm a pet";

    private EditPetFragment mEditPetFragment;

    @Bind(R.id.name_text_input_layout) TextInputLayout mNameTIL;
    @Bind(R.id.name_edit_text) EditText mNameET;
    @BindString(R.string.sending) String mSendingMessage;
    @BindString(R.string.empty_name_error) String mEmptyNameErrorMessage;
    @BindString(R.string.pet_edit_success) String mPetEditSuccessMessage;

    @Mock
    Pet mMockPet;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        when(mMockPet.getName()).thenReturn(PET_NAME);

        setupFragment();
    }

    @Test
    public void startFragment_isVisible() {
        assertThat(mEditPetFragment.getView(), is(notNullValue()));
    }

    @Test
    public void emptyName_showsErrorMessage() {
        mNameET.setText("");

        FloatingActionButton editFab = findById(mEditPetFragment.getActivity(),
                R.id.submit_edit_fab);
        editFab.performClick();

        assertThat(mNameTIL.getError().toString(), equalTo(mEmptyNameErrorMessage));
    }

    @Test
    public void showProgress_showsProgressMessage() {
        mEditPetFragment.showProgress();

        Snackbar snackbar = ShadowSnackbar.getLatestSnackbar();
        String successMessage = ShadowSnackbar.getTextOfLatestSnackbar();

        assertThat(snackbar, is(notNullValue()));
        assertThat(snackbar.isShown(), is(true));
        assertThat(successMessage, equalTo(mSendingMessage));
    }

    @Test
    public void showSuccess_showsSuccessMessage() {
        mEditPetFragment.showSuccess();

        Snackbar snackbar = ShadowSnackbar.getLatestSnackbar();
        String successMessage = ShadowSnackbar.getTextOfLatestSnackbar();

        assertThat(snackbar, is(notNullValue()));
        assertThat(snackbar.isShown(), is(true));
        assertThat(successMessage, equalTo(mPetEditSuccessMessage));
    }

    @Test
    public void showError_showsErrorMessage() {
        mEditPetFragment.showError(ERROR_MESSAGE);

        Snackbar snackbar = ShadowSnackbar.getLatestSnackbar();
        String errorMessage = ShadowSnackbar.getTextOfLatestSnackbar();

        assertThat(snackbar, is(notNullValue()));
        assertThat(snackbar.isShown(), is(true));
        assertThat(errorMessage, equalTo(ERROR_MESSAGE));
    }

    private void setupFragment() {

        EditPetActivity activity = Robolectric.buildActivity(EditPetActivity.class)
                .withIntent(createEditActivityIntent())
                .create()
                .start()
                .resume()
                .get();

        mEditPetFragment = (EditPetFragment) activity.getSupportFragmentManager()
                .findFragmentByTag(EditPetFragment.TAG);

        ButterKnife.bind(this, mEditPetFragment.getView());
    }

    private Intent createEditActivityIntent() {
        Intent intent = new Intent();
        intent.putExtra(EditPetActivity.ARG_PET_EXTRAS, mMockPet);
        return intent;
    }
}
