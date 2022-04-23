package com.example.miolaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miolaapp.entities.Professeur;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class AddProfDialog extends DialogFragment {
    private static final String TAG = "AddProfDialog";

    private EditText nom, prenom, email, tele, depart;
    private SwitchMaterial cord;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        FirebaseApp.initializeApp(this.getContext());
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View view = inflater.inflate(R.layout.dialog_add_prof, null);

        // bind elements
        nom = view.findViewById(R.id.nom);
        prenom = view.findViewById(R.id.prenom);
        email = view.findViewById(R.id.email);
        tele = view.findViewById(R.id.tele);
        depart = view.findViewById(R.id.depart);
        cord = view.findViewById(R.id.cord);

        builder.setView(view)
                // Add action buttons
                .setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
//                        Toast.makeText(AddProfDialog.this.getContext(), "GOOD", Toast.LENGTH_SHORT).show();
                        saveProf();
                        ((ProfsListActivity)AddProfDialog.this.getActivity()).refresh();
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AddProfDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    private void saveProf(){
        // Showing progressDialog while saving
        ProgressDialog progressDialog
                = new ProgressDialog(this.getContext());
        progressDialog.setMessage("Saving ...");
        progressDialog.show();

        // Data
        String nom = this.nom.getText().toString();
        String prenom = this.prenom.getText().toString();
        String email = this.email.getText().toString();
        String tele = this.tele.getText().toString();
        String depart = this.depart.getText().toString();
        boolean cord = this.cord.isChecked();

        Professeur prof = new Professeur(nom, prenom, email, tele, depart, cord, "prof-pictures/avatr.png");

        db.collection("professeurs").document(email).set(prof)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        mAuth.createUserWithEmailAndPassword(email, tele);
//                        Toast.makeText(AddProfDialog.this.getContext(), "GOOD", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "SAVING GOOD");
                    } else {
//                        Toast.makeText(AddProfDialog.this.getContext(), "BAD", Toast.LENGTH_SHORT).show();
                        Log.w(TAG, "SAVING ERROR", task.getException());
                    }
                    progressDialog.dismiss();
                });
    }
}