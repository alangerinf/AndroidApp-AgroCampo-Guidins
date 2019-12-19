package com.grevoltec.cosechaguiddins.views.secure.fragments.core;

public class AbsDropDownItem<T> {

    public String value;
    public T item;

    public AbsDropDownItem(String value) {
        this.value = value;
    }

    public AbsDropDownItem(String value, T item) {
        this.value = value;
        this.item = item;
    }

    @Override
    public String toString() {
        return value;
    }

    public T getItem() {
        return item;
    }

}
