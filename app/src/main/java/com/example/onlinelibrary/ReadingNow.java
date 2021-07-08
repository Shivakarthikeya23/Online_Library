package com.example.onlinelibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ReadingNow extends AppCompatActivity {
    Button btnLib, btnMain;
    TextView lastBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_reading_now);

        btnLib = findViewById(R.id.btnLib);
        btnMain = findViewById(R.id.btnMain);
        lastBook = findViewById(R.id.lastBook);

        Intent intent = getIntent();
        lastBook.setText(intent.getStringExtra("name"));



        btnLib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReadingNow.this, Library.class));
            }
        });

        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReadingNow.this, MainActivity.class));
            }
        });



    }


}