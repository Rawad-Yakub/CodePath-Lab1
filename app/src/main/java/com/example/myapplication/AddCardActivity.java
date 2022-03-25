package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        View cancelButton = findViewById(R.id.cancelbutton);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        findViewById(R.id.savebutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent data = new Intent();
                String inputQuestion = ((EditText) findViewById(R.id.addQuestion)).getText().toString();
                String inputAnswer = ((EditText) findViewById(R.id.enteranswer)).getText().toString();
                data.putExtra("Question key", inputQuestion);
                data.putExtra("Answer key", inputAnswer);
                String wrongAnswer1 = ((EditText) findViewById(R.id.wrong_answer1)).getText().toString();
                String wrongAnswer2 = ((EditText) findViewById(R.id.wrong_answer2)).getText().toString();
                data.putExtra("Wrong Answer1", wrongAnswer1);
                data.putExtra("Wrong Answer2", wrongAnswer2);
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }
}