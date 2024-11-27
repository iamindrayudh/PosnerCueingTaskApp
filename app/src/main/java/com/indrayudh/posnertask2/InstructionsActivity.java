package com.indrayudh.posnertask2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class InstructionsActivity extends AppCompatActivity {

    public static int trialCount = 10; // Default trial count

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);

        EditText etTrialCount = findViewById(R.id.etTrialCount);
        Button btnStart = findViewById(R.id.btnStart);
        Button btnBack = findViewById(R.id.btnBack);

        btnStart.setOnClickListener(v -> {
            String trialCountText = etTrialCount.getText().toString();
            if (!trialCountText.isEmpty()) {
                trialCount = Integer.parseInt(trialCountText);
            }
            startActivity(new Intent(InstructionsActivity.this, TaskActivity.class));
        });

        btnBack.setOnClickListener(v -> finish());
    }
}