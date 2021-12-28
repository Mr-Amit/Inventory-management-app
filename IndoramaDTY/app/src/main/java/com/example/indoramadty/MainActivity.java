package com.example.indoramadty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    Button scanOrder, viewReport, exit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scanOrder = findViewById(R.id.ScanOrder);
        viewReport =  findViewById(R.id.ViewReport);
        exit =  findViewById(R.id.Exit);
        scanOrder.setOnClickListener(v -> SystemAdminPopUp());

        viewReport.setOnClickListener(v -> openScanReport());
        exit.setOnClickListener(v -> exitApp());
    }

    private void SystemAdminPopUp() {
        Intent adminpopup = new Intent(MainActivity.this, PopUpWindow.class);
        startActivity(adminpopup);
    }

    public void openScanReport(){
        Intent intent = new Intent(this, ThirdPage.class);
        startActivity(intent);
    }

    public void exitApp(){
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory( Intent.CATEGORY_HOME );
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }
}