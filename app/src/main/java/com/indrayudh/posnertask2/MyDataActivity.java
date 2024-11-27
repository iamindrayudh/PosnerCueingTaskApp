package com.indrayudh.posnertask2;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class MyDataActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<HashMap<String, String>> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_data);

        listView = findViewById(R.id.listView);
        dataList = new ArrayList<>();

        // Read the CSV file and load data into ListView
        readDataFromCSV();

        // Adapter to display data in the ListView
        SimpleAdapter adapter = new SimpleAdapter(
                this,
                dataList,
                android.R.layout.simple_list_item_2, // Default list item layout
                new String[]{"trial", "details"},
                new int[]{android.R.id.text1, android.R.id.text2}
        );

        listView.setAdapter(adapter);
    }

    // Method to read CSV data from internal storage
    private void readDataFromCSV() {
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader reader = null;

        try {
            // Open the file from internal storage
            fis = openFileInput("stimulus_data.csv");
            isr = new InputStreamReader(fis);
            reader = new BufferedReader(isr);

            String line;
            boolean isFirstLine = true; // Skip header line

            // Read each line from the CSV file
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Skip the header row
                }

                // Split the line by commas
                String[] data = line.split(",");
                if (data.length == 5) {
                    // Create a HashMap for each row of data
                    HashMap<String, String> map = new HashMap<>();
                    map.put("trial", "Trial " + data[0]);
                    map.put("details", "Correct: " + data[1] + ", Your Answer: " + data[2] + ", Correct?: " + data[3] + ", Response Time: " + data[4] + " ms");

                    // Add the row to the list
                    dataList.add(map);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error reading data file", Toast.LENGTH_SHORT).show();
        } finally {
            try {
                if (reader != null) reader.close();
                if (isr != null) isr.close();
                if (fis != null) fis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}