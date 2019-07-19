package com.grevoltec.cosecha.views.secure.fragments.recepcion.core;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;

import com.grevoltec.cosecha.R;
import com.grevoltec.cosecha.app.AppCosecha;
import com.grevoltec.cosecha.entities.PlantaEntity;
import com.grevoltec.cosecha.views.secure.fragments.recepcion.IRecepPallet;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EFragment(R.layout.recep_sel_planta_frag)
public class RecepSelPlantaFragment extends Fragment {

    @ViewById(R.id.lblTitle)
    protected TextView lblTitle;
    @ViewById(R.id.lblPlanta)
    protected TextView lblPlanta;
    @ViewById(R.id.txtPlanta)
    protected TextView txtPlanta;
    @ViewById(R.id.btnLecturar)
    protected FloatingActionButton btnLecturar;

    protected IRecepPallet onStepChangeListener;
    protected PlantaEntity plantaSelected;
    protected int idx;

    @AfterViews
    protected void onAfterViews() {
        try{
            List<PlantaEntity> plantas = getPlantas();
            if(plantas.size() > 0){
                plantaSelected = plantas.get(0);
            }
            onChangePlantaSelected();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    protected void onChangePlantaSelected(){
        txtPlanta.setText("");
        if(plantaSelected != null){
            txtPlanta.setText(plantaSelected.getNombrePlanta());
        }
    }

    @Click(R.id.btnLecturar)
    protected void onClickBtnLecturar(){
        if(plantaSelected== null){
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle(R.string.error);
            builder.setMessage(R.string.seleccione_planta_para_continuar);
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
        onStepChangeListener.goToSecondStep(plantaSelected);
    }

    @Click(R.id.selPlanta)
    protected void onClickSelPlanta(){
        final List<PlantaEntity> plantas = getPlantas();
        if(plantas.size() > 0){
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle(R.string.seleccione_planta);
            String[] items = new String[plantas.size()];
            int checkedItem = 1;
            int idPlantaSelected = (plantaSelected != null ? plantaSelected.getIdPlanta() :  -1);
            for (int i=0; i< plantas.size(); i++){
                PlantaEntity entity = plantas.get(i);
                items[i] = entity.getNombrePlanta();
                if(entity.getIdPlanta() == idPlantaSelected){
                    checkedItem = i;
                }
            }
            builder.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    idx = which;
                }
            });
            builder.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    plantaSelected = plantas.get(idx);
                    onChangePlantaSelected();
                }
            });
            builder.setNegativeButton(R.string.cancelar, null);
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    public RecepSelPlantaFragment setOnStepChangeListener(IRecepPallet onStepChangeListener) {
        this.onStepChangeListener = onStepChangeListener;
        return this;
    }

    private List<PlantaEntity> getPlantas() {
        List<PlantaEntity> result = new ArrayList<>();
        try{
            result.addAll(AppCosecha.getHelper().getPlantaDao().queryForAll());
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return result;
    }

}