package com.bpal.feesmanagement.DBModel;

public class DBFee {

    private String Stream, Course, CurrentYear, TotalFee, PaidFee, RemainingFee, PaidDate;

    public DBFee(){}

    public DBFee(String Stream, String Course, String CurrentYear, String TotalFee, String PaidFee, String RemainingFee, String PaidDate){
        this.Stream=Stream;
        this.Course=Course;
        this.CurrentYear=CurrentYear;
        this.TotalFee=TotalFee;
        this.PaidFee=PaidFee;
        this.RemainingFee=RemainingFee;
        this.PaidDate=PaidDate;
    }

    public String getStream() {
        return Stream;
    }

    public void setStream(String stream) {
        Stream = stream;
    }

    public String getCourse() {
        return Course;
    }

    public void setCourse(String course) {
        Course = course;
    }

    public String getCurrentYear() {
        return CurrentYear;
    }

    public void setCurrentYear(String currentYear) {
        CurrentYear = currentYear;
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
