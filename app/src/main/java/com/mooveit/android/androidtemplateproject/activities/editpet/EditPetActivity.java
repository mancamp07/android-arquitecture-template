package com.mooveit.android.androidtemplateproject.activities.editpet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.mooveit.android.androidtemplateproject.R;
import com.mooveit.android.androidtemplateproject.model.entities.Pet;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditPetActivity extends AppCompatActivity {

    public static final String ARG_PET_EXTRAS = ":Extras:Pet:Parcelable";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private Pet mPet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pet);
        ButterKnife.bind(this);

        setupToolbar();
        retrieveExtras();

        if (savedInstanceState == null) {
            attachEditPetFragment();
        }
    }

    private void attachEditPetFragment() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, EditPetFragment.newInstance(mPet), EditPetFragment.TAG)
                .commit();
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void retrieveExtras() {
        Bundle args = getIntent().getExtras();
        if (args != null) {
            mPet = args.getParcelable(ARG_PET_EXTRAS);
        }
    }
}
