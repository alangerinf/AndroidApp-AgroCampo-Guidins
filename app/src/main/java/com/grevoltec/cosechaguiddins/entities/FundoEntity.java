package com.grevoltec.cosechaguiddins.entities;

import com.grevoltec.cosechaguiddins.entities.core.AbsEntity;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "fundos")
public class FundoEntity extends AbsEntity {

    @DatabaseField(id = true, columnName = ID)
    private int idFundo;
    @DatabaseField(columnName = "codigo")
    private String codigoFundo;
    @DatabaseField(columnName = "nombre")
    private String nombreFundo;
    @DatabaseField(columnName = "idEmpresa")
    private int idEmpresa;
    @DatabaseField(columnName = "idCultivo")
    private int idCultivo;

    public FundoEntity() {
    }

    public FundoEntity(int idFundo, String codigoFundo, String nombreFundo, int idEmpresa, int idCultivo) {
        this.idFundo = idFundo;
        this.codigoFundo = codigoFundo;
        this.nombreFundo = nombreFundo;
        this.idEmpresa = idEmpresa;
        this.idCultivo = idCultivo;
    }

    public int getIdFundo() {
        return idFundo;
    }

    public void setIdFundo(int idFundo) {
        this.idFundo = idFundo;
    }

    public String getCodigoFundo() {
        return codigoFundo;
    }

    public void setCodigoFundo(String codigoFundo) {
        this.codigoFundo = codigoFundo;
    }

    public String getNombreFundo() {
        return nombreFundo;
    }

    public void setNombreFundo(String nombreFundo) {
        this.nombreFundo = nombreFundo;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public int getIdCultivo() {
        return idCultivo;
    }

    public void setIdCultivo(int idCultivo) {
        this.idCultivo = idCultivo;
    }

    @Override
    public String toString() {
        return "FundoEntity{" +
                "idFundo=" + idFundo +
                ", codigoFundo='" + codigoFundo + '\'' +
                ", nombreFundo='" + nombreFundo + '\'' +
                ", idEmpresa=" + idEmpresa +
                ", idCultivo=" + idCultivo +
                '}';
    }
}