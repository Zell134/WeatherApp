package com.zell.weatherapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.zell.weatherapp.R;

public class SettingsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button saveButton = findViewById(R.id.save);
        saveButton.setOnClickListener((view) -> saveCity());

        Button cancelButton = findViewById(R.id.cancel);
        cancelButton.setOnClickListener((view) -> cancel());

        EditText cityView = findViewById(R.id.cityTextView);
        String city = getIntent().getStringExtra(MainActivity.CITY);
        if (city != null) {
            cityView.setText(city);
        }
    }

    private void saveCity() {
        EditText city = findViewById(R.id.cityTextView);
        Intent intent = new Intent();
        intent.putExtra(MainActivity.CITY, city.getText().toString().trim());
        setResult(RESULT_OK, intent);
        finish();
    }

    private void cancel() {
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        finish();
    }
}