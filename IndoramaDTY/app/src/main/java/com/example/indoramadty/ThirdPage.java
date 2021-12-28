package com.example.indoramadty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import java.util.ArrayList;
import java.util.List;

public class ThirdPage extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
        int count;
        public EditText name;
        public Button view;
        public Button back;
        public Button close;
        int currentList, flag = 0;
        ImageButton imagebutton;
        private Spinner spinner;
        DatabaseAccess databaseAccess;
        RecyclerView recyclerView;
        Adapter adapter;
        protected  void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(R.layout.activity_third_page);
            recyclerView = (RecyclerView) findViewById(R.id.recycler);
            back = (Button) findViewById(R.id.button2);
            close = (Button) findViewById(R.id.button3);
            imagebutton = (ImageButton) findViewById(R.id.imageButton3);
            spinner = findViewById(R.id.spinner);
            spinner.setOnItemSelectedListener(this);
            String[] items = getResources().getStringArray(R.array.items);
            ArrayAdapter ad1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, items);
                ad1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(ad1);
            this.databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
            databaseAccess.open();
            this.count = databaseAccess.getData(currentList);

            view = findViewById(R.id.btnView);

            view.setOnClickListener(v -> {
                flag = 1;
                setRecyclerView();
            });
            close.setOnClickListener(v -> goback());
            imagebutton.setOnClickListener(v -> goback());
            // setRecyclerView();
            back.setOnClickListener(v -> dodropdown());
        }
        public void dodropdown(){
            Intent intent = new Intent(this, Scanning.class);
            startActivity(intent);
        }
        public void goback(){
//            Log.i("-------------------");
            databaseAccess.close();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        private void setRecyclerView(){
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter = new Adapter(this, getList());
            recyclerView.setAdapter(adapter);
        }

        private List<method> getList(){
            List<method> payment_list = new ArrayList<>();

            for(int i = 0; i < count; i++){
                payment_list.add(new method(databaseAccess.buffer1[i],
                        databaseAccess.buffer2[i],
                        databaseAccess.buffer3[i],
                        databaseAccess.buffer4[i],
                        databaseAccess.buffer5[i],
                        databaseAccess.buffer6[i],
                        databaseAccess.buffer7[i],
                        databaseAccess.buffer8[i]));
            }

            return payment_list;
        }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getId() == R.id.spinner){
            // String value = parent.getItemAtPosition(position).toString();
            // Log.i("---------------------"," " + position);
            this.currentList = position;
            this.count = databaseAccess.getData(currentList);
            if(flag == 1) {
                setRecyclerView();
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
