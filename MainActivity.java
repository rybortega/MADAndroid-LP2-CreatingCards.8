package com.example.flashcard_app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    TextView flashcardQuestion;
    TextView flashcardAnswer;

    com.example.flashcard_app.FlashcardDatabase flashcardDatabase;
    List<com.example.flashcard_app.Flashcard> allFlashcards;
    int cardIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flashcardQuestion = findViewById(R.id.flashard_question);
        flashcardAnswer = findViewById(R.id.flashard_answer);
        flashcardQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flashcardQuestion.setVisibility(View.INVISIBLE);
                flashcardAnswer.setVisibility(View.VISIBLE);


// get the center for the clipping circle
                int cx = flashcardAnswer.getWidth() / 2;
                int cy = flashcardAnswer.getHeight() / 2;

// get the final radius for the clipping circle
                float finalRadius = (float) Math.hypot(cx, cy);

// create the animator for this view (the start radius is zero)
                Animator anim = ViewAnimationUtils.createCircularReveal(flashcardAnswer, cx, cy, 0f, finalRadius);

// hide the question and show the answer to prepare for playing the animation!
                flashcardQuestion.setVisibility(View.INVISIBLE);
                flashcardAnswer.setVisibility(View.VISIBLE);

                anim.setDuration(3000);
                anim.start();
            }
        });

        ImageView addQuestionImageView = findViewById(R.id.nextPage_image);
        addQuestionImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                startActivityForResult(intent, 100);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });
        flashcardDatabase = new com.example.flashcard_app.FlashcardDatabase(getApplicationContext());
        allFlashcards = flashcardDatabase.getAllCards();

        if(allFlashcards != null && allFlashcards.size() > 0 ) {
            Flashcard firstCard = allFlashcards.get(0);
            flashcardQuestion.setText(firstCard.getQuestion());
            flashcardAnswer.setText(firstCard.getAnswer());
            findViewById(R.id.next_question);


        }
        findViewById(R.id.next_question).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //flashcardQuestion.setVisibility(View.VISIBLE);
                //.setVisibility(View.INVISIBLE);
                if (allFlashcards == null || allFlashcards.size() == 0) {
                    return;
                }
                cardIndex += 1;
                flashcardQuestion.setVisibility(View.VISIBLE);
                flashcardAnswer.setVisibility(View.INVISIBLE);
                if(cardIndex >=  allFlashcards.size()) {
                    Snackbar.make(view,
                            "you've reached the end of the cards going back to the start.",
                            Snackbar.LENGTH_SHORT)
                            .show();
                    cardIndex = 0;
                }

                    Flashcard currentCard = allFlashcards.get(cardIndex);

                    flashcardQuestion.setText(currentCard.getQuestion());
                    flashcardAnswer.setText(currentCard.getAnswer());

                final Animation leftOutAnim = AnimationUtils.loadAnimation(view.getContext(), R.anim.left_out);
                final Animation rightInAnim = AnimationUtils.loadAnimation(view.getContext(), R.anim.right_in);


                leftOutAnim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        // this method is called when the animation first starts
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        // this method is called when the animation is finished playing
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        // we don't need to worry about this method
                    }
                });


                flashcardQuestion.startAnimation(leftOutAnim);
                flashcardAnswer.startAnimation(rightInAnim);
            }
        });







    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Log.d("onActivityResult", "Success" + data.getExtras().getString("QuestionKey"));
        if (requestCode == 100) {
            //get data
            //Log.d("onActivityResult", "requestCode == 100");
            if (data != null) {//check if there's an intent

                flashcardQuestion.setVisibility(View.VISIBLE);
                flashcardAnswer.setVisibility(View.INVISIBLE);

                String QuestionString = data.getExtras().getString("QuestionKey");
                String AnswerString = data.getExtras().getString("AnswerKey");
                flashcardQuestion.setText(QuestionString);
                flashcardAnswer.setText(AnswerString);
                //Log.d("onActivityResult", QuestionString + "; " + AnswerString);
                com.example.flashcard_app.Flashcard  flashcard = new com.example.flashcard_app.Flashcard(QuestionString, AnswerString);
                flashcardDatabase.insertCard(flashcard);
                allFlashcards = flashcardDatabase.getAllCards();

            }
        }
    }
}
