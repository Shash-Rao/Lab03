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
    SeekBar seekBar;
    TextView[] views;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ConstraintLayout layout;

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
        layout = findViewById(R.id.activity_main_layout);
        bRight.setOnClickListener(this);
        bLeft.setOnClickListener(this);
        tRight.setOnClickListener(this);
        tLeft.setOnClickListener(this);
        sharedPreferences = getSharedPreferences(TAG, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            int lastProgress;
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b)
            {
                for (TextView view: views)
                    view.setTextSize(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {
                lastProgress = seekBar.getProgress();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
                Snackbar snackbar = Snackbar.make(layout, "Font size changed to " + seekBar.getProgress() + "sp", Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        seekBar.setProgress(lastProgress);
                        for (TextView x: views)
                            x.setTextSize(lastProgress);
                        Snackbar.make(layout, "Font size reverted to " + seekBar.getProgress() + "sp", Snackbar.LENGTH_LONG);
                    }
                });
                snackbar.setActionTextColor(Color.MAGENTA);
                View snackbarView = snackbar.getView();
                TextView textView = snackbarView.findViewById(R.id.snackbar_text);
                textView.setTextColor(Color.WHITE);
                snackbar.show();
            }
        });
        layout.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View view)
            {
                editor.clear().apply();
                setInitialValues();
                return false;
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