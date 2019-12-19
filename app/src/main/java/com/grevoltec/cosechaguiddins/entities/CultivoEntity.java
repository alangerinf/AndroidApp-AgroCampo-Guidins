package com.grevoltec.cosechaguiddins.entities;

import com.grevoltec.cosechaguiddins.entities.core.AbsEntity;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "cultivos")
public class CultivoEntity extends AbsEntity {

    @DatabaseField(id = true, columnName = ID)
    private int idCultivo;
    @DatabaseField(columnName = "codigo")
    private String codigoCultivo;
    @DatabaseField(columnName = "nombre")
    private String nombreCultivo;

    public CultivoEntity() {
    }

    public CultivoEntity(int idCultivo, String codigoCultivo, String nombreCultivo) {
        this.idCultivo = idCultivo;
        this.codigoCultivo = codigoCultivo;
        this.nombreCultivo = nombreCultivo;
    }

    public int getIdCultivo() {
        return idCultivo;
    }

    public void setIdCultivo(int idCultivo) {
        this.idCultivo = idCultivo;
    }

    public String getCodigoCultivo() {
        return codigoCultivo;
    }

    public void setCodigoCultivo(String codigoCultivo) {
        this.codigoCultivo = codigoCultivo;
    }

    public String getNombreCultivo() {
        return nombreCultivo;
    }

    public void setNombreCultivo(String nombreCultivo) {
        this.nombreCultivo = nombreCultivo;
    }

    @Override
    public String toString() {
        return "CultivoEntity{" +
                "idCultivo=" + idCultivo +
                ", codigoCultivo='" + codigoCultivo + '\'' +
                ", nombreCultivo='" + nombreCultivo + '\'' +
                '}';
    }
}
