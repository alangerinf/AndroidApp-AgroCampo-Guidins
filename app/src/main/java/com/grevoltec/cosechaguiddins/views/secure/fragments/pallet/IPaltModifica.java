package com.grevoltec.cosechaguiddins.views.secure.fragments.pallet;

import android.widget.Button;

import com.grevoltec.cosechaguiddins.util.AppException;

public interface IPaltModifica {

    void goToFirstStep();
    void goToSecondStep(String qrPalletSelected);
    void readQR(String qr) throws AppException;
    void validateQR(String qr) throws AppException;
    Button getActionButton();
    String getQrHeadPallet();
    void setListener(IPaltModificaChangeListener listener);

    interface IPaltModificaChangeListener {
        void onClickActionButton();
    }

}
