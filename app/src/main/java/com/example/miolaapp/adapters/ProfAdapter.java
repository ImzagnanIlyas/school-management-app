package com.example.miolaapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.miolaapp.R;
import com.example.miolaapp.entities.Professeur;
import com.example.miolaapp.utils.GlideApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ProfAdapter extends RecyclerView.Adapter<ProfAdapter.ViewHolder> {

    private static final String DIRECTORY = "prof-pictures/";

    private Context context;
    private ArrayList<Professeur> localDataSet;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView picture;
        final TextView name;
        final TextView email;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            picture = view.findViewById(R.id.imageView_picture);
            name = view.findViewById(R.id.textView_name);
            email = view.findViewById(R.id.textView_email);
//            itemLayoutView.setOnClickListener(this);
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     */
    public ProfAdapter(Context context, ArrayList<Professeur> dataSet) {
        localDataSet = dataSet;
        this.context = context;
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
        viewHolder.name.setText(localDataSet.get(position).getNom()+" "+localDataSet.get(position).getPrenom());
        viewHolder.email.setText(localDataSet.get(position).getEmail());

//        // Reference to an image file in Cloud Storage
        StorageReference storageReference =
                FirebaseStorage.getInstance().getReference(DIRECTORY+localDataSet.get(position).getId());
//        // Download directly from StorageReference using Glide
//        // (See MyAppGlideModule for Loader registration)
        Glide.with(context)
                .load(storageReference)
                .into(viewHolder.picture);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
