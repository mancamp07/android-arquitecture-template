package com.mooveit.android.androidtemplateproject.home;

import com.mooveit.android.androidtemplateproject.editpet.EditPetActivity;
import com.mooveit.android.androidtemplateproject.common.BaseRobolectricUnitTest;
import com.mooveit.android.androidtemplateproject.common.robolectric.shadows.ShadowSnackbar;
import com.mooveit.android.androidtemplateproject.model.entities.Pet;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.annotation.Config;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.robolectric.shadows.support.v4.SupportFragmentTestUtil.startVisibleFragment;

@Config(
        shadows = {ShadowSnackbar.class}
)
public class HomeFragmentUnitTest extends BaseRobolectricUnitTest {

    private HomeFragment mHomeFragment;

    @Mock
    Pet mMockPet;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        mHomeFragment = HomeFragment.newInstance();

        startVisibleFragment(mHomeFragment);
    }

    @Test
    public void startFragment_isVisible() {
        assertThat(mHomeFragment.getView(), is(notNullValue()));
    }

    @Test
    public void showEditPet_opensEditPetActivity() {
        mHomeFragment.showEditPet(mMockPet);

        assertThat(getNextStartedActivityClassName(),
                equalTo(EditPetActivity.class.getName()));
    }
}
