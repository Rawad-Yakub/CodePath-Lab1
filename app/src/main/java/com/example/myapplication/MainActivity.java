package com.example.myapplication;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
//                findViewById(R.id.flashcard_answer).setVisibility(View.VISIBLE);
  //              findViewById(R.id.flashcard_answer2).setVisibility(View.VISIBLE);
    //            findViewById(R.id.flashcard_answer3).setVisibility(View.VISIBLE);

                View answerSideView = findViewById(R.id.flashcard_answer);
                View answerSideView2 = findViewById(R.id.flashcard_answer2);
                View answerSideView3 = findViewById(R.id.flashcard_answer3);

// get the center for the clipping circle
                int cx = answerSideView.getWidth() / 2;
                int cy = answerSideView.getHeight() / 2;
                int ca = answerSideView2.getWidth() / 2;
                int cb = answerSideView2.getHeight() / 2;
                int cd = answerSideView3.getWidth() / 2;
                int ce = answerSideView3.getHeight() / 2;


// get the final radius for the clipping circle
                float finalRadius = (float) Math.hypot(cx, cy);
                float finalRadius2 = (float) Math.hypot(ca, cb);
                float finalRadius3 = (float) Math.hypot(cd, ce);

// create the animator for this view (the start radius is zero)
                Animator anim = ViewAnimationUtils.createCircularReveal(answerSideView, cx, cy, 0f, finalRadius);
                Animator anim2 = ViewAnimationUtils.createCircularReveal(answerSideView2, ca, cb, 0f, finalRadius2);
                Animator anim3 = ViewAnimationUtils.createCircularReveal(answerSideView3, cd, ce, 0f, finalRadius3);


// hide the question and show the answer to prepare for playing the animation!
                flashcardQuestion.setVisibility(View.INVISIBLE);
                answerSideView.setVisibility(View.VISIBLE);
                answerSideView2.setVisibility(View.VISIBLE);
                answerSideView3.setVisibility(View.VISIBLE);


                anim.setDuration(2000);
                anim2.setDuration(2000);
                anim3.setDuration(2000);
                anim.start();
                anim2.start();
                anim3.start();


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
                //startActivity(gotoAddCard);
                MainActivity.this.startActivityForResult(gotoAddCard, 100);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);


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



                final Animation leftOutAnim = AnimationUtils.loadAnimation(v.getContext(), R.anim.left_out);
                final Animation rightInAnim = AnimationUtils.loadAnimation(v.getContext(), R.anim.right_in);

                leftOutAnim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                        flashcardQuestion.startAnimation(rightInAnim);

                        Flashcard currentcard = allFlashcards.get(currentDisplayedIndex);
                        flashcardQuestion.setText(currentcard.getQuestion());
                        flashcardAnswer.setText(currentcard.getAnswer());
                        flashcardAnswer2.setText(currentcard.getWrongAnswer1());
                        flashcardAnswer3.setText(currentcard.getWrongAnswer2());

                        flashcardQuestion.setVisibility(View.VISIBLE);
                        flashcardAnswer.setVisibility(View.INVISIBLE);
                        flashcardAnswer2.setVisibility(View.INVISIBLE);
                        flashcardAnswer3.setVisibility(View.INVISIBLE);

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                flashcardQuestion.startAnimation(leftOutAnim);

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