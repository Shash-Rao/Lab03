package com.raoshashwat.lab03;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    String TAG = "com.raoshashwat.lab03.sharedprefs";
    Button bRight, bLeft;
    TextView tRight, tLeft;
    SeekBar seekBar;
    TextView[] views;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bRight = findViewById(R.id.bottomright_button);
        bLeft = findViewById(R.id.bottomleft_button);
        tRight = findViewById(R.id.topright_text);
        tLeft = findViewById(R.id.topleft_text);
        seekBar = findViewById(R.id.seekbar);
        views = new TextView[]{bRight, bLeft, tRight, tLeft};
        bRight.setOnClickListener(this);
        bLeft.setOnClickListener(this);
        tRight.setOnClickListener(this);
        tLeft.setOnClickListener(this);
        sharedPreferences = getSharedPreferences(TAG, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b)
            {
                for (TextView view: views)
                    view.setTextSize(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {

            }
        });
        setInitialValues();
    }

    private void setInitialValues()
    {
        for (TextView view: views)
            view.setText(sharedPreferences.getString(view.getTag().toString(), "0"));
        seekBar.setProgress(30);
    }

    @Override
    public void onClick(View view)
    {
        TextView x = (TextView) view;
        x.setText("" + (Integer.parseInt(x.getText().toString()) + 1));
        editor.putString(x.getTag().toString(), x.getText().toString()).apply();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        setInitialValues();
    }
}