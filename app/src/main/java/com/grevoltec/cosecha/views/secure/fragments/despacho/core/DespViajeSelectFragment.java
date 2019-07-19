package com.grevoltec.cosecha.views.secure.fragments.despacho.core;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.grevoltec.cosecha.R;
import com.grevoltec.cosecha.app.AppCosecha;
import com.grevoltec.cosecha.entities.ViajeEntity;
import com.grevoltec.cosecha.util.EntityUtils;
import com.grevoltec.cosecha.util.QrUtils;
import com.grevoltec.cosecha.views.secure.fragments.core.adapter.AbsRecyclerViewAdapter;
import com.grevoltec.cosecha.views.secure.fragments.core.fragment.AbsFragment;
import com.grevoltec.cosecha.views.secure.fragments.core.holder.AbsViewHolder;
import com.grevoltec.cosecha.views.secure.fragments.core.holder.TitleViewHolder;
import com.grevoltec.cosecha.views.secure.fragments.despacho.IViajeCrear;
import com.grevoltec.cosecha.views.secure.fragments.despacho.IViajeModifica;
import com.j256.ormlite.dao.GenericRawResults;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EFragment(R.layout.core_recycler_with_title)
public class DespViajeSelectFragment extends AbsFragment {

    public static final String PARAM_TYPE_SELECT = "TYPE_SELECT";
    public static final int TYPE_EDIT_PALLET = 1;
    public static final int TYPE_EDIT_VIAJE = 2;

    @ViewById(R.id.layHeader)
    protected LinearLayout layHeader;
    @ViewById(R.id.recyclerView)
    protected RecyclerView recyclerView;
    protected PaltPalletRecyclerViewAdapter adapter;
    protected TitleViewHolder titleViewHolder;
    protected List<ViajeEntity> entities;

    protected IViajeModifica listenerModifica;
    protected IViajeCrear listenerCrea;
    private int iType =  -1;

    @AfterViews
    protected void onAfterViews() {
        titleViewHolder = new TitleViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.core_title_small, null, false));
        titleViewHolder.lblTitle.setText(R.string.nro_viajes_leidos);
        layHeader.addView(titleViewHolder.itemView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        Bundle bundle = getArguments();
        final int type = (bundle != null ? bundle.getInt(PARAM_TYPE_SELECT, iType) : iType);
        adapter = new PaltPalletRecyclerViewAdapter(getContext(), entities= getEntities(), type);
        recyclerView.setAdapter(adapter);
        titleViewHolder.lblTitle.setText(entities.size()+" "+getString(R.string.viajes_registrados));
    }

    private List<ViajeEntity> getEntities(){
        List<ViajeEntity> result = new ArrayList<>();
        try{
            GenericRawResults<String[]> iGroup = AppCosecha.getHelper().getCosechaDao()
                    .queryRaw("SELECT camion_qr, viaje_completo, COUNT(*) "+
                            "FROM viajes "+
                            "WHERE viaje_completo = 0 " +
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

    public DespViajeSelectFragment setOnStepChangeListener(IViajeCrear listenerCrea) {
        this.listenerCrea = listenerCrea;
        return this;
    }

    public DespViajeSelectFragment setListenerModifica(IViajeModifica listenerModifica) {
        this.listenerModifica = listenerModifica;
        return this;
    }

    public DespViajeSelectFragment setType(int iType) {
        this.iType = iType;
        return this;
    }

    /* RecepPalletRecyclerViewAdapter */
    public class PaltPalletRecyclerViewAdapter extends AbsRecyclerViewAdapter<ViajeEntity, PaltPalletRecyclerViewAdapter.PalletViewHolder> {

        private final int type;

        public PaltPalletRecyclerViewAdapter(Context context, List<ViajeEntity> entities, int type) {
            super(context, entities);
            this.type = type;
        }

        @Override
        public PaltPalletRecyclerViewAdapter.PalletViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new PaltPalletRecyclerViewAdapter.PalletViewHolder(inflaterLayout(viewGroup, R.layout.palt_row_pallet));
        }

        @Override
        public void onBindViewHolder(PaltPalletRecyclerViewAdapter.PalletViewHolder viewHolder, int i) {
            final ViajeEntity entity = mEntities.get(i);
            viewHolder.lblNombre.setText(EntityUtils.CAMION.getPlacaByCodigoOrDefault(QrUtils.QR_CAMIONES.getCodigo(entity.getQrcamion())));
            viewHolder.lblPallet.setText(entity.getQrcamion());
            viewHolder.lblNro.setText(entity.getHoracamion());
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listenerModifica != null) listenerModifica.goToSecondStep(entity.getQrcamion());
                    if(listenerCrea != null) listenerCrea.goToSecondStep(entity.getQrcamion());
                }
            });
        }

        /* JabaViewHolder */
        public  class PalletViewHolder extends AbsViewHolder {

            public TextView lblNombre;
            public TextView lblNro;
            public TextView lblPallet;

            public PalletViewHolder(View view) {
                super(view);
                this.lblNombre = view.findViewById(R.id.lblNombre);
                this.lblPallet = view.findViewById(R.id.lblPallet);
                this.lblNro = view.findViewById(R.id.lblNro);
            }

        }

    }

}