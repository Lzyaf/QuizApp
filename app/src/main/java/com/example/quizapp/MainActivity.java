package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    private int currentQ = 0;
    private List<Question> questionsList;
    private int score;
    private Button trueButton;
    private Button falseButton;
    private TextView questionText;
    private Quiz quiz;

    public static final String EXTRA_SCORE = "score";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wireWidgets();
        setListeners();

        InputStream JsonFileInputStream = getResources().openRawResource(R.raw.questions);
        String jsonString = readTextFile(JsonFileInputStream);
        Gson gson = new Gson();
        Question[] questions =  gson.fromJson(jsonString, Question[].class);
        questionsList = Arrays.asList(questions);
        Log.d(TAG, "onCreate: " + questions.toString());
        quiz = new Quiz(questionsList);
        questionText.setText(quiz.getQuestions().get(quiz.getCurrentQ()).getQuestion());
    }

    private void setListeners() {
        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast yesToast = Toast.makeText(getApplicationContext(), "RIGHT", Toast.LENGTH_SHORT);
                Toast noToast = Toast.makeText(getApplicationContext(), "WRONG", Toast.LENGTH_SHORT);
                if (quiz.checkAnswer(true)) {
                    score++;
                    yesToast.show();
                }
                else {
                    noToast.show();
                }
                if (!quiz.LastQuestionCheck()) {

                    questionText.setText(quiz.nextQuestion());
                }
                else {
                    Intent targetIntent =
                            new Intent(MainActivity.this, ScoreActivity.class);
                    targetIntent.putExtra(EXTRA_SCORE, score);
                    startActivity(targetIntent);
                }

            }
        });

        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast yesToast = Toast.makeText(getApplicationContext(), "RIGHT", Toast.LENGTH_SHORT);
                Toast noToast = Toast.makeText(getApplicationContext(), "WRONG", Toast.LENGTH_SHORT);
                if (quiz.checkAnswer(false)) {
                    score++;
                    yesToast.show();

                }
                else {
                    noToast.show();
                }
                    if (!quiz.LastQuestionCheck()) {
                        questionText.setText(quiz.nextQuestion());
                }
                    else {
                        Intent targetIntent =
                                new Intent(MainActivity.this, ScoreActivity.class);
                        targetIntent.putExtra(EXTRA_SCORE, score);
                        startActivity(targetIntent);
                    }
            }
        });
    }

    private void wireWidgets() {
        trueButton = findViewById(R.id.button_main_true);
        falseButton = findViewById(R.id.button_main_false);
        questionText = findViewById(R.id.textView_main_question);
    }

    public String readTextFile(InputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        byte buf[] = new byte[1024];
        int len;
        try {
            while ((len = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {

        }
        return outputStream.toString();
    }


}