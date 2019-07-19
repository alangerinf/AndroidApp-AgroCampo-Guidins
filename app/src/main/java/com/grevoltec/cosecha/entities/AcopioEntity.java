package com.grevoltec.cosecha.entities;

import com.grevoltec.cosecha.entities.core.AbsEntity;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "acopios")
public class AcopioEntity extends AbsEntity {

    @DatabaseField(id = true, columnName = ID)
    private int idAcopio;
    @DatabaseField(columnName = "codigo")
    private String codigoAcopio;
    @DatabaseField(columnName = "nombre")
    private String nombreAcopio;
    @DatabaseField(columnName = "idEmpresa")
    private int idEmpresa;

    public AcopioEntity() {
    }

    public AcopioEntity(int idAcopio, String codigoAcopio, String nombreAcopio, int idEmpresa) {
        this.idAcopio = idAcopio;
        this.codigoAcopio = codigoAcopio;
        this.nombreAcopio = nombreAcopio;
        this.idEmpresa = idEmpresa;
    }

    public int getIdAcopio() {
        return idAcopio;
    }

    public void setIdAcopio(int idAcopio) {
        this.idAcopio = idAcopio;
    }

    public String getCodigoAcopio() {
        return codigoAcopio;
    }

    public void setCodigoAcopio(String codigoAcopio) {
        this.codigoAcopio = codigoAcopio;
    }

    public String getNombreAcopio() {
        return nombreAcopio;
    }

    public void setNombreAcopio(String nombreAcopio) {
        this.nombreAcopio = nombreAcopio;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    @Override
    public String toString() {
        return "AcopioEntity{" +
                "idAcopio=" + idAcopio +
                ", codigoAcopio='" + codigoAcopio + '\'' +
                ", nombreAcopio='" + nombreAcopio + '\'' +
                ", idEmpresa=" + idEmpresa +
                '}';
    }
}
