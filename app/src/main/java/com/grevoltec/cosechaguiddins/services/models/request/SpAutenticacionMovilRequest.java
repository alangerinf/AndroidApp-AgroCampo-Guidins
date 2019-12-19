package com.grevoltec.cosechaguiddins.services.models.request;

import com.grevoltec.cosechaguiddins.services.models.AbsModel;

public class SpAutenticacionMovilRequest extends AbsModel {

    private String user;
    private String clave;
    private int idcult;
    private int perf;

    public SpAutenticacionMovilRequest() {
    }

    public SpAutenticacionMovilRequest(String user, String clave, int idcult, int perf) {
        this.user = user;
        this.clave = clave;
        this.idcult = idcult;
        this.perf = perf;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public int getIdcult() {
        return idcult;
    }

    public void setIdcult(int idcult) {
        this.idcult = idcult;
    }

    public int getPerf() {
        return perf;
    }

    public void setPerf(int perf) {
        this.perf = perf;
    }

}
