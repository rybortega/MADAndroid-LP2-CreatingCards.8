package com.example.flashcard_app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AddCardActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_card_activity);

        findViewById(R.id.cancel_icon).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ImageView saveQuestionImageView = findViewById(R.id.save_button);
        saveQuestionImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                String inputQuestion = ((EditText) findViewById(R.id.question_input)).getText().toString();
                String inputAnswer = ((EditText) findViewById(R.id.answer_input)).getText().toString();
                //Log.d("AddCardActivity", "hi" + inputQuestion + ", " + inputAnswer );
                data.putExtra("QuestionKey",inputQuestion);
                data.putExtra("AnswerKey",inputAnswer);
                String str = data.getExtras().getString("QuestionKey");

                //Log.d("AddCardActivity", "hi" + str );
                setResult(RESULT_OK, data);
                finish();

            }
        });


    }
}