package com.example.delta_task1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String CHNC_LEFT_KEY= "chncLeft";
    private static final String YOD_KEY= "YOD";

    private EditText etYOD, etGuess;
    private TextView tvChncLeft;
    private Button btnGuess;
    int noOfChncLeft, YOD=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etGuess= findViewById(R.id.etGuess);
        etYOD= findViewById(R.id.etYOD);
        tvChncLeft= findViewById(R.id.tvChncLeft);
        btnGuess= findViewById(R.id.btnEnter);

        if (savedInstanceState!= null) {
            if (savedInstanceState.containsKey(CHNC_LEFT_KEY)) {
                noOfChncLeft= savedInstanceState.getInt(CHNC_LEFT_KEY);
            }

            if (savedInstanceState.containsKey(YOD_KEY)) {
                YOD= savedInstanceState.getInt(YOD_KEY);
                if (YOD!= 0) {
                    btnGuess.setEnabled(true);
                    tvChncLeft.setText("No. of Chance Left : "+noOfChncLeft);
                }
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CHNC_LEFT_KEY, noOfChncLeft);
        outState.putInt(YOD_KEY, YOD);
    }

    private void setToast(String s) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, s, duration);
        toast.show();
    }

    private void Calculate(int Set, int Guess) {
        if (Guess-Set>0) {
            setToast("Guess is higher");
            noOfChncLeft--;
            tvChncLeft.setText("No. of Chance Left : "+noOfChncLeft);
            if (noOfChncLeft==0) {
                final int RED= 0xFFFF0000;

                btnGuess.setEnabled(false);
                tvChncLeft.setTextColor(RED);
                tvChncLeft.setText("You have lost the Game");
                YOD=0;
            }
        }
        else if (Guess-Set<0) {
            setToast("Guess is lower");
            noOfChncLeft--;
            tvChncLeft.setText("No. of Chance Left : "+noOfChncLeft);
            if (noOfChncLeft==0) {
                final int RED= 0xFFFF0000;

                btnGuess.setEnabled(false);
                tvChncLeft.setTextColor(RED);
                tvChncLeft.setText("You have lost the Game");
                YOD=0;
            }
        }
        else {
            final int GREEN= 0xFF33FF33;

            setToast("Correct Guess");
            YOD= 0;
            btnGuess.setEnabled(false);
            tvChncLeft.setText("You have won the Game");
            tvChncLeft.setTextColor(GREEN);
        }
    }

    public void Set(View view) {
        final int WHITE= 0xFFFFFFFF;

        if(etYOD.getText().length()==0) {
            setToast("Set the year of Death");
        }

        else if (Integer.parseInt(etYOD.getText().toString())>100 || Integer.parseInt(etYOD.getText().toString())==0 ) {
            etYOD.getText().clear();
            setToast("Choose age from 1-100");
        }

        else {
            YOD=Integer.parseInt(etYOD.getText().toString());
            etYOD.getText().clear();
            noOfChncLeft= 10;
            setToast("Successfully set. Now Guess can be made");
            btnGuess.setEnabled(true);
            tvChncLeft.setTextColor(WHITE);
            tvChncLeft.setText("No. of Chances Left are : "+noOfChncLeft);
        }
    }

    public void Guess(View view) {
        if(etGuess.getText().length()==0) {
            setToast("Guess the year of Death");
        }

        else if (Integer.parseInt(etGuess.getText().toString())>100 || Integer.parseInt(etGuess.getText().toString())==0 ) {
            etGuess.getText().clear();
            setToast("Guess age from 1-100");
        }

        else {
            int Guess= Integer.parseInt(etGuess.getText().toString());
            etGuess.getText().clear();
            Calculate(YOD, Guess);
        }
    }
}
