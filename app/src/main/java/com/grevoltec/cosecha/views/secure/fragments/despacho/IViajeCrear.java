package com.grevoltec.cosecha.views.secure.fragments.despacho;

import android.widget.Button;

import com.grevoltec.cosecha.entities.ViajeEntity;
import com.grevoltec.cosecha.util.AppException;

import java.util.List;

public interface IViajeCrear {

    void goToFirstStep();
    void goToSecondStep(String qrCamionSelected);
    void readQR(String qr) throws AppException;
    void validateQRCaminion(String qr) throws AppException;
    void validateQRPallete(String qr) throws AppException;
    Button getActionButton();
    List<ViajeEntity> getViajes();
    String getQrHeadPallet();
    void setOnViajesListener(IViajePalletChangeListener listener);
    IViajePalletChangeListener getOnViajesListener();

    interface IViajePalletChangeListener {
        void onViajeChange();
        void onClickActionButton();
        String deletePallet(String qr) throws AppException;
    }

}