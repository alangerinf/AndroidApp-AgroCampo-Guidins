package com.grevoltec.cosecha.views.secure.fragments.despacho.pallet;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.grevoltec.cosecha.R;
import com.grevoltec.cosecha.app.AppCosecha;
import com.grevoltec.cosecha.util.AppException;
import com.grevoltec.cosecha.util.EntityUtils;
import com.grevoltec.cosecha.util.QrUtils;
import com.grevoltec.cosecha.views.secure.fragments.core.fragment.AbsQrFragment;
import com.grevoltec.cosecha.views.secure.fragments.core.holder.AbsViewHolder;
import com.grevoltec.cosecha.views.secure.fragments.despacho.IViajeCrear;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.desp_edit_palt_frag)
public class DespPalletEditarFragment extends AbsQrFragment {

    @ViewById(R.id.rowPallet)
    protected CardView rowPallet;
    protected PalletViewHolder palletViewHolder;
    @ViewById(R.id.lblError)
    protected TextView lblError;
    @ViewById(R.id.rowError)
    protected CardView rowError;
    @ViewById(R.id.btnLecturar)
    protected FloatingActionButton btnLecturar;

    protected IViajeCrear onStepChangeListener;
    protected String qr;

    @AfterViews
    protected void onAfterViews() {
        rowPallet.setVisibility(View.GONE);
        setupQRCodeReader();
        palletViewHolder = new PalletViewHolder(rowPallet);
        rowPallet.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadQRTest();
            }
        },5000);
    }

    protected void loadQRTest(){
        if(AppCosecha.IsTest) {
            onQRCodeRead("P010002170501");
        }
    }

    @Override
    protected void onClickQRImage() {
        rowPallet.setVisibility(View.GONE);
        super.onClickQRImage();
    }

    public DespPalletEditarFragment setOnStepChangeListener(IViajeCrear onStepChangeListener) {
        this.onStepChangeListener = onStepChangeListener;
        return this;
    }

    @Override
    protected void onQRCodeRead(String text){
        if(text.equalsIgnoreCase(qr)) return;
        qr = null;
        rowError.setVisibility(View.GONE);
        rowPallet.setVisibility(View.GONE);
        try{
            onStepChangeListener.validateQRPallete(text);
            qr = text;
            /* ViajeViewHolder  */
            palletViewHolder.lblPallet.setText(QrUtils.QR_PALLETS.getCorrelativo(qr));
            palletViewHolder.lblNombre.setText(EntityUtils.ACOPIO.getNombreByCodigoOrDefault(QrUtils.QR_PALLETS.getAcopio(qr)));
            palletViewHolder.lblNro.setText(EntityUtils.PALLET.getSizeByQROrDefault(qr) );
            rowPallet.setVisibility(View.VISIBLE);
        }catch (AppException ex){
            if(ex.getCode() == AppException.ERROR_DUPLICATE){
                qr = text;
                removeQRPallet(qr);
            }else{
                lblError.setText(ex.getMessage());
                rowError.setVisibility(View.VISIBLE);
            }
        }catch (Exception ex1){
            ex1.printStackTrace();
            lblError.setText(R.string.ups_error_inesperado);
            rowError.setVisibility(View.VISIBLE);
            Toast.makeText(AppCosecha.getContext(), ex1.getMessage(), Toast.LENGTH_LONG).show();
        }
        /*
        btnLecturar.postDelayed(new Runnable() {
            @Override
            public void run() {
                stopCameraQR();
            }
        }, 200);


         */

    }

    @Click(R.id.btnLecturar)
    protected void onClickBtnLecturar(){
        rowError.setVisibility(View.GONE);
        if(qr != null){
            try{
                onStepChangeListener.readQR(qr);
                rowPallet.setVisibility(View.GONE);
                qr = null;
            }catch (AppException ex){
                if(ex.getCode() == AppException.ERROR_DUPLICATE && qr != null){
                    removeQRPallet(qr);
                }else{
                    lblError.setText(ex.getMessage());
                    rowError.setVisibility(View.VISIBLE);
                }
            }catch (Exception ex1){
                ex1.printStackTrace();
                lblError.setText(R.string.ups_error_inesperado);
                rowError.setVisibility(View.VISIBLE);
            }
        }else{
            lblError.setText(R.string.lea_qr_valido_para_continuar);
            rowError.setVisibility(View.VISIBLE);
        }
    }

    protected void removeQRPallet(String text){
        try{
            lblError.setText(onStepChangeListener.getOnViajesListener().deletePallet(text));
            rowError.setVisibility(View.VISIBLE);
            rowPallet.setVisibility(View.GONE);
        }catch (AppException ex){
            ex.printStackTrace();
            lblError.setText(ex.getMessage());
            rowError.setVisibility(View.VISIBLE);
        }catch (Exception ex1){
            ex1.printStackTrace();
            lblError.setText(R.string.ups_error_inesperado);
            rowError.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onTorchOn() {

    }

    @Override
    public void onTorchOff() {

    }

    /* ViajeViewHolder */
    public static class PalletViewHolder extends AbsViewHolder {

        public TextView lblNombre;
        public TextView lblPallet;
        public TextView lblNro;

        public PalletViewHolder(View view) {
            super(view);
            this.lblNombre = view.findViewById(R.id.lblNombre);
            //this.lblJaba = view.findViewById(R.id.lblJaba);
            this.lblPallet = view.findViewById(R.id.lblPallet);
            this.lblNro = view.findViewById(R.id.lblNro);
        }

    }

}