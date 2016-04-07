package com.mooveit.android.androidtemplateproject.activities.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.mooveit.android.androidtemplateproject.R;
import com.mooveit.android.androidtemplateproject.activities.addpet.AddPetActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        if (savedInstanceState == null) {
            attachHomeFragment();
        }
    }

    private void attachHomeFragment() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, HomeFragment.newInstance())
                .commit();
    }

    @OnClick(R.id.add_pet_fab)
    public void onAddButtonClicked() {
        startActivity(new Intent(HomeActivity.this, AddPetActivity.class));
    }
}
