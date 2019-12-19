package com.grevoltec.cosechaguiddins.entities;

import com.grevoltec.cosechaguiddins.entities.core.AbsRegEntity;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "viajes")
public class ViajeEntity extends AbsRegEntity {

    @DatabaseField(columnName = "user_id")
    private int iduser;
    @DatabaseField(columnName = "camion_qr")
    private String qrcamion;
    @DatabaseField(columnName = "camion_hora")
    private String horacamion;
    @DatabaseField(columnName = "viaje_completo")
    private int viajecompleto;
    @DatabaseField(columnName = "equipo_imei")
    private String imeiequipo;
    @DatabaseField(columnName = "equipo_nro")
    private String numeroequipo;
    @DatabaseField(columnName = "pallet_qr")
    private String qrpallet;
    @DatabaseField(columnName = "pallet_hora")
    private String horapallet;

    private String nroPallets;
    private AcopioEntity acopio;

    public ViajeEntity() {
    }

    public ViajeEntity(long poid, String synCodigo, int iduser, String qrcamion, String horacamion, int viajecompleto, String imeiequipo, String numeroequipo, String qrpallet, String horapallet) {
        super(poid, synCodigo);
        this.iduser = iduser;
        this.qrcamion = qrcamion;
        this.horacamion = horacamion;
        this.viajecompleto = viajecompleto;
        this.imeiequipo = imeiequipo;
        this.numeroequipo = numeroequipo;
        this.qrpallet = qrpallet;
        this.horapallet = horapallet;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public String getQrcamion() {
        return qrcamion;
    }

    public void setQrcamion(String qrcamion) {
        this.qrcamion = qrcamion;
    }

    public String getHoracamion() {
        return horacamion;
    }

    public void setHoracamion(String horacamion) {
        this.horacamion = horacamion;
    }

    public int getViajecompleto() {
        return viajecompleto;
    }

    public void setViajecompleto(int viajecompleto) {
        this.viajecompleto = viajecompleto;
    }

    public String getImeiequipo() {
        return imeiequipo;
    }

    public void setImeiequipo(String imeiequipo) {
        this.imeiequipo = imeiequipo;
    }

    public String getNumeroequipo() {
        return numeroequipo;
    }

    public void setNumeroequipo(String numeroequipo) {
        this.numeroequipo = numeroequipo;
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

    public String getNroPallets() {
        return nroPallets;
    }

    public void setNroPallets(String nroPallets) {
        this.nroPallets = nroPallets;
    }

    public AcopioEntity getAcopio() {
        return acopio;
    }

    public void setAcopio(AcopioEntity acopio) {
        this.acopio = acopio;
    }

    @Override
    public String toString() {
        return "ViajeEntity{" +
                "iduser=" + iduser +
                ", qrcamion='" + qrcamion + '\'' +
                ", horacamion='" + horacamion + '\'' +
                ", viajecompleto=" + viajecompleto +
                ", imeiequipo='" + imeiequipo + '\'' +
                ", numeroequipo='" + numeroequipo + '\'' +
                ", qrpallet='" + qrpallet + '\'' +
                ", horapallet='" + horapallet + '\'' +
                ", nroPallets='" + nroPallets + '\'' +
                ", acopio=" + acopio +
                ", poid=" + poid +
                ", synCodigo='" + synCodigo + '\'' +
                '}';
    }
}