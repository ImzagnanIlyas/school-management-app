package com.example.miolaapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.miolaapp.adapters.ProfAdapter;

public class MainActivity extends AppCompatActivity {
    private Button btnListEtud, btnListProf, btnSignout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnListEtud = findViewById(R.id.button_list_etud);
        btnListProf = findViewById(R.id.button_list_prof);
        btnSignout = findViewById(R.id.button_signout);

        btnListEtud.setOnClickListener(view -> {
            Intent intent = new Intent(this, EtudiantsListActivity.class);
            startActivity(intent);
        });

        btnListProf.setOnClickListener(view -> {
            Intent intent = new Intent(this, ProfsListActivity.class);
            startActivity(intent);
        });

        btnSignout.setOnClickListener(view -> finish());
    }
}
