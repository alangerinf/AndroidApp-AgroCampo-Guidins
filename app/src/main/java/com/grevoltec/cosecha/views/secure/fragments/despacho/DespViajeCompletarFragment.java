package com.grevoltec.cosecha.views.secure.fragments.despacho;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.grevoltec.cosecha.R;
import com.grevoltec.cosecha.app.AppCosecha;
import com.grevoltec.cosecha.entities.ViajeEntity;
import com.grevoltec.cosecha.util.EntityUtils;
import com.grevoltec.cosecha.util.QrUtils;
import com.grevoltec.cosecha.views.secure.fragments.core.adapter.AbsRecyclerViewAdapter;
import com.grevoltec.cosecha.views.secure.fragments.core.holder.AbsViewHolder;
import com.grevoltec.cosecha.views.secure.fragments.core.holder.TitleViewHolder;
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
public class DespViajeCompletarFragment extends Fragment {

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
    protected List<ViajeEntity> entities;

    @AfterViews
    protected void onAfterViews() {
        toolbar.setTitle(R.string.completar_viaje);
        titleViewHolder = new TitleViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.core_title_small, null, false));
        layHeader.addView(titleViewHolder.itemView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PaltPalletRecyclerViewAdapter(getContext(), entities = getEntities());
        titleViewHolder.lblTitle.setText(entities.size()+" "+getString(R.string.viajes_registrados));
        recyclerView.setAdapter(adapter);
        btnAction.setText("Grabar");
        btnAction.setVisibility(View.VISIBLE);
    }

    @Click(R.id.btnAction)
    public void onClickActionButton() {
        try {
            for (ViajeEntity entity: entities) {
                if(entity.getIduser() != entity.getViajecompleto()){
                    UpdateBuilder update= AppCosecha.getHelper().getViajeDao().updateBuilder();
                    update.updateColumnValue("viaje_completo",entity.getViajecompleto())
                            .where().eq("camion_qr",entity.getQrcamion())
                            .and().eq("viaje_completo",entity.getIduser());
                    update.update();
                }
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle(R.string.mensaje);
            builder.setCancelable(false);
            builder.setMessage(R.string.confirmacion_registro_local);
            builder.setPositiveButton(R.string.continuar, new DialogInterface.OnClickListener() {
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

    private List<ViajeEntity> getEntities(){
        List<ViajeEntity> result = new ArrayList<>();
        try{
            GenericRawResults<String[]> iGroup = AppCosecha.getHelper().getCosechaDao()
                    .queryRaw("SELECT camion_qr, viaje_completo, COUNT(*) "+
                            "FROM viajes "+
                            "GROUP BY camion_qr, viaje_completo "+
                            "ORDER BY 1");
            for (String[] row: iGroup.getResults()) {
                result.add(new ViajeEntity(-1,"",Integer.parseInt(row[1]), row[0], row[2], Integer.parseInt(row[1]),"","","",""));
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return  result;
    }

    /* RecepPalletRecyclerViewAdapter */
    public static class PaltPalletRecyclerViewAdapter extends AbsRecyclerViewAdapter<ViajeEntity, PaltPalletRecyclerViewAdapter.PalletViewHolder> {

        public PaltPalletRecyclerViewAdapter(Context context, List<ViajeEntity> entities) {
            super(context, entities);
        }

        @Override
        public PaltPalletRecyclerViewAdapter.PalletViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new PaltPalletRecyclerViewAdapter.PalletViewHolder(inflaterLayout(viewGroup, R.layout.desp_row_completar));
        }

        @Override
        public void onBindViewHolder(final PaltPalletRecyclerViewAdapter.PalletViewHolder viewHolder, int i) {
            final ViajeEntity entity = mEntities.get(i);
            viewHolder.lblNombre.setText(Html.fromHtml(EntityUtils.CAMION.getPlacaByCodigoOrDefault(QrUtils.QR_CAMIONES.getCodigo(entity.getQrcamion())))+" - "+entity.getQrcamion());
            viewHolder.lblNro.setText(entity.getHoracamion());
            viewHolder.chkSelected.setChecked(entity.getViajecompleto() == 1);
            viewHolder.chkSelected.setEnabled(entity.getViajecompleto() != 1);
            viewHolder.chkSelected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    entity.setViajecompleto(b ? 1 : 0);
                }
            });
            viewHolder.vwLine.setBackgroundColor(mContext.getResources().getColor(entity.getViajecompleto() == 1 ? R.color.colorCompleto : R.color.colorIncompleto));
            viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle(R.string.alerta);
                    builder.setMessage(R.string.desea_abrir_registro);
                    builder.setPositiveButton(R.string.reabrir, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            entity.setViajecompleto(0);
                            viewHolder.chkSelected.setChecked(entity.getViajecompleto() == 1);
                            viewHolder.chkSelected.setEnabled(true);
                            viewHolder.vwLine.setBackgroundColor(mContext.getResources().getColor(entity.getViajecompleto() == 1 ? R.color.colorCompleto : R.color.colorIncompleto));
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