package com.book.dan.geoquiz;

import android.support.annotation.MainThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";



    private static final String KEY_INDEX = "index";

    Button mTrueButton;
    Button mFalseButton;
    Button mNextButton;
    TextView mQuestionTextView;

    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_australia,true),
            new Question(R.string.question_oceans,true),
            new Question(R.string.question_mideast,false),
            new Question(R.string.question_africa,false),
            new Question(R.string.question_americas,true),
            new Question(R.string.question_asia,true)
    };
    private int mCurrentIndex = 0;
    private int mCorrectAnswers = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        if(savedInstanceState!=null)
            mCurrentIndex=savedInstanceState.getInt(KEY_INDEX);

        mTrueButton = (Button)findViewById(R.id.true_button);
        mFalseButton = (Button) findViewById(R.id.false_button);
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mNextButton = (Button) findViewById(R.id.next_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(true);
                blockButtons(false);
            }
        });
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
                blockButtons(false);
            }
        });
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextQuestion();
            }
        });
        updateQuestion();
    }

    private void blockButtons(boolean isEnabled){
        mTrueButton.setEnabled(isEnabled);
        mFalseButton.setEnabled(isEnabled);
    }

    private void nextQuestion() {
        if(mCurrentIndex!=(mQuestionBank.length-1)){
            mCurrentIndex=(mCurrentIndex+1)%mQuestionBank.length;
            updateQuestion();
        }
        else
        {
            mNextButton.setEnabled(false);
            mQuestionTextView.setText(getString(R.string.result_text,mCorrectAnswers*100/mQuestionBank.length));
        }
    }

    private void updateQuestion() {
        blockButtons(true);
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userAnswer){
        boolean correctAnswer = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageResultId = 0;
        if(userAnswer==correctAnswer){
            messageResultId = R.string.correct_toast;
            mCorrectAnswers++;
        }
        else
            messageResultId = R.string.incorrect_toast;
        Toast.makeText(this, messageResultId, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState");
        outState.putInt(KEY_INDEX,mCurrentIndex);
    }
}
