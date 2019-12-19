package com.grevoltec.cosechaguiddins.entities;

import com.grevoltec.cosechaguiddins.entities.core.AbsEntity;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "camiones")
public class CamionEntity  extends AbsEntity {

    @DatabaseField(id = true, columnName = ID)
    private int idCamion;
    @DatabaseField(columnName = "codigo")
    private String codigoCamion;
    @DatabaseField(columnName = "placa")
    private String placaCamion;
    @DatabaseField(columnName = "qr")
    private String codigoQRCamion;

    public CamionEntity() {
    }

    public CamionEntity(int idCamion, String codigoCamion, String placaCamion, String codigoQRCamion) {
        this.idCamion = idCamion;
        this.codigoCamion = codigoCamion;
        this.placaCamion = placaCamion;
        this.codigoQRCamion = codigoQRCamion;
    }

    public int getIdCamion() {
        return idCamion;
    }

    public void setIdCamion(int idCamion) {
        this.idCamion = idCamion;
    }

    public String getCodigoCamion() {
        return codigoCamion;
    }

    public void setCodigoCamion(String codigoCamion) {
        this.codigoCamion = codigoCamion;
    }

    public String getPlacaCamion() {
        return placaCamion;
    }

    public void setPlacaCamion(String placaCamion) {
        this.placaCamion = placaCamion;
    }

    public String getCodigoQRCamion() {
        return codigoQRCamion;
    }

    public void setCodigoQRCamion(String codigoQRCamion) {
        this.codigoQRCamion = codigoQRCamion;
    }

}