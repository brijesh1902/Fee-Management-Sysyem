package com.bpal.feesmanagement.DBModel;

public class DBPaid {

    private String TotalFee, PaidFee, RemainingFee, PaidDate, currentyear;

    public DBPaid(){}

    public DBPaid(String TotalFee, String PaidFee, String RemainingFee, String PaidDate, String currentyear){
        this.TotalFee=TotalFee;
        this.PaidFee=PaidFee;
        this.RemainingFee=RemainingFee;
        this.PaidDate=PaidDate;
        this.currentyear=currentyear;
    }

    public String getCurrentyear() {
        return currentyear;
    }

    public void setCurrentyear(String currentyear) {
        this.currentyear = currentyear;
    }

    public String getTotalFee() {
        return TotalFee;
    }

    public void setTotalFee(String totalFee) {
        TotalFee = totalFee;
    }

    public String getPaidFee() {
        return PaidFee;
    }

    public void setPaidFee(String paidFee) {
        PaidFee = paidFee;
    }

    public String getRemainingFee() {
        return RemainingFee;
    }

    public void setRemainingFee(String remainingFee) {
        RemainingFee = remainingFee;
    }

    public String getPaidDate() {
        return PaidDate;
    }

    public void setPaidDate(String paidDate) {
        PaidDate = paidDate;
    }
}
