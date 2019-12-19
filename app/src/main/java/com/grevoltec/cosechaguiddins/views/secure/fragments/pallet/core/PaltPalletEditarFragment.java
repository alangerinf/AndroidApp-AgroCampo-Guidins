package com.grevoltec.cosechaguiddins.views.secure.fragments.pallet.core;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.grevoltec.cosechaguiddins.R;
import com.grevoltec.cosechaguiddins.app.AppCosecha;
import com.grevoltec.cosechaguiddins.util.AppException;
import com.grevoltec.cosechaguiddins.util.EntityUtils;
import com.grevoltec.cosechaguiddins.util.QrUtils;
import com.grevoltec.cosechaguiddins.views.secure.fragments.core.fragment.AbsQrFragment;
import com.grevoltec.cosechaguiddins.views.secure.fragments.core.holder.AbsViewHolder;
import com.grevoltec.cosechaguiddins.views.secure.fragments.pallet.IPaltModifica;
import com.j256.ormlite.stmt.UpdateBuilder;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.sql.SQLException;

@EFragment(R.layout.palt_edit_pallet_frag)
public class PaltPalletEditarFragment extends AbsQrFragment implements IPaltModifica.IPaltModificaChangeListener {

    @ViewById(R.id.rowPallet)
    protected CardView rowPallet;
    protected PalletViewHolder palletViewHolder;
    @ViewById(R.id.lblError)
    protected TextView lblError;
    @ViewById(R.id.rowError)
    protected CardView rowError;

    protected IPaltModifica onStepChangeListener;
    protected String qr;

    @AfterViews
    protected void onAfterViews() {
        rowPallet.setVisibility(View.GONE);
        setupQRCodeReader();
        palletViewHolder = new PaltPalletEditarFragment.PalletViewHolder(rowPallet);
        onStepChangeListener.setListener(this);
        rowError.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadQRTest();
            }
        }, 4500);
    }

    protected void loadQRTest(){
        if(AppCosecha.IsTest) {
            onQRCodeRead("P010003170501");
        }
    }

    @Override
    protected void onClickQRImage() {
        rowPallet.setVisibility(View.GONE);
        super.onClickQRImage();
    }

    public PaltPalletEditarFragment setOnStepChangeListener(IPaltModifica onStepChangeListener) {
        this.onStepChangeListener = onStepChangeListener;
        return this;
    }

    @Override
    protected void onQRCodeRead(String text){
        qr = null;
        rowError.setVisibility(View.GONE);
        rowPallet.setVisibility(View.GONE);
        try{
            onStepChangeListener.validateQR(text);
            qr = text;
            /* ViajeViewHolder  */
            palletViewHolder.lblPallet.setText(QrUtils.QR_PALLETS.getCorrelativo(qr));
            palletViewHolder.lblNombre.setText(EntityUtils.ACOPIO.getNombreByCodigoOrDefault(QrUtils.QR_PALLETS.getAcopio(qr)));
            palletViewHolder.lblNro.setText(EntityUtils.PALLET.getSizeByQROrDefault(onStepChangeListener.getQrHeadPallet()) );
            rowPallet.setVisibility(View.VISIBLE);
        }catch (AppException ex){
            lblError.setText(ex.getMessage());
            rowError.setVisibility(View.VISIBLE);
        }catch (Exception ex1){
            ex1.printStackTrace();
            lblError.setText(R.string.ups_error_inesperado);
            rowError.setVisibility(View.VISIBLE);
        }
        /*
        rowError.postDelayed(new Runnable() {
            @Override
            public void run() {
                stopCameraQR();
            }
        }, 200);

         */
    }

    @Override
    public void onClickActionButton() {
        try {
            UpdateBuilder update = AppCosecha.getHelper().getPalletDao().updateBuilder();
            update.updateColumnValue("pallet_qr", qr)
                    .where().eq("pallet_qr", onStepChangeListener.getQrHeadPallet())
                    .and().eq("pallet_comp", 0);
            update.update();

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle(R.string.mensaje);
            builder.setCancelable(false);
            builder.setMessage(R.string.confirmacion_actualizacion_local);
            builder.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    getActivity().onBackPressed();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        } catch (SQLException e) {
            e.printStackTrace();
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
            this.lblPallet = view.findViewById(R.id.lblPallet);
            this.lblNro = view.findViewById(R.id.lblNro);
        }

    }

}
