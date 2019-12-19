package com.grevoltec.cosechaguiddins.views.secure.fragments.pallet;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.grevoltec.cosechaguiddins.R;
import com.grevoltec.cosechaguiddins.app.AppCosecha;
import com.grevoltec.cosechaguiddins.entities.PalletEntity;
import com.grevoltec.cosechaguiddins.util.EntityUtils;
import com.grevoltec.cosechaguiddins.util.QrUtils;
import com.grevoltec.cosechaguiddins.views.secure.fragments.core.adapter.AbsRecyclerViewAdapter;
import com.grevoltec.cosechaguiddins.views.secure.fragments.core.fragment.AbsFragment;
import com.grevoltec.cosechaguiddins.views.secure.fragments.core.holder.AbsViewHolder;
import com.grevoltec.cosechaguiddins.views.secure.fragments.core.holder.TitleViewHolder;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.stmt.UpdateBuilder;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@EFragment(R.layout.core_recycler_with_title_and_toolbar)
public class PaltPalletCompletarFragment extends AbsFragment {

    @ViewById(R.id.toolbar)
    protected Toolbar toolbar;
    @ViewById(R.id.btnAction)
    protected Button btnAction;
    @ViewById(R.id.layHeader)
    protected LinearLayout layHeader;
    @ViewById(R.id.recyclerView)
    protected RecyclerView recyclerView;
    protected PaltPalletRecyclerViewAdapter adapter;
    protected TitleViewHolder titleViewHolder;
    protected List<PalletEntity> entities;

    @AfterViews
    protected void onAfterViews() {
        toolbar.setTitle(R.string.completar_palletizado);
        titleViewHolder = new TitleViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.core_title_small, null, false));
        layHeader.addView(titleViewHolder.itemView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PaltPalletRecyclerViewAdapter(getContext(), entities = getEntities());
        titleViewHolder.lblTitle.setText(entities.size()+" "+getString(R.string.pallets_registrados));
        recyclerView.setAdapter(adapter);
        btnAction.setText("Grabar");
        btnAction.setVisibility(View.VISIBLE);
    }

    @Click(R.id.btnAction)
    public void onClickActionButton() {
        try {
            for (PalletEntity entity: entities) {
                if(entity.getIduser() != entity.getPalletcomp()) {
                    UpdateBuilder update = AppCosecha.getHelper().getPalletDao().updateBuilder();
                    update.updateColumnValue("pallet_comp", entity.getPalletcomp())
                            .where().eq("pallet_qr", entity.getQrpallet())
                            .and().eq("pallet_comp", entity.getIduser());
                    update.update();
                }
            }

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
            Log.e(TAG,"onClickActionButton:"+e.toString());
            e.printStackTrace();
        }
    }

    String TAG = "completar"+this.getClass().getSimpleName();
    private List<PalletEntity> getEntities(){
        List<PalletEntity> result = new ArrayList<>();
        try{
            GenericRawResults<String[]> iGroup = AppCosecha.getHelper().getCosechaDao()
                    .queryRaw("SELECT pallet_qr, pallet_comp, pallet_peso, COUNT(*)"+
                            "FROM pallets "+
                            "GROUP BY pallet_qr, pallet_comp "+
                            "ORDER BY 1");
            for (String[] row: iGroup.getResults()) {
                result.add(new PalletEntity(-1,"",Integer.parseInt(row[1]), row[0], row[3], Integer.parseInt(row[1]),"","","","",Double.parseDouble(row[2])));
            }
        }catch (Exception ex){
            Log.e(TAG,"getEntities:"+ex.toString());
            ex.printStackTrace();
        }
        return  result;
    }

    /* RecepPalletRecyclerViewAdapter */
    public static class PaltPalletRecyclerViewAdapter extends AbsRecyclerViewAdapter<PalletEntity, PaltPalletRecyclerViewAdapter.PalletViewHolder> {

        public PaltPalletRecyclerViewAdapter(Context context, List<PalletEntity> entities) {
            super(context, entities);
        }

        @Override
        public PaltPalletRecyclerViewAdapter.PalletViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new PaltPalletRecyclerViewAdapter.PalletViewHolder(inflaterLayout(viewGroup, R.layout.palt_row_completar));
        }

        @Override
        public void onBindViewHolder(final PaltPalletRecyclerViewAdapter.PalletViewHolder viewHolder, int i) {
            final PalletEntity entity = mEntities.get(i);
            viewHolder.lblNombre.setText(EntityUtils.ACOPIO.getNombreByCodigoOrDefault(QrUtils.QR_PALLETS.getAcopio(entity.getQrpallet())));
            viewHolder.lblNro.setText(QrUtils.QR_PALLETS.getCorrelativo(entity.getQrpallet()) + " - " + QrUtils.QR_PALLETS.getFecha(entity.getQrpallet()) + " ( " + entity.getHorapallet() + " )");
            viewHolder.chkSelected.setChecked(entity.getPalletcomp() == 1);
            viewHolder.chkSelected.setEnabled(entity.getPalletcomp() != 1);
            viewHolder.chkSelected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    entity.setPalletcomp(b ? 1 : 0);
                }
            });
            viewHolder.vwLine.setBackgroundColor(mContext.getResources().getColor(entity.getPalletcomp() == 1 ? R.color.colorCompleto : R.color.colorIncompleto));
            viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle(R.string.alerta);
                    builder.setMessage(R.string.desea_abrir_registro);
                    builder.setPositiveButton(R.string.reabrir, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            entity.setPalletcomp(0);
                            viewHolder.chkSelected.setChecked(entity.getPalletcomp() == 1);
                            viewHolder.chkSelected.setEnabled(true);
                            viewHolder.vwLine.setBackgroundColor(mContext.getResources().getColor(entity.getPalletcomp() == 1 ? R.color.colorCompleto : R.color.colorIncompleto));
                        }
                    });
                    builder.setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return false;
                }
            });
        }

        /* JabaViewHolder */
        public static class PalletViewHolder extends AbsViewHolder {

            public TextView lblNombre;
            public TextView lblNro;
            public CheckBox chkSelected;
            public View vwLine;

            public PalletViewHolder(View view) {
                super(view);
                this.lblNombre = view.findViewById(R.id.lblNombre);
                this.lblNro = view.findViewById(R.id.lblNro);
                this.chkSelected = view.findViewById(R.id.chkSelected);
                this.vwLine = view.findViewById(R.id.vwLine);
            }

        }

    }

}