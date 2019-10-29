package com.example.quizapp;

import android.content.Intent;

import java.util.List;

public class Quiz {
    private List<Question> questions;
    private int score;
    private int currentQ;

    public Quiz(List<Question> questions) {
        this.questions = questions;
        score = 0;
        currentQ = 0;
    }

    public boolean checkAnswer(boolean answer) {

        return questions.get(currentQ).getAnswer() == answer;
    }

    public boolean LastQuestionCheck() {

        if (questions.size() - 1 > currentQ)
            return false;
        else
            return true;


    }

    public String nextQuestion() {

        currentQ++;
        return questions.get(currentQ).getQuestion();
    }


    public int getCurrentQ() {

        return currentQ;
    }

    public void setCurrentQ(int currentQuestion) {

        this.currentQ = currentQuestion;
    }

    public int getScore() {

        return score;
    }

    public void setScore(int score) {

        this.score = score;
    }

    public List<Question> getQuestions() {

        return questions;
    }

    public void setQuestions(List<Question> quiz) {

        this.questions = questions;
    }
}