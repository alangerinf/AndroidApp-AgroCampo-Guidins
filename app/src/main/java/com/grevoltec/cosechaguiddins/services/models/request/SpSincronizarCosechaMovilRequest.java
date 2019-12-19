package com.grevoltec.cosechaguiddins.services.models.request;

import com.grevoltec.cosechaguiddins.services.models.AbsModel;

public class SpSincronizarCosechaMovilRequest extends AbsModel {

    private int idacc;
    private int idtur;
    private int iduser;
    private String codigoqr;
    private String horaenvase;
    private String imeiequipo;
    private String nroequipo;
    private int envaseobs;
    private int envaseper;

    public SpSincronizarCosechaMovilRequest() {
    }

    public SpSincronizarCosechaMovilRequest(int idacc, int idtur, int iduser, String codigoqr, String horaenvase, String imeiequipo, String nroequipo, int envaseobs, int envaseper) {
        this.idacc = idacc;
        this.idtur = idtur;
        this.iduser = iduser;
        this.codigoqr = codigoqr;
        this.horaenvase = horaenvase;
        this.imeiequipo = imeiequipo;
        this.nroequipo = nroequipo;
        this.envaseobs = envaseobs;
        this.envaseper = envaseper;
    }

    public int getIdacc() {
        return idacc;
    }

    public void setIdacc(int idacc) {
        this.idacc = idacc;
    }

    public int getIdtur() {
        return idtur;
    }

    public void setIdtur(int idtur) {
        this.idtur = idtur;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public String getCodigoqr() {
        return codigoqr;
    }

    public void setCodigoqr(String codigoqr) {
        this.codigoqr = codigoqr;
    }

    public String getHoraenvase() {
        return horaenvase;
    }

    public void setHoraenvase(String horaenvase) {
        this.horaenvase = horaenvase;
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

    public int getEnvaseobs() {
        return envaseobs;
    }

    public void setEnvaseobs(int envaseobs) {
        this.envaseobs = envaseobs;
    }

    public int getEnvaseper() {
        return envaseper;
    }

    public void setEnvaseper(int envaseper) {
        this.envaseper = envaseper;
    }
}
