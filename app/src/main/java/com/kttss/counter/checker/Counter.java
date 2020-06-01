package com.kttss.counter.checker;

import java.util.Date;



public class Counter {

    private int id;
    private String name;
    private Date date;
    private double lap;
    private double value;

    public Counter() {
    }

    public Counter(int id, String name, Date date, double lap, double value) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.lap = lap;
        this.value = value;
    }

    public Counter(String name, Date date, double lap, double value) {
        this.name = name;
        this.date = date;
        this.lap = lap;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }


    public void setDate(Date date) {
        this.date = date;
    }

    public double getLap() {
        return lap;
    }

    public void setLap(double lap) {
        this.lap = lap;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
