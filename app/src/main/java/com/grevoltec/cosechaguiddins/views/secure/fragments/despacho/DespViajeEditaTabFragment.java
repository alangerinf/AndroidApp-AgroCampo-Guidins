package com.grevoltec.cosechaguiddins.views.secure.fragments.despacho;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.grevoltec.cosechaguiddins.R;
import com.grevoltec.cosechaguiddins.app.AppCosecha;
import com.grevoltec.cosechaguiddins.entities.ViajeEntity;
import com.grevoltec.cosechaguiddins.util.AppException;
import com.grevoltec.cosechaguiddins.util.EntityUtils;
import com.grevoltec.cosechaguiddins.util.QrUtils;
import com.grevoltec.cosechaguiddins.views.secure.fragments.core.AppViewPager;
import com.grevoltec.cosechaguiddins.views.secure.fragments.core.fragment.AbsFragment;
import com.grevoltec.cosechaguiddins.views.secure.fragments.core.fragment.AbsPagerAdapter;
import com.grevoltec.cosechaguiddins.views.secure.fragments.despacho.core.DespViajeSelectFragment_;
import com.grevoltec.cosechaguiddins.views.secure.fragments.despacho.pallet.DespPalletEditarFragment_;
import com.grevoltec.cosechaguiddins.views.secure.fragments.despacho.pallet.DespViajeResumenFragment_;
import com.j256.ormlite.stmt.QueryBuilder;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EFragment(R.layout.core_tabs)
public class DespViajeEditaTabFragment extends AbsFragment implements IViajeCrear {

    @ViewById(R.id.toolbar)
    protected Toolbar toolbar;
    @ViewById(R.id.btnAction)
    protected Button btnAction;
    @ViewById(R.id.tabs)
    protected TabLayout tabs;
    @ViewById(R.id.viewpager)
    protected AppViewPager viewpager;
    protected DespViajeEditaTabFragment.PagerAdapter adapter;

    protected int step = 0;
    protected DespViajeEditaTabFragment that;

    protected String qrCamionSelected;
    protected List<ViajeEntity> items;
    protected IViajePalletChangeListener listener;

    @AfterViews
    protected void onAfterViews() {
        this.that = this;
        this.items = new ArrayList<>();
        setupViewPager(viewpager);
        btnAction.setText(R.string.grabar);
    }

    private void loadDataQRS(){
        String[] data = new String[]{
                "P010001170501",
                "P010002170501",
                "P010003170501",
                "P010004170501",
                "P010005170501",
                "P010006170501",
                "P010007170501",
                "P010008170501",
                "P010009170501",
                "P010010170501",
                "P010011170501",
                "P010012170501",
                "P010013170501"
        };
        for (String qr: data) {
            try {
                readQR(qr);
            } catch (AppException e) {
                e.printStackTrace();
            }
        }
    }

