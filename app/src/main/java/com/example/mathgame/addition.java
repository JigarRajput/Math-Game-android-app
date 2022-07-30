package com.example.mathgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;
import java.util.Random;


public class addition extends AppCompatActivity {

    TextView scoreView, lifeView, timerView;
    static TextView tvQues ;
    EditText ansSpace;
    Button btnSubmit, btnNext;

    Random random = new Random();
    int number1, number2, userAns, correctAns;
    int life = 3;
    int score = 0;

    CountDownTimer countDownTimer;

    private static final long START_TIMER_IN_MILLIS = 20000;
    Boolean timer_running;
    long TIME_LEFT_IN_MILLIS = START_TIMER_IN_MILLIS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addition);

        scoreView = findViewById(R.id.scoreView);
        lifeView = findViewById(R.id.lifeView);
        timerView = findViewById(R.id.timerView);
        tvQues = findViewById(R.id.tvQues);
        ansSpace = findViewById(R.id.ansSpace);

        btnSubmit = findViewById(R.id.btnSubmit);
        btnNext = findViewById(R.id.btnNext);

        gameContinue();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ansSpace.getText().toString().trim().length() == 0) {
                    pauseTimer();
                    resetTimer();
                    tvQues.setText("Answer cannot be empty !!");
                }

                else {
                    userAns = Integer.valueOf(ansSpace.getText().toString());
                    pauseTimer();
                    if (userAns == correctAns) {
                        score = score + 10;
                        scoreView.setText(score + "");
                        tvQues.setText(" You're genius !! ");
                        resetTimer();
                    }
                    else {
                        life = life - 1;
                        lifeView.setText(life + "");
                        tvQues.setText(" Wrong answer dude !! ");
                        resetTimer();
                    }
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ansSpace.setText("");
                pauseTimer();
                resetTimer();
                gameContinue();
            }
        });
    }

    public void gameContinue(){
        number1 = random.nextInt(100);
        number2 = random.nextInt(200);

        tvQues.setText(number1 + " + " + number2);
        correctAns = number1 + number2;
        startTimer();

    }

    public void startTimer(){
        countDownTimer = new CountDownTimer(TIME_LEFT_IN_MILLIS,1000) {
            @Override
            public void onTick(long l)
            {
                TIME_LEFT_IN_MILLIS = l;
                updateText();
            }

            @Override
            public void onFinish() {
                timer_running = false;
                pauseTimer();
                resetTimer();
                updateText();
                life = life - 1;
                lifeView.setText(life+"");
                tvQues.setText("Sorry!, Your time's UP !!");
            }
        }.start();
        timer_running = true;
    }

    public void updateText()
    {
        int second = (int)(TIME_LEFT_IN_MILLIS / 1000) % 60;
        String time_left = String.format(Locale.getDefault(), "%02d",second);
        timerView.setText(time_left);
    }
    public void pauseTimer()
    {
        countDownTimer.cancel();
        timer_running = false;
    }
    public void resetTimer()
    {
       TIME_LEFT_IN_MILLIS = START_TIMER_IN_MILLIS;
       updateText();
    }
}