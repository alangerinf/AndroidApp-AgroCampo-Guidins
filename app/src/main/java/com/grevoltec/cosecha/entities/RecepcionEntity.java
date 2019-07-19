package com.grevoltec.cosecha.entities;

import com.grevoltec.cosecha.entities.core.AbsRegEntity;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "recepciones")
public class RecepcionEntity extends AbsRegEntity {

    @DatabaseField(columnName = "planta_id")
    private int idplanta;
    @DatabaseField(columnName = "recep_fecha")
    private String fecharecep;
    @DatabaseField(columnName = "user_id")
    private int iduser;
    @DatabaseField(columnName = "pallet_qr")
    private String qrpallet;
    @DatabaseField(columnName = "pallet_hora")
    private String horapallet;
    @DatabaseField(columnName = "equipo_imei")
    private String imeiequipo;
    @DatabaseField(columnName = "equipo_nro")
    private String nroequipo;
    @DatabaseField(columnName = "pallet_peso")
    private double pesopallet;

    public RecepcionEntity() {
    }

    public RecepcionEntity(long poid, String synCodigo, int idplanta, String fecharecep, int iduser, String qrpallet, String horapallet, String imeiequipo, String nroequipo, double pesopallet) {
        super(poid, synCodigo);
        this.idplanta = idplanta;
        this.fecharecep = fecharecep;
        this.iduser = iduser;
        this.qrpallet = qrpallet;
        this.horapallet = horapallet;
        this.imeiequipo = imeiequipo;
        this.nroequipo = nroequipo;
        this.pesopallet = pesopallet;
    }

    public int getIdplanta() {
        return idplanta;
    }

    public void setIdplanta(int idplanta) {
        this.idplanta = idplanta;
    }

    public String getFecharecep() {
        return fecharecep;
    }

    public void setFecharecep(String fecharecep) {
        this.fecharecep = fecharecep;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public String getQrpallet() {
        return qrpallet;
    }

    public void setQrpallet(String qrpallet) {
        this.qrpallet = qrpallet;
    }

    public String getHorapallet() {
        return horapallet;
    }

    public void setHorapallet(String horapallet) {
        this.horapallet = horapallet;
    }

    public String getImeiequipo() {
        return imeiequipo;
    }

    public void setImeiequipo(String imeiequipo) {
        this.imeiequipo = imeiequipo;
    }

    public String getNroequipo() {
        return nroequipo;
    }

    public void setNroequipo(String nroequipo) {
        this.nroequipo = nroequipo;
    }

    public double getPesopallet() {
        return pesopallet;
    }

    public void setPesopallet(double pesopallet) {
        this.pesopallet = pesopallet;
    }

    @Override
    public String toString() {
        return "RecepcionEntity{" +
                "idplanta=" + idplanta +
                ", fecharecep='" + fecharecep + '\'' +
                ", iduser=" + iduser +
                ", qrpallet='" + qrpallet + '\'' +
                ", horapallet='" + horapallet + '\'' +
                ", imeiequipo='" + imeiequipo + '\'' +
                ", nroequipo='" + nroequipo + '\'' +
                ", pesopallet=" + pesopallet +
                ", poid=" + poid +
                ", synCodigo='" + synCodigo + '\'' +
                '}';
    }
}
