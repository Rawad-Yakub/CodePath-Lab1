package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class empty extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.such_empty);

        findViewById(R.id.add_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoAddCard = new Intent(empty.this, MainActivity.class);
                 //                  startActivity(gotoAddCard);
                startActivityForResult(gotoAddCard, 100);
            }
        });
    }
}