package com.ttcnpm.group28.weatherapp.huylinh;



public class SpinnerItem extends Object {
    private int icon;
    private String value;

    public SpinnerItem(int icon, String value) {
        this.icon = icon;
        this.value = value;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}