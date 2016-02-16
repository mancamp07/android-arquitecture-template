package com.mooveit.android.androidtemplateproject.activities.home;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mooveit.android.androidtemplateproject.R;
import com.mooveit.android.androidtemplateproject.activities.petdetails.PetDetailsActivity;
import com.mooveit.android.androidtemplateproject.model.entities.Pet;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PetsListAdapter extends RecyclerView.Adapter<PetsListAdapter.PetViewHolder> {

    private List<Pet> mPets;

    public PetsListAdapter(List<Pet> pets) {
        this.mPets = pets;
    }

    public static class PetViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.pet_name) TextView mNameTV;
        @Bind(R.id.pet_status) TextView mStatusTV;

        public PetViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.getContext().startActivity(new Intent(v.getContext(), PetDetailsActivity.class));
                }
            });
        }
    }

    @Override
    public PetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext().getApplicationContext())
                .inflate(R.layout.layout_pet_list_item, parent, false);

        return new PetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PetViewHolder holder, int position) {

        final String petName = mPets.get(position).getName();
        final String petStatus = mPets.get(position).getStatus();

        holder.mNameTV.setText(petName);
        holder.mStatusTV.setText(petStatus);
    }

    @Override
    public int getItemCount() {
        return mPets.size();
    }

    public void setPets(List<Pet> pets) {
        this.mPets.clear();
        this.mPets.addAll(pets);
        notifyDataSetChanged();
    }
}
