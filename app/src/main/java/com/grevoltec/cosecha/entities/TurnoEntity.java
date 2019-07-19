package com.grevoltec.cosecha.entities;

import com.grevoltec.cosecha.entities.core.AbsEntity;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "turnos")
public class TurnoEntity extends AbsEntity {

    @DatabaseField(id = true, columnName = ID)
    private int idTurno;
    @DatabaseField(columnName = "turno_codigo")
    private String codigoTurno;
    @DatabaseField(columnName = "turno_nombre")
    private String nombreTurno;
    @DatabaseField(columnName = "turno_qr")
    private String codigoQRTurno;
    @DatabaseField(columnName = "idEmpresa")
    private int idEmpresa;
    @DatabaseField(columnName = "idCultivo")
    private int idCultivo;
    @DatabaseField(columnName = "fundo_id")
    private String idFundo;

    public TurnoEntity() {
    }

    public TurnoEntity(int idTurno, String codigoTurno, String nombreTurno, String codigoQRTurno, int idEmpresa, int idCultivo, String idFundo) {
        this.idTurno = idTurno;
        this.codigoTurno = codigoTurno;
        this.nombreTurno = nombreTurno;
        this.codigoQRTurno = codigoQRTurno;
        this.idEmpresa = idEmpresa;
        this.idCultivo = idCultivo;
        this.idFundo = idFundo;
    }

    public int getIdTurno() {
        return idTurno;
    }

    public void setIdTurno(int idTurno) {
        this.idTurno = idTurno;
    }

    public String getCodigoTurno() {
        return codigoTurno;
    }

    public void setCodigoTurno(String codigoTurno) {
        this.codigoTurno = codigoTurno;
    }

    public String getNombreTurno() {
        return nombreTurno;
    }

    public void setNombreTurno(String nombreTurno) {
        this.nombreTurno = nombreTurno;
    }

    public String getCodigoQRTurno() {
        return codigoQRTurno;
    }

    public void setCodigoQRTurno(String codigoQRTurno) {
        this.codigoQRTurno = codigoQRTurno;
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

    public String getIdFundo() {
        return idFundo;
    }

    public void setIdFundo(String idFundo) {
        this.idFundo = idFundo;
    }

    @Override
    public String toString() {
        return "TurnoEntity{" +
                "idTurno=" + idTurno +
                ", codigoTurno='" + codigoTurno + '\'' +
                ", nombreTurno='" + nombreTurno + '\'' +
                ", codigoQRTurno='" + codigoQRTurno + '\'' +
                ", idEmpresa=" + idEmpresa +
                ", idCultivo=" + idCultivo +
                ", idFundo='" + idFundo + '\'' +
                '}';
    }
}
