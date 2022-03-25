package com.example.myapplication;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView flashcardQuestion;
    TextView flashcardAnswer;
    TextView flashcardAnswer2;
    TextView flashcardAnswer3;
    FlashcardDatabase flashcardDatabase;
    List<Flashcard> allFlashcards;
    int currentDisplayedIndex = 0;
    Intent gotoAddCard;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        flashcardQuestion = findViewById(R.id.flashcard_question);
        flashcardAnswer = findViewById(R.id.flashcard_answer);
        flashcardAnswer2 = findViewById(R.id.flashcard_answer2);
        flashcardAnswer3 = findViewById(R.id.flashcard_answer3);
        View next_button = findViewById(R.id.next_button);

        findViewById(R.id.flashcard_answer).setVisibility(View.INVISIBLE);
        findViewById(R.id.flashcard_answer2).setVisibility(View.INVISIBLE);
        findViewById(R.id.flashcard_answer3).setVisibility(View.INVISIBLE);

        View addbutton = findViewById(R.id.addcard);

        flashcardQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.flashcard_answer).setVisibility(View.VISIBLE);
                findViewById(R.id.flashcard_answer2).setVisibility(View.VISIBLE);
                findViewById(R.id.flashcard_answer3).setVisibility(View.VISIBLE);


            }
        });

        flashcardAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flashcardAnswer.setBackgroundColor(getResources().getColor(R.color.green));
            }
        });
        flashcardAnswer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flashcardAnswer2.setBackgroundColor(getResources().getColor(R.color.red));
                flashcardAnswer.setBackgroundColor(getResources().getColor(R.color.green));

            }
        });
        flashcardAnswer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flashcardAnswer3.setBackgroundColor(getResources().getColor(R.color.red));
                flashcardAnswer.setBackgroundColor(getResources().getColor(R.color.green));

            }
        });

        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoAddCard = new Intent(MainActivity.this, AddCardActivity.class);
 //                   startActivity(gotoAddCard);
                startActivityForResult(gotoAddCard, 100);


            }
        });







        flashcardDatabase = new FlashcardDatabase(getApplicationContext());
        allFlashcards = flashcardDatabase.getAllCards();




        findViewById(R.id.next_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (allFlashcards == null || allFlashcards.size() == 0) {
                    setContentView(R.layout.such_empty);
                    /*findViewById(R.id.add_first).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            gotoAddCard = new Intent(MainActivity.this, AddCardActivity.class);
                                              startActivity(gotoAddCard);
                            startActivityForResult(gotoAddCard, 100);
                        }
                    });*/
                    return;
                }

                currentDisplayedIndex += 1;


                if (currentDisplayedIndex >= allFlashcards.size()) {
                    Snackbar.make(findViewById(R.id.next_button),
                            "You've reached the end of the flashcards.",
                            Snackbar.LENGTH_SHORT)
                            .show();
                    currentDisplayedIndex = 0;
                }

                Flashcard currentcard = allFlashcards.get(currentDisplayedIndex);
                flashcardQuestion.setText(currentcard.getQuestion());
                flashcardAnswer.setText(currentcard.getAnswer());
                flashcardAnswer2.setText(currentcard.getWrongAnswer1());
                flashcardAnswer3.setText(currentcard.getWrongAnswer2());




            }
        });
        findViewById(R.id.delete_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flashcardDatabase.deleteCard(((TextView) findViewById(R.id.flashcard_question)).getText().toString());
                allFlashcards = flashcardDatabase.getAllCards();
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100){
            if (data != null){
                String questionString = data.getExtras().getString("Question key");
                String answerString = data.getExtras().getString("Answer key");
                String wrongAnswer1 = data.getExtras().getString("Wrong Answer1");
                String wrongAnswer2 = data.getExtras().getString("Wrong Answer2");
//                flashcardQuestion.setText(questionString);
//                flashcardAnswer.setText(answerString);
//                flashcardAnswer2.setText(wrong1String);
//                flashcardAnswer3.setText(wrong2String);
                Snackbar.make(findViewById(R.id.flashcard_question),
                        "Successfully added question",
                        Snackbar.LENGTH_SHORT)
                       .show();
                Flashcard flashcard = new Flashcard(questionString, answerString,wrongAnswer1,wrongAnswer2);
                flashcardDatabase.insertCard(flashcard);

                allFlashcards = flashcardDatabase.getAllCards();

            }
        }
    }
}