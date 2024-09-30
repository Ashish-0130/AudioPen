package com.example.audiopen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Spinner backgroundSpinner, fontSpinner;
    Button startButton, addBackgroundButton;
    private static final int REQUEST_CODE = 1;
    ArrayList<String> backgroundList = new ArrayList<>();
    ArrayList<String> fontList = new ArrayList<>();
    ArrayAdapter<String> backgroundAdapter, fontAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        backgroundSpinner = findViewById(R.id.background_spinner1);
        fontSpinner = findViewById(R.id.fonts_spinner1);
        startButton = findViewById(R.id.start_button);
        addBackgroundButton = findViewById(R.id.add_background_button);

        initializeBackgrounds();
        initializeFonts();

        startButton.setEnabled(false);

        addBackgroundButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddBackgroundActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        });

        backgroundSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                checkIfSelectionsComplete();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        fontSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                checkIfSelectionsComplete();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        startButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, NotesActivity.class);
            intent.putExtra("background", backgroundSpinner.getSelectedItem().toString());
            intent.putExtra("font", fontSpinner.getSelectedItem().toString());
            startActivity(intent);
        });
    }

    // Initialize background options for the spinner
    private void initializeBackgrounds() {
        backgroundList.add("Select Background");
        backgroundList.add("Default Background 1");
        backgroundList.add("Default Background 2");

        backgroundAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, backgroundList);
        backgroundAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        backgroundSpinner.setAdapter(backgroundAdapter);
    }

    // Initialize font options for the spinner
    private void initializeFonts() {
        fontList.add("Select Font");
        fontList.add("Arial");
        fontList.add("Sans Serif");
        fontList.add("Monospace");

        fontAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, fontList);
        fontAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fontSpinner.setAdapter(fontAdapter);
    }

    private void checkIfSelectionsComplete() {
        boolean validBackground = backgroundSpinner.getSelectedItemPosition() > 0;
        boolean validFont = fontSpinner.getSelectedItemPosition() > 0;

        startButton.setEnabled(validBackground && validFont);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            // Handle new background image added to spinner
            String newBackground = data.getStringExtra("newBackground");
            if (newBackground != null && !newBackground.isEmpty()) {
                backgroundList.add(newBackground);
                backgroundAdapter.notifyDataSetChanged();
            }
        }
    }
}
