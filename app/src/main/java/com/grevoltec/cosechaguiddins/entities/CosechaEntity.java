package com.grevoltec.cosechaguiddins.entities;

import com.grevoltec.cosechaguiddins.entities.core.AbsRegEntity;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "cosechas")
public class CosechaEntity extends AbsRegEntity {

    @DatabaseField(columnName = "emp_id")
    private int idemp;
    @DatabaseField(columnName = "tur_id")
    private int idtur;
    @DatabaseField(columnName = "cantPersonas")
    private int cantPersonas;
    @DatabaseField(columnName = "user_id")
    private int iduser;
    @DatabaseField(columnName = "codigo_qr")
    private String codigoqr;
    @DatabaseField(columnName = "envase_hora")
    private String horaenvase;
    @DatabaseField(columnName = "equipo_imei")
    private String imeiequipo;
    @DatabaseField(columnName = "equipo_nro")
    private String nroequipo;
    @DatabaseField(columnName = "envase_obs")
    private int envaseobs;
    @DatabaseField(columnName = "envase_per")
    private int envaseper;
    /* Calculado de la Lectura del QR */
    @DatabaseField(columnName = "qr_dni")
    private String dni;
    @DatabaseField(columnName = "qr_cosechador")
    private String cosechador;
    @DatabaseField(columnName = "qr_nro_etiqueta")
    private String nroetiqueta;
    @DatabaseField(columnName = "qr_total_etiqueta")
    private String totaletiqueta;
    @DatabaseField(columnName = "qr_tot_etiqueta")
    private int totetiqueta;

    public CosechaEntity(){

    }

    public CosechaEntity(int idemp, int idtur, int iduser, String codigoqr, String horaenvase, String imeiequipo, String nroequipo, int envaseobs, int envaseper, String dni, String cosechador,int cantPersonas) {
        this.idemp = idemp;
        this.idtur = idtur;
        this.iduser = iduser;
        this.codigoqr = codigoqr;
        this.horaenvase = horaenvase;
        this.imeiequipo = imeiequipo;
        this.nroequipo = nroequipo;
        this.envaseobs = envaseobs;
        this.envaseper = envaseper;
        this.dni = dni;
        this.cosechador = cosechador;
        this.cantPersonas=cantPersonas;
    }

    public CosechaEntity(long poid, String synCodigo, int idemp, int idtur, int iduser, String codigoqr, String horaenvase, String imeiequipo, String nroequipo, int envaseobs, int envaseper, String dni, String cosechador, String nroetiqueta, String totaletiqueta, int totetiqueta,int cantPersonas) {
        super(poid, synCodigo);
        this.idemp = idemp;
        this.idtur = idtur;
        this.iduser = iduser;
        this.codigoqr = codigoqr;
        this.horaenvase = horaenvase;
        this.imeiequipo = imeiequipo;
        this.nroequipo = nroequipo;
        this.envaseobs = envaseobs;
        this.envaseper = envaseper;
        this.dni = dni;
        this.cosechador = cosechador;
        this.nroetiqueta = nroetiqueta;
        this.totaletiqueta = totaletiqueta;
        this.totetiqueta = totetiqueta;
        this.cantPersonas=cantPersonas;
    }

    public int getIdemp() {
        return idemp;
    }

    public void setIdemp(int idacc) {
        this.idemp = idacc;
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

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getCosechador() {
        return cosechador;
    }

    public void setCosechador(String cosechador) {
        this.cosechador = cosechador;
    }

    public String getNroetiqueta() {
        return nroetiqueta;
    }

    public void setNroetiqueta(String nroetiqueta) {
        this.nroetiqueta = nroetiqueta;
    }

    public String getTotaletiqueta() {
        return totaletiqueta;
    }

    public void setTotaletiqueta(String totaletiqueta) {
        this.totaletiqueta = totaletiqueta;
    }

    public int getTotetiqueta() {
        return totetiqueta;
    }

    public void setTotetiqueta(int totetiqueta) {
        this.totetiqueta = totetiqueta;
    }


    public int getCantPersonas() {
        return cantPersonas;
    }

    public void setCantPersonas(int cantPersonas) {
        this.cantPersonas = cantPersonas;
    }

    @Override
    public String toString() {
        return "CosechaEntity{" +
                "idemp=" + idemp +
                ", idtur=" + idtur +
                ", iduser=" + iduser +
                ", codigoqr='" + codigoqr + '\'' +
                ", horaenvase='" + horaenvase + '\'' +
                ", imeiequipo='" + imeiequipo + '\'' +
                ", nroequipo='" + nroequipo + '\'' +
                ", envaseobs=" + envaseobs +
                ", envaseper=" + envaseper +
                ", dni='" + dni + '\'' +
                ", cosechador='" + cosechador + '\'' +
                ", nroetiqueta='" + nroetiqueta + '\'' +
                ", totaletiqueta='" + totaletiqueta + '\'' +
                ", totetiqueta=" + totetiqueta +
                ", poid=" + poid +
                ", synCodigo='" + synCodigo + '\'' +
                ", cantPersonas='" + cantPersonas + '\'' +
                '}';
    }
}