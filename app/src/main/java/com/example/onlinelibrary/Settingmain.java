package com.example.onlinelibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings.SettingNotFoundException;
import android.provider.Settings.System;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;


public class Settingmain extends AppCompatActivity {
    SeekBar seekBar;
    Button btnLogout;
    TextView tvBrightness;
    FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_settings);
        seekBar = findViewById(R.id.seekBar);
        btnLogout = findViewById(R.id.btnLogout);
        tvBrightness = findViewById(R.id.tvBrightness);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intoSignin = new Intent(Settingmain.this, LoginActivity.class);
                startActivity(intoSignin);
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                                 // When the position of the slider changes, the method is triggered, where the parameter progress is directly called, that is, the progress value represented by the current slider
                tvBrightness.setText("Value" + Integer.toString(progress));
                Settingmain.this.setScreenBrightness((float) seekBar.getProgress() / 100);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }

    private void setScreenBrightness(float v) {
        WindowManager.LayoutParams layoutParams = super.getWindow().getAttributes();
        // Set the brightness of the screen
        layoutParams.screenBrightness = v;
        // Reset the properties of the window
        super.getWindow().setAttributes(layoutParams);
    }
}