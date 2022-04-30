package com.example.miolaapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.example.miolaapp.entities.Professeur;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.Locale;

public class ShowProfDialog extends DialogFragment {
    private ShapeableImageView image;
    private TextView nom, depart;
    private Button appel, sms, wtsp, email;

    private ProfsListActivity activity;
    private Professeur prof;
    private Drawable loadedImage;

    public ShowProfDialog(Professeur prof, Drawable loadedImage) {
        super();
        this.prof = prof;
        this.loadedImage = loadedImage;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        activity = ((ProfsListActivity)ShowProfDialog.this.getActivity());

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View view = inflater.inflate(R.layout.dialog_show_prof, null);

        // bind elements
        image = view.findViewById(R.id.imageView_picture);
        nom = view.findViewById(R.id.textView_name);
        depart = view.findViewById(R.id.textView_depart);
        appel = view.findViewById(R.id.button_appel);
        sms = view.findViewById(R.id.button_sms);
        wtsp = view.findViewById(R.id.button_wtsp);
        email = view.findViewById(R.id.button_email);

        fillData();

        builder.setView(view);
        return builder.create();
    }

    public void fillData(){
        Glide.with(activity)
                .load(loadedImage)
                .into(image);
        nom.setText(prof.getNom().toUpperCase(Locale.ROOT)+" "+prof.getPrenom());
        depart.setText("Département "+prof.getDepart());
        appel.setOnClickListener(view -> {
            Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + prof.getTele()));
            startActivity(callIntent);
        });
        sms.setOnClickListener(view -> {
            Intent smsIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + prof.getTele()));
            startActivity(smsIntent);
        });
        wtsp.setOnClickListener(view -> {
            PackageManager pm=activity.getPackageManager();
            try {
                Intent wtspIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + prof.getTele()));
                pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA); // get WhatsApp info, if its not exist exception
                wtspIntent.setPackage("com.whatsapp");
                startActivity(wtspIntent);
            } catch (PackageManager.NameNotFoundException e) {
                Toast.makeText(activity, "WhatsApp not Installed", Toast.LENGTH_SHORT).show();
            }
        });
        email.setOnClickListener(view -> {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"));
//            emailIntent.setType(HTTP.PLAIN_TEXT_TYPE);
            emailIntent.putExtra(Intent.EXTRA_EMAIL,  new String[] {prof.getEmail()}); // recipients
            startActivity(emailIntent);
        });
    }
}
