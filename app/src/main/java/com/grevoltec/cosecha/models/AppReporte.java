package com.grevoltec.cosecha.models;

public class AppReporte {

    protected String orden;
    protected String group;
    protected String count;

    public AppReporte() {
    }

    public AppReporte(String orden, String group, String count) {
        this.orden = orden;
        this.group = group;
        this.count = count;
    }

    public String getOrden() {
        return orden;
    }

    public void setOrden(String orden) {
        this.orden = orden;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
