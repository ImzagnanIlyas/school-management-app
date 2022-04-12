package com.example.miolaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
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

    private FirebaseFirestore db;
    private static ArrayList<Professeur> list;

    private ProfAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profs_list);

        Log.v(TAG, "HI ITS LIST");

        list = new ArrayList<>();

        // set up the RecyclerView
        recyclerView = findViewById(R.id.rvProfs);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adapter = new ProfAdapter(this, list);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getProfs();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void getProfs(){
        Log.v(TAG, "GET PROFS");
        db = FirebaseFirestore.getInstance();
        list.clear();
        db.collection("professeurs")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
//                            Log.v(TAG, document.getId() + " => " + document.getData());
//                            list.add(document.toObject(Professeur.class));
                            list.add(new Professeur(document.getString("nom"), document.getString("prenom"), document.getString("email"), document.getString("image")));
//                            Log.v(TAG, document.toObject(Professeur.class).toString());
//                            Log.v(TAG, new Professeur(document.get("id").toString(), document.get("nom").toString(), document.get("prenom").toString(), document.get("email").toString()).toString());
                        }
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                    // Notifier l'adapter
                    adapter.notifyDataSetChanged();
                });
    }
}