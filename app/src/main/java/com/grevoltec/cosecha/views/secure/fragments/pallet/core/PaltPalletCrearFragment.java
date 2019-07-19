package com.grevoltec.cosecha.views.secure.fragments.pallet.core;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.grevoltec.cosecha.R;
import com.grevoltec.cosecha.app.AppCosecha;
import com.grevoltec.cosecha.util.AppException;
import com.grevoltec.cosecha.util.QrUtils;
import com.grevoltec.cosecha.views.secure.fragments.core.fragment.AbsQrFragment;
import com.grevoltec.cosecha.views.secure.fragments.core.holder.AbsViewHolder;
import com.grevoltec.cosecha.views.secure.fragments.pallet.IPaltCrear;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.palt_crear_frag)
public class PaltPalletCrearFragment extends AbsQrFragment {

    @ViewById(R.id.rowPalletHead)
    protected CardView rowPalletHead;
    protected PalletHeadViewHolder palletHeadViewHolder;
    @ViewById(R.id.lblError)
    protected TextView lblError;
    @ViewById(R.id.rowError)
    protected CardView rowError;
    @ViewById(R.id.btnLecturar)
    protected FloatingActionButton btnLecturar;

    protected IPaltCrear onStepChangeListener;
    protected String qr;

    @AfterViews
    protected void onAfterViews() {
        rowPalletHead.setVisibility(View.GONE);
        setupQRCodeReader();
        palletHeadViewHolder = new PalletHeadViewHolder(rowPalletHead);
        loadQRTest();
    }

    protected void loadQRTest(){
        if(AppCosecha.IsTest) {
            onQRCodeRead("P010001170501");
        }
    }

    public PaltPalletCrearFragment setOnStepChangeListener(IPaltCrear onStepChangeListener) {
        this.onStepChangeListener = onStepChangeListener;
        return this;
    }

    @Override
    protected void onQRCodeRead(String text){
        qr = null;
        rowError.setVisibility(View.GONE);
        rowPalletHead.setVisibility(View.GONE);
        try{
            onStepChangeListener.validateQRPallet(text);
            qr = text;
            /* PalletHeadViewHolder  */
            palletHeadViewHolder.lblNombre.setText(getString(R.string.acopio)+" : "+QrUtils.QR_PALLETS.getAcopio(qr));
            palletHeadViewHolder.lblFecha.setText(QrUtils.QR_PALLETS.getFecha(qr));
            palletHeadViewHolder.lblNro.setText(QrUtils.QR_PALLETS.getCorrelativo(qr));
            rowPalletHead.setVisibility(View.VISIBLE);
        }catch (AppException ex){
            lblError.setText(ex.getMessage());
            rowError.setVisibility(View.VISIBLE);
        }catch (Exception ex1){
            ex1.printStackTrace();
            lblError.setText(R.string.ups_error_inesperado);
            rowError.setVisibility(View.VISIBLE);
        }
        btnLecturar.postDelayed(new Runnable() {
            @Override
            public void run() {
                stopCameraQR();
            }
        }, 200);
    }

    @Click(R.id.btnLecturar)
    protected void onClickBtnLecturar(){
        if(qr== null){
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle(R.string.error);
            builder.setMessage(R.string.lea_qr_pallet_para_continuar);
            builder.setCancelable(false);
            builder.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
            return;
        }
        onStepChangeListener.goToSecondStep(qr);
    }

    /* PalletHeadViewHolder */
    public static class PalletHeadViewHolder extends AbsViewHolder {

        public TextView lblNombre;
        public TextView lblFecha;
        public TextView lblNro;

        public PalletHeadViewHolder(View view) {
            super(view);
            this.lblNombre = view.findViewById(R.id.lblNombre);
            this.lblFecha = view.findViewById(R.id.lblFecha);
            this.lblNro = view.findViewById(R.id.lblNro);
        }

    }

}
