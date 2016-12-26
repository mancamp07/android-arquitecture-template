package com.mooveit.android.androidtemplateproject.activities.addpet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.mooveit.android.androidtemplateproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddPetActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet);

        ButterKnife.bind(this);

        setupToolbar();

        attachAddPetFragment();
    }

    private void attachAddPetFragment() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, AddPetFragment.newInstance())
                .commit();
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

}
