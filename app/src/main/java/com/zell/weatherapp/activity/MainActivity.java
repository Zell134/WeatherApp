package com.zell.weatherapp.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.zell.weatherapp.R;
import com.zell.weatherapp.presenter.MainActivityPresenter;
import com.zell.weatherapp.utils.Properties;

public class MainActivity extends AppCompatActivity implements MainActivityPresenter.View, NavigationView.OnNavigationItemSelectedListener {

    private final int REQUEST_CODE = 1;
    public static final String CITY = "City";
    private MainActivityPresenter presenter;

    private TextView cityView;
    private TextView tempView;
    private ImageView iconView;
    private TextView windSpeedView;
    private TextView pressureView;
    private TextView humidityView;
    private ProgressBar progressBar;
    private String city;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityView = findViewById(R.id.city);
        tempView = findViewById(R.id.temp);
        iconView = findViewById(R.id.icon);
        windSpeedView = findViewById(R.id.windSpeed);
        pressureView = findViewById(R.id.pressure);
        humidityView = findViewById(R.id.humidity);
        progressBar = findViewById(R.id.progressBar);

        Button settingsButton = findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener((view) -> openSettings());

        Button exitButton = findViewById(R.id.exit);
        exitButton.setOnClickListener((view) -> finish());

        city = Properties.getCity(this);
        if (city == null) {
            city = "Волгоград";
        }
        presenter = new MainActivityPresenter(this, city);

        setToolbar();
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.nav_open_drawer, R.string.nav_close_drawer);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                openSettings();
                break;
            case R.id.exit:
                finish();
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK && data != null) {
            String newCity = data.getStringExtra(CITY);
            if (!city.isEmpty() && !city.equals(newCity)) {
                city = newCity;
                clearViews();
                Properties.saveCity(this, city);
                presenter.updateWeather(city);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void openSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        intent.putExtra(CITY, city);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void throwError(String value) {
        clearViews();
        humidityView.setText("Измените название города в настройках");
        Toast.makeText(this, value, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateCity(String value) {
        cityView.setText(value);
    }

    @Override
    public void updateTemperature(String value) {
        tempView.setText(value);
    }

    @Override
    public void updateWeatherIcon(Bitmap value) {
        if (value != null) {
            iconView.setVisibility(View.VISIBLE);
            iconView.setImageDrawable(new BitmapDrawable(value));
        } else {
            iconView.setImageDrawable(null);
        }
    }

    @Override
    public void updateWindSpeed(String value) {
        windSpeedView.setText(value);
    }

    @Override
    public void updatePressure(String value) {
        pressureView.setText(value);
    }

    @Override
    public void updateHumidity(String value) {
        humidityView.setText(value);
    }

    private void clearViews() {
        cityView.setText("");
        tempView.setText("");
        iconView.setImageDrawable(null);
        windSpeedView.setText("");
        pressureView.setText("");
        humidityView.setText("");
        progressBar.setVisibility(View.VISIBLE);
    }
}