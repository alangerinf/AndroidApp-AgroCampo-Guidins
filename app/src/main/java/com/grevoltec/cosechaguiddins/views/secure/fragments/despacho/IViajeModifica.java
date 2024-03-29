package com.grevoltec.cosechaguiddins.views.secure.fragments.despacho;

import android.widget.Button;

import com.grevoltec.cosechaguiddins.util.AppException;

public interface IViajeModifica {

    void goToFirstStep();
    void goToSecondStep(String qrViajeSelected);
    void readQR(String qr) throws AppException;
    void validateQR(String qr) throws AppException;
    Button getActionButton();
    String getQrCamionViaje();
    void setListener(IViajeModificaChangeListener listener);

    interface IViajeModificaChangeListener {
        void onClickActionButton();
    }

}
