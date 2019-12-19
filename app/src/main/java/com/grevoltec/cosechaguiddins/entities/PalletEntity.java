package com.grevoltec.cosechaguiddins.entities;

import com.grevoltec.cosechaguiddins.entities.core.AbsRegEntity;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "pallets")
public class PalletEntity extends AbsRegEntity {

    @DatabaseField(columnName = "user_id")
    private int iduser;
    @DatabaseField(columnName = "pallet_qr")
    private String qrpallet;
    @DatabaseField(columnName = "pallet_peso")
    private double pesopallet;
    @DatabaseField(columnName = "pallet_hora")
    private String horapallet;
    @DatabaseField(columnName = "pallet_comp")
    private int palletcomp;
    @DatabaseField(columnName = "equipo_imei")
    private String imeiequipo;
    @DatabaseField(columnName = "equipo_nro")
    private String nroequipo;
    @DatabaseField(columnName = "jaba_qr")
    private String qrjaba;
    @DatabaseField(columnName = "jaba_hora")
    private String horajaba;

    private CosechaEntity cosecha;

    public PalletEntity() {
    }

    public PalletEntity(long poid, String synCodigo, int iduser, String qrpallet, String horapallet, int palletcomp, String imeiequipo, String nroequipo, String qrjaba, String horajaba,double pesoPallet) {
        super(poid, synCodigo);
        this.iduser = iduser;
        this.qrpallet = qrpallet;
        this.horapallet = horapallet;
        this.palletcomp = palletcomp;
        this.imeiequipo = imeiequipo;
        this.nroequipo = nroequipo;
        this.qrjaba = qrjaba;
        this.horajaba = horajaba;
        this.pesopallet = pesoPallet;
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

    public int getPalletcomp() {
        return palletcomp;
    }

    public void setPalletcomp(int palletcomp) {
        this.palletcomp = palletcomp;
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

    public String getQrjaba() {
        return qrjaba;
    }

    public void setQrjaba(String qrjaba) {
        this.qrjaba = qrjaba;
    }

    public String getHorajaba() {
        return horajaba;
    }

    public void setHorajaba(String horajaba) {
        this.horajaba = horajaba;
    }

    public CosechaEntity getCosecha() {
        return cosecha;
    }

    public void setCosecha(CosechaEntity cosecha) {
        this.cosecha = cosecha;
    }

    public double getPesopallet() {
        return pesopallet;
    }

    public void setPesopallet(double pesopallet) {
        this.pesopallet = pesopallet;
    }

    @Override
    public String toString() {
        return "PalletEntity{" +
                "iduser=" + iduser +
                ", qrpallet='" + qrpallet + '\'' +
                ", horapallet='" + horapallet + '\'' +
                ", palletcomp=" + palletcomp +
                ", imeiequipo='" + imeiequipo + '\'' +
                ", nroequipo='" + nroequipo + '\'' +
                ", qrjaba='" + qrjaba + '\'' +
                ", horajaba='" + horajaba + '\'' +
                ", cosecha=" + cosecha +
                ", poid=" + poid +
                ", synCodigo='" + synCodigo + '\'' +
                '}';
    }
}
