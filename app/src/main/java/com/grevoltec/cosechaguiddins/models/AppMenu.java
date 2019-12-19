package com.grevoltec.cosechaguiddins.models;

import java.util.ArrayList;
import java.util.List;

public class AppMenu {

    private int id;
    private String nombre;
    private int icon;
    private List<AppMenu> subMenus;

    public AppMenu() {
        subMenus = new ArrayList<>();
    }

    public AppMenu(int id, String nombre, int icon, List<AppMenu> subMenus) {
        this.id = id;
        this.nombre = nombre;
        this.icon = icon;
        this.subMenus = subMenus;
    }

    public int getId() {
        return id;
    }

    public AppMenu setId(int id) {
        this.id = id;
        return this;
    }

    public String getNombre() {
        return nombre;
    }

    public AppMenu setNombre(String nombre) {
        this.nombre = nombre;
        return  this;
    }

    public int getIcon() {
        return icon;
    }

    public AppMenu setIcon(int icon) {
        this.icon = icon;
        return this;
    }

    public List<AppMenu> getSubMenus() {
        return subMenus;
    }

    public boolean hasSubMenus() {
        return subMenus == null ? false : subMenus.size() > 0;
    }

    public AppMenu setSubMenus(List<AppMenu> subMenus) {
        this.subMenus = subMenus;
        return this;
    }

    public AppMenu addSubMenu(AppMenu subMenu){
        subMenus.add(subMenu);
        return this;
    }

}
