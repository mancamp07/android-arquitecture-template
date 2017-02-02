package com.mooveit.android.androidtemplateproject.home;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.mooveit.android.androidtemplateproject.R;
import com.mooveit.android.androidtemplateproject.addpet.presenter.AddPetActivity;
import com.mooveit.android.androidtemplateproject.common.BaseRobolectricTest;
import com.mooveit.android.androidtemplateproject.common.model.entities.Pet;
import com.mooveit.android.androidtemplateproject.editpet.presenter.EditPetActivity;
import com.mooveit.android.androidtemplateproject.home.presenter.HomeActivity;
import com.mooveit.android.androidtemplateproject.home.presenter.PetsListAdapter;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.robolectric.Robolectric;

import butterknife.BindView;
import butterknife.ButterKnife;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

@Ignore
public class HomeActivityTest extends BaseRobolectricTest {

    @BindView(R.id.add_pet_fab)
    FloatingActionButton mAddPetFab;

    @BindView(R.id.pets_list)
    RecyclerView mPetsRV;

    private HomeActivity mActivity;

    @Before
    public void setup() {
        setupActivity();
    }

    @Test
    public void addPet_opensAddPetActivity() {

        mAddPetFab.performClick();

        assertThat("Add button didn't open AddPetActivity",
                getNextStartedActivityClassName(),
                equalTo(AddPetActivity.class.getName()));
    }

    @Test
    public void editPet_opensEditPetActivity() {
        // workaround robolectric recyclerview measurement issue
        mPetsRV.measure(0, 0);
        mPetsRV.layout(0, 0, 100, 1000);

        PetsListAdapter adapter = (PetsListAdapter) mPetsRV.getAdapter();

        Button editButton = (Button) mPetsRV.findViewHolderForAdapterPosition(0)
                .itemView.findViewById(R.id.edit_button);
        editButton.performClick();

        Intent intent = getNextStartedActivityIntent();

        assertThat(intent.getComponent().getClassName(),
                equalTo(EditPetActivity.class.getName()));

        Pet intentExtraPet = intent.getExtras().getParcelable(EditPetActivity.ARG_PET_EXTRAS);
        assertThat("Intent extra is null", intentExtraPet, is(not(nullValue())));
        assertThat("Pet in intent extra is different from the selected item",
                intentExtraPet.getExternalId(),
                equalTo(adapter.getPets().get(0).getExternalId()));
    }

    private void setupActivity() {
        mActivity = Robolectric.setupActivity(HomeActivity.class);
        ButterKnife.bind(this, mActivity);
    }
}
