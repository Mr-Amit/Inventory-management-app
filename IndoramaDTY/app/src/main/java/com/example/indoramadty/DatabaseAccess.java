package com.example.indoramadty;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class DatabaseAccess {
    String deli= "800746091",pall= "1306006971",line= "10",cno;
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private  static DatabaseAccess instance;
    Cursor c = null;
    String[] buffer1,buffer2,buffer3,buffer4,buffer5,buffer6,buffer7,buffer8;
    String[] delivery_line,pallet;
    String reqprd, scanprd;
    private DatabaseAccess(Context context){
        this.openHelper = new DBHelper(context);
    }
    public  static DatabaseAccess getInstance(Context context){
        if (instance == null){
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    public void open(){
        this.db = openHelper.getWritableDatabase();
    }
    public void close(){
        if(db!=null){
            this.db.close();
        }
    }

    public boolean palletNoExists(String palletNo){
        String query = "select count(palleteNo) from scanOrder where palleteNo = '"+palletNo+"';";
        c = db.rawQuery(query, new String[]{});
        c.moveToNext();
        String data=(c.getString(0));
        if(data.equals("0")){
            return false;
        }
        return true;
    }
    public boolean deliveryNoExists(String deliveryNo){
        String query = "select count(deliveryNo) from scanOrder where deliveryNo = '"+deliveryNo+"';";
        c = db.rawQuery(query, new String[]{});
        c.moveToNext();
        String data=(c.getString(0));
        if(data.equals("0")) {
            return false;
        }
        return true;
    }
    public boolean cartonNoExists(String cartonNo){
        String query = "select count(cartonNo) from scanOrder where cartonNo = '"+cartonNo+"';";
        c = db.rawQuery(query, new String[]{});
        c.moveToNext();
        String data=(c.getString(0));
        if(data.equals("0")){
            return false;
        }
        return true;
    }

    public String[] getPalletNos(){

        int len = buffer4.length;
        int l = getUniquePallet();
        System.out.println("------Number of uniques----" + l);
        pallet = new String[l+1];
        pallet[0] = "Select";
        int index = 1;

        for (int i = 0; i < len; i++)
        {
            int flag = 0;
            for (int j = 0; j < i; j++){
                if(buffer4[i] == null || buffer4[j] == null){
                    continue;
                }
                if (buffer4[i].equals(buffer4[j])){
                    flag = 1;
                    break;
                }
            }
            if (flag == 0 && index < l+1){
                pallet[index] = buffer4[i];
                index++;
            }
        }
        return pallet;
    }

        public String[] getDeliNos(){
            String[] res = new String[buffer1.length];
            for(int i=0;i<buffer1.length;i++) {

                res[i]= buffer1[i] + "-" + buffer2[i];
            }
            int len = res.length;
            int l = getUniqueDelivery_Line();
            System.out.println("------Number of uniques----" + l);
            delivery_line = new String[l+1];
            delivery_line[0] = "Select";
            int index = 1;

            for (int i = 0; i < len; i++)
            {
                int flag = 0;
                for (int j = 0; j < i; j++){
                    if (res[i].equals(res[j])){
                        flag = 1;
                        break;
                    }
                }
                if (flag == 0 && index < l+1){
                    delivery_line[index] = res[i];
                    index++;
                }
            }
            return delivery_line;
        }

    public int getUniqueDelivery_Line(){
//         COMEBACK TO THIS _____________________________________________________
//        this.delivery_line[0] = this.deli + "-" + this.line;
        String data1, data2;
        int i = 0;
        this.delivery_line = new String[100];
        c = db.rawQuery("select DISTINCT * from (select deliveryNo, lineItemNo from scanOrder where deliveryNo is not Null) ;", new String[]{});
        while(c.moveToNext()){
            data1 = (c.getString(0));
            data2 = (c.getString(1));
            this.delivery_line[i] = data1 + "-" + data2;
            i += 1;
        }
        return i;
    }

    public int getUniquePallet(){
//         COMEBACK TO THIS _____________________________________________________
//         this.pallet[0] = this.pall;
        String data;
        this.pallet = new String[100];
        c = db.rawQuery("select count(DISTINCT palleteNo) from scanOrder;", new String[]{});
        c.moveToNext();
        int i = Integer.parseInt(c.getString(0));
        return i;
    }

    public String getTotalQty(){
        c = db.rawQuery("select count(*) from scanOrder ", new String[]{});
        c.moveToNext();
        String data=(c.getString(0));
        return data;
    }
    public String getTotalGross(){
        c = db.rawQuery("select sum(grossWt) from scanOrder ", new String[]{});
        c.moveToNext();
        String data=(c.getString(0));
        return data;
    }
    public String getTotalNet(){
        c = db.rawQuery("select sum(netWt) from scanOrder ", new String[]{});
        c.moveToNext();
        String data=(c.getString(0));
        return data;
    }

    public String getReqPrd(String pall){
        //System.out.println("---------------------------"+cno);
        String clause = "palleteNo ='" + pall + "';";
        String query = "select proReqNetWt from scanOrder where " + clause;
//        System.out.println("--------------"+query);

        c = db.rawQuery(query , new String[]{});
        c.moveToNext();
        /////////////////////////////////////////////////////////////////////////////////
        /// TRY CATCH BECAUSE DELI NO & PALLET NO if not match
        String data=(c.getString(0));
        this.reqprd=data;
        System.out.println("----------THISS-----------"+data);
        if(data == null) {
            return "null";
        }
        return data;
    }
    public String getTotPrd(String pall, String line ,String deli){
        String clause = "palleteNo ='" + pall + "' and lineItemNo = '" +line + "' and deliveryNo='" + deli +"';";
        String query = "select sum(netWt) from scanOrder where scanFlag = '1' and " + clause;
        c = db.rawQuery(query, new String[]{});
        c.moveToNext();
        String data=(c.getString(0));
        this.scanprd = "0";
        if(data != null){
            this.scanprd=data;
        }
        return data;
    }


    public Double getExcessNet(){
        double i = Double.parseDouble(scanprd) - Double.parseDouble(reqprd);
        if(i<=0 ){
            i=0;
        }
        return i;
    }
    public Double getBalNet(){
        double i = Double.parseDouble(reqprd) - Double.parseDouble(scanprd);
        if(i < 0.0){
            return 0.0;
        }
        return i;
    }
    public String getTotalCarton(String deli, String pall, String line){
        // System.out.println("---------------------------"+deli+pall+line);
        String query = "select count(*) from scanOrder where deliveryNo='"+deli+"' and lineItemNo='"+line+"' and palleteNo='"+pall+"';";
        // Log.i("----------------------",query);
        c = db.rawQuery(query, new String[]{});
        c.moveToNext();
        String data=(c.getString(0));
        return data;
    }
    public String getScanCarton(String deli, String pall, String line){
        String query = "select count(*) from scanOrder where deliveryNo='"+deli+"' and lineItemNo='"+line+"' and palleteNo='"+pall+"' and scanFlag='1';";
        c = db.rawQuery(query, new String[]{});
        c.moveToNext();
        String data=(c.getString(0));
        return data;
    }

    public int updateFlag1(String cno, String deli) {
        String quer;
        String data;

        quer = "select scanflag from scanOrder where cartonNo='"+cno+"' and deliveryNo='" + deli + "';";
        c = db.rawQuery(quer , new String[]{});
        c.moveToNext();
        data=(c.getString(0));
        System.out.println("----------BeforeUpdateFlag1------"+data);
        if(data.equals("1")){
            return -1;
        }
        try {
        String query = "update scanOrder set scanFlag='1' where cartonNo='"+cno+"';";
        c = db.rawQuery(query , new String[]{});
        c.moveToNext();
//        data=(c.getString(0));
            } catch (Exception e){

            System.out.println("Carton already scanned");
            String query = "select cartonNo, scanFlag from scanOrder where cartonNo='"+cno+"';";
            // Log.i("----------------------",query);
            String data1, data2;
            c = db.rawQuery(query, new String[]{});
            c.moveToNext();
            data1 = (c.getString(0));


//            while(c.moveToNext()) {
//                data1 = (c.getString(0));
//                data2 = (c.getString(1));
//                System.out.println(data1 + "----------" + data2);
//
//                System.out.println("Carton already scanned");
//
//                }
            return -1;
            }

        quer = "select scanflag from scanOrder where cartonNo='"+cno+"';";
        c = db.rawQuery(quer , new String[]{});
        c.moveToNext();
        data=(c.getString(0));
        System.out.println("-------scanflag should be 1 here-------"+data);

        return 1;
    }

    public int updateFlag0(String cno, String deli) {
        String dat;
        String quer = "select scanflag from scanOrder where cartonNo='"+cno+"' and deliveryNo='"+deli+"' ;";
        c = db.rawQuery(quer , new String[]{});
        c.moveToNext();
        dat=(c.getString(0));
        System.out.println("---BEfore UPDATEFLAG0----"+dat);


        String query = "update scanOrder set scanFlag='0' where cartonNo='"+cno+"';";
        try {
//              db.rawQuery(query, new String[]{});
            c = db.rawQuery(query, new String[]{});
            c.moveToNext();
//            String data = (c.getString(0));
//            System.out.println("----Printing data-------"+data);
        } catch (Exception e){
            System.out.println("------------Carton was not scanned");
            return -1;
        }
        quer = "select scanflag from scanOrder where cartonNo='"+cno+"';";
        c = db.rawQuery(quer , new String[]{});
        c.moveToNext();
        dat=(c.getString(0));
        System.out.println("--- After UPDATEFLAG0----"+dat);
        return 1;
    }



//        boolean palletNoExists(String pall){
//        for(int i = 0; i < buffer4.length; i++){
//            if(buffer4[i] != null){
//                if(buffer4[i].equals(pall)){
//                    return true;
//                }
//            }
//        }
//        return false;
//    }


    public int getData(int pos){

        String query;
        if(pos == 0){
            query = "select * from scanOrder WHERE scanFlag = '0' ";
        } else {
            query = "select * from scanOrder WHERE scanFlag = '1' ";
        }

        c = db.rawQuery(query, new String[]{});
        // StringBuffer buffer = new StringBuffer();
        String[] temp = new String[10];
        int i = 0;
        this.buffer1 = new String[600];
        this.buffer2 = new String[600];
        this.buffer2 = new String[600];
        this.buffer3 = new String[600];
        this.buffer4 = new String[600];
        this.buffer5 = new String[600];
        this.buffer6 = new String[600];
        this.buffer7 = new String[600];
        this.buffer8 = new String[600];

        while(c.moveToNext()){
            temp[0] = c.getString(0);
            temp[1] = c.getString(1);
            temp[2] = c.getString(2);
            temp[3] = c.getString(3);
            temp[4] = c.getString(4);
            temp[5] = c.getString(5);
            temp[6] = c.getString(6);
            temp[7] = c.getString(7);
            // buffer.append(" " + temp);
            this.buffer1[i] = temp[0];
            this.buffer2[i] = temp[1];
            this.buffer3[i] = temp[2];
            this.buffer4[i] = temp[3];
            this.buffer5[i] = temp[4];
            this.buffer6[i] = temp[5];
            this.buffer7[i] = temp[6];
            this.buffer8[i] = temp[7];

            i += 1;
        }
        return i;
        // return buffer.toString();
    }
}
