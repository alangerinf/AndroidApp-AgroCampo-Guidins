package com.grevoltec.cosechaguiddins.views.secure.fragments.cosecha.reporte.core;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.grevoltec.cosechaguiddins.R;
import com.grevoltec.cosechaguiddins.app.AppCosecha;
import com.grevoltec.cosechaguiddins.models.AppReporte;
import com.grevoltec.cosechaguiddins.views.secure.fragments.core.fragment.AbsFragment;
import com.grevoltec.cosechaguiddins.views.secure.fragments.core.holder.AbsViewHolder;
import com.j256.ormlite.dao.GenericRawResults;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@EFragment(R.layout.core_recycler_with_title)
public class CschReptCosechadorFragment extends AbsFragment {

    @ViewById(R.id.layHeader)
    protected LinearLayout layHeader;
    @ViewById(R.id.recyclerView)
    protected RecyclerView recyclerView;
    protected CschRepCosechadorRecyclerViewAdapter adapter;
    protected AbsViewHolder titleViewHolder;

    @AfterViews
    protected void onAfterViews() {
        titleViewHolder = new TitleViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.csch_title_report, null, false));
        layHeader.addView(titleViewHolder.itemView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CschRepCosechadorRecyclerViewAdapter(getContext(), getEntities());
        recyclerView.setAdapter(adapter);
        recyclerView.setBackgroundColor(getContext().getResources().getColor(R.color.white));
    }

    private List<AppReporte> getEntities(){
        List<AppReporte> result = new ArrayList<>();
        try {
            GenericRawResults<String[]> iGroup = AppCosecha.getHelper().getCosechaDao()
                    .queryRaw("SELECT qr_cosechador, COUNT(*) "+
                                     "FROM cosechas "+
                                     "GROUP BY qr_cosechador "+
                                     "ORDER BY 2 DESC, 1");
            int i = 1;
            for (String[] row: iGroup.getResults()) {
                result.add(new AppReporte(""+(i++), row[0], row[1]));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  result;
    }

    /* CschRepCosechadorRecyclerViewAdapter */
    public static class CschRepCosechadorRecyclerViewAdapter extends RecyclerView.Adapter<CschRepCosechadorRecyclerViewAdapter.RepCosechadorViewHolder> {

        private List<AppReporte> mEntities;
        private Context mContext;

        public CschRepCosechadorRecyclerViewAdapter(Context context, List<AppReporte> entities) {
            this.mEntities = entities;
            this.mContext = context;
        }

        @Override
        public CschRepCosechadorRecyclerViewAdapter.RepCosechadorViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.csch_row_report, viewGroup, false);
            CschRepCosechadorRecyclerViewAdapter.RepCosechadorViewHolder viewHolder = new CschRepCosechadorRecyclerViewAdapter.RepCosechadorViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(CschRepCosechadorRecyclerViewAdapter.RepCosechadorViewHolder viewHolder, int i) {
            AppReporte entity = mEntities.get(i);
            viewHolder.lblPos.setText(entity.getOrden());
            viewHolder.lblNombre.setText(entity.getGroup());
            viewHolder.lblNro.setText(entity.getCount());
        }

        @Override
        public int getItemCount() {
            return (null != mEntities ? mEntities.size() : 0);
        }

        /* RepCosechadorViewHolder */
        public static class RepCosechadorViewHolder extends RecyclerView.ViewHolder {

            public TextView lblPos;
            public TextView lblNombre;
            public TextView lblNro;

            public RepCosechadorViewHolder(View view) {
                super(view);
                this.lblPos = view.findViewById(R.id.lblPos);
                this.lblNombre = view.findViewById(R.id.lblNombre);
                this.lblNro = view.findViewById(R.id.lblNro);
            }

        }

    }

    /* TitleViewHolder */
    public class TitleViewHolder extends AbsViewHolder{

        public TextView titPos;
        public TextView titNombre;
        public TextView titNro;

        public TitleViewHolder(View view) {
            super(view);
            this.titPos = view.findViewById(R.id.titPos);
            this.titNombre = view.findViewById(R.id.titNombre);
            this.titNro = view.findViewById(R.id.titNro);
            init();
        }

        protected void init(){
            this.titPos.setText(R.string.nro);
            this.titNombre.setText(R.string.cosechador);
            this.titNro.setText(R.string.jabas);
        }
    }

}
