package com.example.expenses.models;

public class Expenses {

    public String item;
    public String price;
    public Integer day;
    public Integer month;
    public Integer year;
    public Integer id;

    public Expenses(String i, String p, Integer d, Integer m, Integer y){
        this.item = i;
        this.price = p;
        this.day = d;
        this.month = m;
        this.year = y;
    }

    public Expenses(String i, String p, Integer d, Integer m, Integer y, Integer id){
        this.item = i;
        this.price = p;
        this.day = d;
        this.month = m;
        this.year = y;
        this.id = id;

    }

    public Expenses(Integer d, String p,Integer m, Integer y){
        this.price = p;
        this.day = d;
        this.month = m;
        this.year = y;
    }

    public void setItem(String i ){
        this.item = i;
    }

    public String getItem() {
        return item;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getDay() {
        return day;
    }

    public Integer getMonth() {
        return month;
    }

    public Integer getYear() {
        return year;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
