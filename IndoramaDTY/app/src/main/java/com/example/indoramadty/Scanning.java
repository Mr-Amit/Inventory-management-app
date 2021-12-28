package com.example.indoramadty;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class Scanning extends AppCompatActivity {
    // String deli= "800746091",line= "10",pall, cno;
    String deli, pall, line, cno;

    String[] arrOfStr;
    Spinner delispinner,palletspinner,scan;
    TextView lbl,groosWt,netWt,reqPrd,totPrd,excessNet,balNet;
    TextView totcarton,scancarton;
    EditText cartonNo;
    String[] data = new String[600];
    int count;
    public EditText name;
    public Button view, closebtn,scanbtn;
    public TextView result_address;
    DatabaseAccess databaseAccess;
    RecyclerView recyclerView;
    ImageButton cancel, clear;
    int spinnerpos = 0;

    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scanning);
        recyclerView = findViewById(R.id.recycler);
        this.databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();
        int cnt = this.count = databaseAccess.getData(0);
        // Toast.makeText(getApplicationContext(), choice,Toast.LENGTH_LONG).show();
        view = findViewById(R.id.btnView);
        result_address = findViewById(R.id.result);
        delispinner = findViewById(R.id.delino);
        palletspinner = findViewById(R.id.palletno);
        cartonNo=findViewById(R.id.cartonNo);
        cno=cartonNo.getText().toString();
        scan = findViewById(R.id.scan);
        lbl=findViewById(R.id.textView8);
        groosWt=findViewById(R.id.textView9);
        netWt=findViewById(R.id.textView10);

        reqPrd=findViewById(R.id.reqPrd);
        totPrd=findViewById(R.id.totPrd);
        excessNet=findViewById(R.id.excessNet);
        balNet=findViewById(R.id.balNet);

        totcarton=findViewById(R.id.totcart);
        scancarton=findViewById(R.id.scancarton);
        cancel = findViewById(R.id.imageButton);
        cancel.setOnClickListener(v -> goback());
        scanbtn = findViewById(R.id.scanbtn);
        String totalQty = databaseAccess.getTotalQty();
        lbl.setText(totalQty);

        String gwt = databaseAccess.getTotalGross();
        groosWt.setText(gwt);

        String nwt = databaseAccess.getTotalNet();
        netWt.setText(nwt);
        scanbtn.setOnClickListener(v -> {
            cno=cartonNo.getText().toString();

            if(!databaseAccess.cartonNoExists(cno)){
                System.out.println("Wrong Carton Number");
                Toast.makeText(getBaseContext(),"Wrong Carton Number",Toast.LENGTH_LONG).show();
            }
            else {
                int res = databaseAccess.updateFlag1(cno, deli);
                if (res == 1) {
                    update();
                } else {
                    Toast.makeText(getApplicationContext(),"Carton already scanned",Toast.LENGTH_SHORT).show();
                    System.out.println("Carton already scanned");
                }
                scan.setSelection(0);
                 cartonNo.setText("");
            }
        });

        closebtn = findViewById(R.id.closebtn);
        closebtn.setOnClickListener(v -> goback());
        String[] options = getResources().getStringArray(R.array.scan);
        ArrayAdapter adp3 = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, options);
        adp3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        scan.setAdapter(adp3);
        scan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // clicked item will be shown as spinner

                // Toast.ma3keText(getApplicationContext(),""+parent.getItemAtPosition(position).toString(),Toast.LENGTH_SHORT).show();
                String x=parent.getItemAtPosition(position).toString();
                // System.out.println("---------SPINNER-------" + position);
                if(x.equals("Done")){
                    // Toast.makeText(getApplicationContext(),"Done with carton scanning",Toast.LENGTH_SHORT).show();
                    System.out.println("----------------------" + x);
                    spinnerpos = 0;
                }else if(x.equals("Total Cancel")){
                    spinnerpos = 1;
                    if(!databaseAccess.cartonNoExists(cno)){
                        System.out.println("Wrong Carton Number");
                        Toast.makeText(getBaseContext(),"Wrong Carton Number",Toast.LENGTH_LONG).show();
                    }
                    else {
                        databaseAccess.updateFlag0(cno, deli);
                        System.out.println("----------------------" + x);
                        // Toast.makeText(getApplicationContext(),"Scanning cancelled",Toast.LENGTH_SHORT).show();
                        update();
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        String[] list1=databaseAccess.getDeliNos();
        ArrayAdapter adp1 = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, list1);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        delispinner.setAdapter(adp1);

        delispinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // clicked item will be shown as spinner
                // Toast.makeText(getApplicationContext(),""+parent.getItemAtPosition(position).toString(),Toast.LENGTH_SHORT).show();
                String str=parent.getItemAtPosition(position).toString();
                if(str.indexOf('-') != -1) {
                    arrOfStr = str.split("-");
                    deli = arrOfStr[0];
                    line = arrOfStr[1];
                }
                update();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        ArrayAdapter<String> adp2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, databaseAccess.getPalletNos());
        adp2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        palletspinner.setAdapter(adp2);
        palletspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // clicked item will be shown as spinner
                // Toast.makeText(getApplicationContext(),""+parent.getItemAtPosition(position).toString(),Toast.LENGTH_SHORT).show();
                pall=palletspinner.getSelectedItem().toString();

                update();

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        update();
        clear = findViewById(R.id.imageButton2);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pall.equals("Select") || deli.equals("Select") || cno.equals("")) {
                    Toast.makeText(getApplicationContext(), "Please select all fields", Toast.LENGTH_SHORT).show();
                }
                else if(spinnerpos == 0) {
                    delispinner.setSelection(0);
                    scan.setSelection(0);
                    cartonNo.setText("");
                    cno = "";
                    palletspinner.setSelection(0);
                    Toast.makeText(getApplicationContext(), "Task Successfull!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Select Option", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void update(){

//        if(!databaseAccess.cartonNoExists(cno)){
//            System.out.println("CartonNo Invalid");
//            Toast.makeText(getApplicationContext(),"CartonNo Invalid",Toast.LENGTH_SHORT).show();
//        }
        if(pall != null && deli != null) {
            if (pall.equals("Select") || deli.equals("Select")) {
                System.out.println(")))((()))((((");
                put0s();
                return;
            }
        }

        if(!databaseAccess.deliveryNoExists(deli)){
            System.out.println("Choose Delivery Number");
            Toast.makeText(getApplicationContext(),"Choose Delivery Number",Toast.LENGTH_SHORT).show();
            return;
        }
        if(!databaseAccess.palletNoExists(pall)){
            System.out.println("Choose Valid Pallet Number");
            Toast.makeText(getApplicationContext(),"Choose pallet Number",Toast.LENGTH_SHORT).show();
            return;
        }
//        System.out.println(")))((()))((((" + pall);
        String rp = databaseAccess.getReqPrd(pall);
        reqPrd.setText(rp);

        String tc = databaseAccess.getTotalCarton(deli, pall, line);
        totcarton.setText(tc);
        // System.out.println("--------------------------------"+tc);
        if(tc.equals("0")){
            excessNet.setText("0");
//            reqPrd.setText("0");
            balNet.setText("0");
            scancarton.setText("0");
            totPrd.setText("0");
            return;
        }

        String tp = databaseAccess.getTotPrd(pall, line, deli);
        if(tp == null){
            tp = "0";
        }

        totPrd.setText(tp);

        Double en = databaseAccess.getExcessNet();
        excessNet.setText(String.valueOf(en));

        Double bn = databaseAccess.getBalNet();
        balNet.setText(String.valueOf(bn));


        String sc = databaseAccess.getScanCarton(deli, pall, line);
        scancarton.setText(sc);

    }
    void put0s(){
        totcarton.setText("0");
        scancarton.setText("0");
        balNet.setText("0");
        totPrd.setText("0");
        reqPrd.setText("0");
        excessNet.setText("0");
    }
    public void goback(){
//            Log.i("-------------------");
        databaseAccess.close();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
