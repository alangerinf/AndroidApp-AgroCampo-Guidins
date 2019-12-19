package com.grevoltec.cosechaguiddins.views.secure.fragments.pallet;

import android.widget.Button;

import com.grevoltec.cosechaguiddins.entities.PalletEntity;
import com.grevoltec.cosechaguiddins.util.AppException;

import java.util.List;

public interface IPaltCrear {

    void goToFirstStep();
    void setPeso(double peso);
    void goToSecondStep(String qrHeadPalletSelected);
    void readQR(String qr) throws AppException;
    void validateQRPallet(String qr) throws AppException;
    void validateQRJaba(String qr) throws AppException;
    Button getActionButton();
    List<PalletEntity> getPallets();
    String getQrHeadPallet();
    void setOnPalletsListener(IPaltJabaChangeListener listener);
    IPaltJabaChangeListener getOnPalletsListener();

    interface IPaltJabaChangeListener {
        void onJabaChange();
        void onClickActionButton();
        String deleteJaba(String qr) throws AppException;
    }

}
