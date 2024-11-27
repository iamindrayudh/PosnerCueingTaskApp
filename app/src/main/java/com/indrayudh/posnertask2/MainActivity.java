package com.indrayudh.posnertask2;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnPosner, btnMyData, btnQuit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPosner = findViewById(R.id.btn_posner);
        btnMyData = findViewById(R.id.btn_my_data);
        btnQuit = findViewById(R.id.btn_quit);

        btnPosner.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, InstructionsActivity.class);
            startActivity(intent);
        });

        btnMyData.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MyDataActivity.class);
            startActivity(intent);
        });

        btnQuit.setOnClickListener(v -> finish());
    }
}