    private void setupViewPager(final AppViewPager viewPager) {
        goToFirstStep();
        toolbar.setTitle(R.string.agregar_quitar_pallete);
        tabs.addTab(tabs.newTab().setText(R.string.desp_crear_viaje));
        tabs.addTab(tabs.newTab().setText(R.string.desp_leer_pallet));
        tabs.addTab(tabs.newTab().setText(R.string.desp_resumen));
        tabs.setTabGravity(TabLayout.GRAVITY_FILL);
        viewPager.setPagingEnabled(false);
        viewPager.setAdapter(adapter = new DespViajeEditaTabFragment.PagerAdapter(getActivity().getSupportFragmentManager(), 3));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            private int lastPosition = -1;

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (step){
                    case 1:
                        if (tab.getPosition() != 0) {
                            lastPosition = 0;
                            viewPager.setCurrentItem(lastPosition);
                            TabLayout.Tab tab1 = tabs.getTabAt(lastPosition);
                            tab1.select();
                        } else {
                            lastPosition = tab.getPosition();
                            viewPager.setCurrentItem(tab.getPosition());
                        }
                        break;
                    case 2:
                        if (tab.getPosition() != 1 && tab.getPosition() != 2) {
                            lastPosition = (lastPosition == 1 ||  lastPosition == 2 ? lastPosition : 1 );
                            viewPager.setCurrentItem(lastPosition);
                            TabLayout.Tab tab1 = tabs.getTabAt(lastPosition);
                            tab1.select();
                        } else {
                            lastPosition = tab.getPosition();
                            viewPager.setCurrentItem(tab.getPosition());
                        }
                        btnAction.setVisibility( tab.getPosition() == 2 ? View.VISIBLE : View.INVISIBLE );
                        break;
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }
            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });
    }

    public void goToFirstStep() {
        step = 1;
        setTab(0);
    }

    @Override
    public void goToSecondStep(String qrCamionSelected) {
        this.qrCamionSelected = qrCamionSelected;
        step = 2;
        setTab(1);
        try{
            QueryBuilder<ViajeEntity, Long> query = AppCosecha.getHelper().getViajeDao().queryBuilder();
            query.where().eq("camion_qr",this.qrCamionSelected);
            items.addAll(query.query());
            for ( ViajeEntity entity: items) {
                entity.setNroPallets(EntityUtils.PALLET.getSizeByQROrDefault(entity.getQrpallet()));
                entity.setAcopio(EntityUtils.ACOPIO.getEntityByCodigoOrDefault(QrUtils.QR_PALLETS.getAcopio(entity.getQrpallet())));
            }
            if(listener != null){
                listener.onViajeChange();
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        if(AppCosecha.IsTest) {
            loadDataQRS();
        }
    }

    @Override
    public void validateQRCaminion(String qr) throws AppException {
        QrUtils.QR_CAMIONES.validateQR(qr);
    }

    @Override
    public void validateQRPallete(String qr) throws AppException {
        QrUtils.QR_PALLETS.validateQR(qr);
        for (ViajeEntity entity: items) {
            if(qr.equalsIgnoreCase(entity.getQrpallet())) throw new AppException(getString(R.string.qr_pallet_ya_leido)).setCode(AppException.ERROR_DUPLICATE);;
        }
    }

    @Override
    public Button getActionButton() {
        return btnAction;
    }

    @Click(R.id.btnAction)
    protected void onClickActionButton(){
        if(listener != null){
            listener.onClickActionButton();
        }
    }

    @Override
    public void readQR(String qr) throws AppException {
        validateQRPallete(qr);

        ViajeEntity entity = new ViajeEntity();

        entity.setIduser(AppCosecha.getUserLogin().getIdUsuario());
        entity.setImeiequipo(AppCosecha.getImei());
        entity.setNumeroequipo("");
        entity.setQrcamion(qrCamionSelected);
        entity.setQrpallet(qr);
        entity.setViajecompleto(0);
        entity.setHoracamion(AppCosecha.getDate());
        entity.setHorapallet(AppCosecha.getDate());

        entity.setNroPallets(EntityUtils.PALLET.getSizeByQROrDefault(qr));
        entity.setAcopio(EntityUtils.ACOPIO.getEntityByCodigoOrDefault(QrUtils.QR_PALLETS.getAcopio(qr)));

        items.add(entity);

        if(listener != null){
            listener.onViajeChange();
        }
    }

    @Override
    public List<ViajeEntity> getViajes() {
        return items;
    }

    @Override
    public String getQrHeadPallet() {
        return qrCamionSelected;
    }

    @Override
    public void setOnViajesListener(IViajePalletChangeListener listener) {
        this.listener = listener;
    }

    @Override
    public IViajePalletChangeListener getOnViajesListener() {
        return listener;
    }

    public void setTab(int idx){
        if(viewpager!= null) viewpager.setCurrentItem(idx);
        if(tabs!= null){
            TabLayout.Tab tab1 = tabs.getTabAt(idx);
            if(tab1 != null) tab1.select();
        }
    }

    /* PagerAdapter */
    public class PagerAdapter extends AbsPagerAdapter {

        public PagerAdapter(FragmentManager fm, int numTabs) {
            super(fm, numTabs);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new DespViajeSelectFragment_().setOnStepChangeListener(that);
                case 1:
                    return new DespPalletEditarFragment_().setOnStepChangeListener(that);
                case 2:
                    return new DespViajeResumenFragment_().setOnStepChangeListener(that);
                default:
                    throw new RuntimeException("Tab position invalid " + position);
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getContext().getString(R.string.desp_crear_viaje);
                case 1:
                    return getContext().getString(R.string.desp_leer_pallet);
                case 2:
                    return getContext().getString(R.string.desp_resumen);
                default:
                    throw new RuntimeException("Tab position invalid " + position);
            }
        }

    }

}