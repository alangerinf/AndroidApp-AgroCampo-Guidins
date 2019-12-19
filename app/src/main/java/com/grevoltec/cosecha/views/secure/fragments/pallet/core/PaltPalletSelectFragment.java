package com.grevoltec.cosecha.views.secure.fragments.pallet.core;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.grevoltec.cosecha.R;
import com.grevoltec.cosecha.app.AppCosecha;
import com.grevoltec.cosecha.entities.PalletEntity;
import com.grevoltec.cosecha.util.EntityUtils;
import com.grevoltec.cosecha.util.QrUtils;
import com.grevoltec.cosecha.views.secure.fragments.core.adapter.AbsRecyclerViewAdapter;
import com.grevoltec.cosecha.views.secure.fragments.core.fragment.AbsFragment;
import com.grevoltec.cosecha.views.secure.fragments.core.holder.AbsViewHolder;
import com.grevoltec.cosecha.views.secure.fragments.core.holder.TitleViewHolder;
import com.grevoltec.cosecha.views.secure.fragments.pallet.IPaltCrear;
import com.grevoltec.cosecha.views.secure.fragments.pallet.IPaltModifica;
import com.j256.ormlite.dao.GenericRawResults;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EFragment(R.layout.core_recycler_with_title)
public class PaltPalletSelectFragment extends AbsFragment {

    public static final String PARAM_TYPE_SELECT = "TYPE_SELECT";
    public static final int TYPE_EDIT_JABA = 1;
    public static final int TYPE_EDIT_PALLET = 2;

    @ViewById(R.id.layHeader)
    protected LinearLayout layHeader;
    @ViewById(R.id.recyclerView)
    protected RecyclerView recyclerView;
    protected PaltPalletRecyclerViewAdapter adapter;
    protected TitleViewHolder titleViewHolder;
    protected List<PalletEntity> entities;

    protected IPaltModifica listenerModifica;
    protected IPaltCrear listenerCrea;
    private int iType =  -1;

    @AfterViews
    protected void onAfterViews() {
        titleViewHolder = new TitleViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.core_title_small, null, false));
        layHeader.addView(titleViewHolder.itemView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        Bundle bundle = getArguments();
        final int type = (bundle != null ? bundle.getInt(PARAM_TYPE_SELECT, iType) : iType);
        adapter = new PaltPalletRecyclerViewAdapter(getContext(), entities = getEntities(), type);
        recyclerView.setAdapter(adapter);
        titleViewHolder.lblTitle.setText(entities.size()+" "+getString(R.string.pallets_registrados));
    }

    String TAG = "select pallet"+this.getClass().getSimpleName();
    private List<PalletEntity> getEntities(){
        List<PalletEntity> result = new ArrayList<>();
        try{
            GenericRawResults<String[]> iGroup = AppCosecha.getHelper().getPalletDao()
                    .queryRaw("SELECT pallet_qr, pallet_comp, pallet_peso, COUNT(*)"+
                            "FROM pallets "+
                            "WHERE pallet_comp = 0 " +
                            "GROUP BY pallet_qr, pallet_comp "+
                            "ORDER BY 1");
            for (String[] row: iGroup.getResults()) {
                result.add(new PalletEntity(-1,"",Integer.parseInt(row[1]), row[0], row[3], Integer.parseInt(row[1]),"","","","",Double.parseDouble(row[3])));
            }
        }catch (Exception ex){
            Log.e(TAG,"getEntities"+ex.toString());
            ex.printStackTrace();
        }
        return  result;
    }

    public PaltPalletSelectFragment setOnStepChangeListener(IPaltCrear listenerCrea) {
        this.listenerCrea = listenerCrea;
        return this;
    }

    public PaltPalletSelectFragment setListenerModifica(IPaltModifica listenerModifica) {
        this.listenerModifica = listenerModifica;
        return this;
    }

    public PaltPalletSelectFragment setType(int iType) {
        this.iType = iType;
        return this;
    }

    /* RecepPalletRecyclerViewAdapter */
    public class PaltPalletRecyclerViewAdapter extends AbsRecyclerViewAdapter<PalletEntity, PaltPalletRecyclerViewAdapter.PalletViewHolder> {

        private final int type;

        public PaltPalletRecyclerViewAdapter(Context context, List<PalletEntity> entities, int type) {
            super(context, entities);
            this.type = type;
        }

        @Override
        public PaltPalletRecyclerViewAdapter.PalletViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new PaltPalletRecyclerViewAdapter.PalletViewHolder(inflaterLayout(viewGroup, R.layout.palt_row_pallet));
        }

        @Override
        public void onBindViewHolder(PaltPalletRecyclerViewAdapter.PalletViewHolder viewHolder, int i) {
            final PalletEntity entity = mEntities.get(i);
            viewHolder.lblNombre.setText(EntityUtils.ACOPIO.getNombreByCodigoOrDefault(QrUtils.QR_PALLETS.getAcopio(entity.getQrpallet())));
            viewHolder.lblPallet.setText(QrUtils.QR_PALLETS.getCorrelativo(entity.getQrpallet()) + " - " + QrUtils.QR_PALLETS.getFecha(entity.getQrpallet()));
            viewHolder.lblNro.setText(entity.getHorapallet());
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listenerModifica != null) listenerModifica.goToSecondStep(entity.getQrpallet());
                    if(listenerCrea != null) listenerCrea.goToSecondStep(entity.getQrpallet());
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