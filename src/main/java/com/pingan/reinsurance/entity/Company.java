package com.pingan.reinsurance.entity;

/**
 * Created by heutqyw on 2017/2/21.
 */
public class Company {
    private String code;
    private String name;
    private long capital;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCapital() {
        return capital;
    }

    public void setCapital(long capital) {
        this.capital = capital;
    }
}
