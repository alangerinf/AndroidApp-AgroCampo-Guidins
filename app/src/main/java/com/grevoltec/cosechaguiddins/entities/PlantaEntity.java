package com.grevoltec.cosechaguiddins.entities;

import com.grevoltec.cosechaguiddins.entities.core.AbsEntity;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "plantas")
public class PlantaEntity extends AbsEntity {

    @DatabaseField(id = true, columnName = ID)
    private int idPlanta;
    @DatabaseField(columnName = "codigo")
    private String codigoPlanta;
    @DatabaseField(columnName = "nombre")
    private String nombrePlanta;
    @DatabaseField(columnName = "idEmpresa")
    private int idEmpresa;

    public PlantaEntity() {
    }

    public PlantaEntity(int idPlanta, String codigoPlanta, String nombrePlanta, int idEmpresa) {
        this.idPlanta = idPlanta;
        this.codigoPlanta = codigoPlanta;
        this.nombrePlanta = nombrePlanta;
        this.idEmpresa = idEmpresa;
    }

    public int getIdPlanta() {
        return idPlanta;
    }

    public void setIdPlanta(int idPlanta) {
        this.idPlanta = idPlanta;
    }

    public String getCodigoPlanta() {
        return codigoPlanta;
    }

    public void setCodigoPlanta(String codigoPlanta) {
        this.codigoPlanta = codigoPlanta;
    }

    public String getNombrePlanta() {
        return nombrePlanta;
    }

    public void setNombrePlanta(String nombrePlanta) {
        this.nombrePlanta = nombrePlanta;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    @Override
    public String toString() {
        return "PlantaEntity{" +
                "idPlanta=" + idPlanta +
                ", codigoPlanta='" + codigoPlanta + '\'' +
                ", nombrePlanta='" + nombrePlanta + '\'' +
                ", idEmpresa=" + idEmpresa +
                '}';
    }
}
