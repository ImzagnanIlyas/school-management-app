package com.example.miolaapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.miolaapp.AddProfDialog;
import com.example.miolaapp.R;
import com.example.miolaapp.entities.Professeur;
import com.example.miolaapp.utils.GlideApp;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Locale;

public class ProfAdapter extends RecyclerView.Adapter<ProfAdapter.ViewHolder> {

    private static final String DIRECTORY = "prof-pictures/";

//    private Context context;
    private FragmentActivity fragmentActivity;
    private ArrayList<Professeur> localDataSet;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
//        final ImageView picture;
        final ShapeableImageView picture;
        final TextView name;
        final TextView email;
        final TextView buttonViewOption;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            picture = view.findViewById(R.id.imageView_picture);
            name = view.findViewById(R.id.textView_name);
            email = view.findViewById(R.id.textView_email);
            buttonViewOption = view.findViewById(R.id.textViewOptions);
//            itemLayoutView.setOnClickListener(this);
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     */
//    public ProfAdapter(Context context, ArrayList<Professeur> dataSet) {
//        localDataSet = dataSet;
//        this.context = context;
//    }
    public ProfAdapter(FragmentActivity fragmentActivity, ArrayList<Professeur> dataSet) {
        localDataSet = dataSet;
        this.fragmentActivity = fragmentActivity;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.prof_item_layout, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.name.setText(localDataSet.get(position).getNom().toUpperCase(Locale.ROOT)+" "+localDataSet.get(position).getPrenom());
        viewHolder.email.setText("Département "+localDataSet.get(position).getDepart());

        // Reference to an image file in Cloud Storage
        StorageReference storageReference =
                FirebaseStorage.getInstance().getReference(localDataSet.get(position).getImage());

        // Download directly from StorageReference using Glide
        Glide.with(fragmentActivity)
                .load(storageReference)
                .into(viewHolder.picture);

        // Set Popup Menu
        viewHolder.buttonViewOption.setOnClickListener(view -> {
            //creating a popup menu
            PopupMenu popup = new PopupMenu(fragmentActivity, viewHolder.buttonViewOption);
            //inflating menu from xml resource
            popup.inflate(R.menu.popup_menu_items);
            //adding click listener
            popup.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.edit:
                        //handle menu1 click
                        editProf(localDataSet.get(position).getEmail());
                        break;
                    case R.id.delete:
                        //handle menu2 click
                        break;
                }
                return false;
            });
            //displaying the popup
            popup.show();
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    private void editProf(String id){
        AddProfDialog addProfDialog = new AddProfDialog();
        addProfDialog.edit(id);
        addProfDialog.show(fragmentActivity.getSupportFragmentManager(), "DIALOG");
    }

}
