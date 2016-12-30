package com.mooveit.android.androidtemplateproject.home.presenter;

import android.graphics.Canvas;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mooveit.android.androidtemplateproject.R;
import com.mooveit.android.androidtemplateproject.common.model.entities.Pet;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PetsListAdapter extends RecyclerView.Adapter<PetsListAdapter.PetViewHolder> {

    private RecyclerView mRecyclerView;
    private final OnItemClickListener mOnItemClickListener;
    private final OnItemDeletedListener mOnItemDeletedListener;
    private final List<Pet> mPets;

    public PetsListAdapter(List<Pet> pets,
                           OnItemClickListener onItemClickListener,
                           OnItemDeletedListener onItemDeletedListener) {
        this.mPets = pets;
        this.mOnItemClickListener = onItemClickListener;
        this.mOnItemDeletedListener = onItemDeletedListener;
    }

    public interface OnItemClickListener {
        void onItemClick(Pet pet);

        void onEditItemClick(Pet pet);
    }

    public interface OnItemDeletedListener {
        void onPetItemDeleted(Pet pet);
    }

    public class PetViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        @BindView(R.id.card_view)
        CardView mCardView;
        @BindView(R.id.pet_name)
        TextView mNameTV;
        @BindView(R.id.pet_status)
        TextView mStatusTV;

        @OnClick(R.id.edit_button)
        public void onEditClicked() {
            Pet pet = mPets.get(getAdapterPosition());
            mOnItemClickListener.onEditItemClick(pet);
        }

        public PetViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Pet pet = mPets.get(getAdapterPosition());
            mOnItemClickListener.onItemClick(pet);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new AdapterItemTouchHelperCallback(0, ItemTouchHelper.RIGHT)
        );
        itemTouchHelper.attachToRecyclerView(recyclerView);

        mRecyclerView = recyclerView;
    }

    @Override
    public PetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
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

    public void onItemRemoved(final RecyclerView.ViewHolder viewHolder) {
        final int itemPosition = viewHolder.getAdapterPosition();
        final Pet removedPet = mPets.get(itemPosition);
        Snackbar.make(
                mRecyclerView,
                R.string.delete_pet_message,
                Snackbar.LENGTH_LONG
        ).setAction(R.string.undo_action, v -> {
            onItemRemovedUndoAction(itemPosition, removedPet);
        }).addCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar snackbar, int event) {
                if (event != Snackbar.Callback.DISMISS_EVENT_ACTION) {
                    mOnItemDeletedListener.onPetItemDeleted(removedPet);
                }
            }
        }).show();

        mPets.remove(itemPosition);
        notifyItemRemoved(itemPosition);
    }

    private void onItemRemovedUndoAction(int previousItemPosition, Pet removedPet) {
        mPets.add(previousItemPosition, removedPet);
        notifyItemInserted(previousItemPosition);
        mRecyclerView.scrollToPosition(previousItemPosition);
    }

    private class AdapterItemTouchHelperCallback extends ItemTouchHelper.SimpleCallback {

        public AdapterItemTouchHelperCallback(int dragDirs, int swipeDirs) {
            super(dragDirs, swipeDirs);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            onItemRemoved(viewHolder);
        }

        @Override
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            getDefaultUIUtil().clearView(((PetViewHolder) viewHolder).mCardView);
        }

        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
            if (viewHolder != null) {
                getDefaultUIUtil().onSelected(((PetViewHolder) viewHolder).mCardView);
            }
        }

        @Override
        public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            getDefaultUIUtil().onDraw(c, recyclerView, ((PetViewHolder) viewHolder).mCardView, dX, dY, actionState, isCurrentlyActive);
        }

        @Override
        public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            getDefaultUIUtil().onDrawOver(c, recyclerView, ((PetViewHolder) viewHolder).mCardView, dX, dY, actionState, isCurrentlyActive);
        }
    }

}
