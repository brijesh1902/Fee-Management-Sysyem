package com.bpal.feesmanagement.DBModel;

public class Option {

    private String Year;
    private boolean expanded;

    public Option(){}

    public Option(String Year){
        this.Year = Year;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }
}
