package com.grevoltec.cosecha.views.secure.fragments.pallet;

import android.widget.Button;

import com.grevoltec.cosecha.entities.PalletEntity;
import com.grevoltec.cosecha.util.AppException;

import java.util.List;

public interface IPaltCrear {

    void goToFirstStep();
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
