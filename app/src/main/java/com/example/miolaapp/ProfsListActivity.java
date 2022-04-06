package com.example.miolaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.miolaapp.adapters.ProfAdapter;
import com.example.miolaapp.entities.Professeur;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ProfsListActivity extends AppCompatActivity {
    private static final String TAG = "ProfsListActivity";

    private ProfAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profs_list);


        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvProfs);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProfAdapter(this, getProfs());
        recyclerView.setAdapter(adapter);
    }

    private ArrayList<Professeur> getProfs(){
        Log.d(TAG, "GET PROFS");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        ArrayList<Professeur> list = new ArrayList<>();
        db.collection("professeurs")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d(TAG, document.getId() + " => " + document.getData());
                            list.add(document.toObject(Professeur.class));
                            Log.d(TAG, document.toObject(Professeur.class).toString());
                        }
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                });
        return list;
    }
}