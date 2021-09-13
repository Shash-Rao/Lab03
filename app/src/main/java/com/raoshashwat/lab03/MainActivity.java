package com.raoshashwat.lab03;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    String TAG = "com.raoshashwat.lab03.sharedprefs";
    Button bRight, bLeft;
    TextView tRight, tLeft;
    TextView targetText;
    TextView[] views;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ConstraintLayout layout;
    int rand;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        targetText = findViewById(R.id.target_text);
        bRight = findViewById(R.id.bottomright_button);
        bLeft = findViewById(R.id.bottomleft_button);
        tRight = findViewById(R.id.topright_text);
        tLeft = findViewById(R.id.topleft_text);
        views = new TextView[]{bRight, bLeft, tRight, tLeft};
        layout = findViewById(R.id.activity_main_layout);
        bRight.setOnClickListener(this);
        bLeft.setOnClickListener(this);
        tRight.setOnClickListener(this);
        tLeft.setOnClickListener(this);
        sharedPreferences = getSharedPreferences(TAG, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        setInitialValues();
    }

    private void setInitialValues()
    {
        for (TextView view: views)
            view.setText(sharedPreferences.getString(view.getTag().toString(), "0"));
        rand = (int)(Math.random()*(40-20+1)+20);
        targetText.setText("Make " + rand + " \n" + tLeft.getText() + " + " + tRight.getText() + " + " + bLeft.getText() + " + " + bRight.getText());
    }

    @Override
    public void onClick(View view)
    {
        TextView x = (TextView) view;
        x.setText("" + (Integer.parseInt(x.getText().toString()) + 1));
        editor.putString(x.getTag().toString(), x.getText().toString()).apply();
        targetText.setText("Make " + rand + " \n" + tLeft.getText() + " + " + tRight.getText() + " + " + bLeft.getText() + " + " + bRight.getText());
    }

    private void win()
    {
        targetText.setText("Congratulations! You win!\nClick restart to try again");
        editor.clear().apply();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        setInitialValues();
    }

    public void restart(View view)
    {
        editor.clear().apply();
        setInitialValues();
    }

    public void submit(View view)
    {
        int sum = 0;
        for (TextView v: views)
        {
            sum += Integer.parseInt((String) v.getText());
        }
        if (sum == rand)
        {
            win();
        }
        else
        {
            targetText.setText("Sorry, that's not correct\nClick restart to try again");
            editor.clear().apply();
        }
    }
}