package com.indrayudh.posnertask2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class TaskActivity extends AppCompatActivity {

    private TextView fixationPoint, stimulusSymbol;
    private Button leftButton, rightButton, backButton;
    private int trialCounter = 0, totalTrials;
    private long startTime, responseTime;
    private ArrayList<String> results;
    private Random random;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        // Initialize UI components
        fixationPoint = findViewById(R.id.fixationPoint);
        stimulusSymbol = findViewById(R.id.stimulusSymbol);
        leftButton = findViewById(R.id.leftButton);
        rightButton = findViewById(R.id.rightButton);
        backButton = findViewById(R.id.backButton);

        totalTrials = getIntent().getIntExtra("TOTAL_TRIALS", 10); // Get total trials from Intent
        results = new ArrayList<>();
        random = new Random();
        handler = new Handler();

        // Start the first trial
        startTrial();

        // Button listeners
        leftButton.setOnClickListener(v -> handleResponse("Left"));
        rightButton.setOnClickListener(v -> handleResponse("Right"));
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void startTrial() {
        if (trialCounter >= totalTrials) {
            // End the task and show results
            saveResultsToExternalStorage();
            Intent intent = new Intent(this, MyDataActivity.class);
            intent.putStringArrayListExtra("RESULTS", results);
            startActivity(intent);
            finish();
            return;
        }

        // Show fixation point in the center for 500ms
        fixationPoint.setVisibility(View.VISIBLE);
        stimulusSymbol.setVisibility(View.INVISIBLE);

        handler.postDelayed(() -> showStimulus(), 500);
    }

    private void showStimulus() {
        fixationPoint.setVisibility(View.INVISIBLE); // Hide fixation point
        stimulusSymbol.setVisibility(View.VISIBLE); // Show stimulus symbol

        // Randomly position the `$` symbol (Left or Right)
        boolean isLeft = random.nextBoolean(); // Randomly decide if it's left or right
        stimulusSymbol.setText("$");

        if (isLeft) {
            stimulusSymbol.setX(100); // Move to the left side
            stimulusSymbol.setTag("Left");
        } else {
            stimulusSymbol.setX(getWindowManager().getDefaultDisplay().getWidth() - 200); // Move to the right side
            stimulusSymbol.setTag("Right");
        }

        // Start the response timer
        startTime = System.currentTimeMillis();
    }

    private void handleResponse(String userResponse) {
        responseTime = System.currentTimeMillis() - startTime; // Calculate reaction time
        String originalAnswer = stimulusSymbol.getTag().toString(); // Get correct answer
        boolean isCorrect = userResponse.equals(originalAnswer);

        // Record trial data
        results.add((trialCounter + 1) + "\t" + originalAnswer + "\t" + userResponse + "\t" +
                (isCorrect ? "True" : "False") + "\t" + responseTime);

        // Show response time in a toast
        Toast.makeText(this, "Response Time: " + responseTime + " ms", Toast.LENGTH_SHORT).show();

        // Proceed to the next trial
        trialCounter++;
        startTrial();
    }

    // Method to save results to external storage
    private void saveResultsToExternalStorage() {
        // Get the path to the external storage directory for the app
        File externalStorage = getExternalFilesDir(null); // App-specific external storage
        if (externalStorage != null) {
            File file = new File(externalStorage, "stimulus_data.csv");

            try (FileWriter writer = new FileWriter(file)) {
                // Write CSV header
                writer.write("Trial Number,Original Answer,Your Answer,Correct,Response Time\n");

                // Write each trial's results
                for (String result : results) {
                    writer.write(result + "\n");
                }

                Toast.makeText(this, "Results saved to external storage", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed to save results", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
