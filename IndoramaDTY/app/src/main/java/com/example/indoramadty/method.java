package com.example.indoramadty;

public class method {
    String deliveryNo;
    String cartonNO;
    String lineItemNo;
    String palleteNo;
    String netWt;
    String grossWt;
    String proReqNetWt;
    String scanFlag;

    public method(String deliveryNo, String lineItemNo, String cartonNO, String palleteNo, String netWt, String grossWt, String proReqNetWt, String scanFlag) {
        this.deliveryNo = deliveryNo;
        this.cartonNO = cartonNO;
        this.lineItemNo = lineItemNo;
        this.palleteNo = palleteNo;
        this.netWt = netWt;
        this.grossWt = grossWt;
        this.proReqNetWt = proReqNetWt;
        this.scanFlag = scanFlag;
    }

    public String getPalleteNo() {
        return palleteNo;
    }

    public String getNetWt() {
        return netWt;
    }

    public String getGrossWt() {
        return grossWt;
    }

    public String getProReqNetWt() {
        return proReqNetWt;
    }

    public String getScanFlag() {
        return scanFlag;
    }

    public String getDeliveryNo() {
        return deliveryNo;
    }

    public String getCartonNO() {
        return cartonNO;
    }

    public String getLineItemNo() {
        return lineItemNo;
    }
}
