package com.grevoltec.cosecha.views.secure.fragments.pallet;

import android.widget.Button;

import com.grevoltec.cosecha.util.AppException;

import java.util.List;

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
