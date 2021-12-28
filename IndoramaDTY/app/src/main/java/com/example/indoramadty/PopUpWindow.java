package com.example.indoramadty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class PopUpWindow extends AppCompatActivity {
    public Button yes, no;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_window);
        yes = (Button) findViewById(R.id.Yes);
        no = (Button) findViewById(R.id.No);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .95), (int) (height*.37));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nodropdown();
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dodropdown();
            }
        });
    }
    private void nodropdown(){
        Intent intent = new Intent(this, scanningwhenyes.class);
        startActivity(intent);
    }
    private void dodropdown(){
        Intent intent = new Intent(this, Scanning.class);
        startActivity(intent);
    }
}