package com.grevoltec.cosecha.views.secure.fragments.recepcion;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.grevoltec.cosecha.R;
import com.grevoltec.cosecha.app.AppCosecha;
import com.grevoltec.cosecha.entities.PlantaEntity;
import com.grevoltec.cosecha.entities.RecepcionEntity;
import com.grevoltec.cosecha.util.AppException;
import com.grevoltec.cosecha.util.QrUtils;
import com.grevoltec.cosecha.views.secure.fragments.core.AppViewPager;
import com.grevoltec.cosecha.views.secure.fragments.core.fragment.AbsFragment;
import com.grevoltec.cosecha.views.secure.fragments.core.fragment.AbsPagerAdapter;
import com.grevoltec.cosecha.views.secure.fragments.recepcion.core.RecepPalletLeerFragment_;
import com.grevoltec.cosecha.views.secure.fragments.recepcion.core.RecepResumenFragment_;
import com.grevoltec.cosecha.views.secure.fragments.recepcion.core.RecepSelPlantaFragment_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EFragment(R.layout.core_tabs)
public class RecepPalletTabFragment extends AbsFragment implements IRecepPallet {

    @ViewById(R.id.toolbar)
    protected Toolbar toolbar;
    @ViewById(R.id.btnAction)
    protected Button btnAction;
    @ViewById(R.id.tabs)
    protected TabLayout tabs;
    @ViewById(R.id.viewpager)
    protected AppViewPager viewpager;
    protected PagerAdapter adapter;

    protected int step = 0;
    protected RecepPalletTabFragment that;

    protected PlantaEntity plantaSelected;
    protected List<RecepcionEntity> recepcion;
    protected IRecepPalletChangeListener listener;


    @AfterViews
    protected void onAfterViews() {
        this.that = this;
        this.recepcion = new ArrayList<>();
        setupViewPager(viewpager);
        btnAction.setText(R.string.grabar);
    }

    private void loadDataQRS(){
        String[] data = new String[]{
                //"J0462243602950AMARANTAJUAN0340170501",
        };
        for (String qr: data) {
            try {
                readQR(qr, 5);
            } catch (AppException e) {
                e.printStackTrace();
            }
        }
    }

    private void setupViewPager(final AppViewPager viewPager) {
        goToFirstStep();
        toolbar.setTitle(R.string.leer_pallete);
        tabs.addTab(tabs.newTab().setText(R.string.planta));
        tabs.addTab(tabs.newTab().setText(R.string.qr));
        tabs.addTab(tabs.newTab().setText(R.string.resumen));
        tabs.setTabGravity(TabLayout.GRAVITY_FILL);
        viewPager.setPagingEnabled(false);
        viewPager.setAdapter(adapter = new PagerAdapter(getActivity().getSupportFragmentManager(), 3));
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

    @Override
    public void goToFirstStep() {
        step = 1;
        setTab(0);
    }

    @Override
    public void goToSecondStep(PlantaEntity plantaSelected) {
        this.plantaSelected = plantaSelected;
        step = 2;
        setTab(1);
        if(AppCosecha.IsTest) {
            loadDataQRS();
        }
    }

    @Override
    public void validateQR(String qr) throws AppException {
        QrUtils.QR_PALLETS.validateQR(qr);
        for (RecepcionEntity entity: recepcion) {
            if(qr.equalsIgnoreCase(entity.getQrpallet())) throw new AppException(getString(R.string.qr_ya_leido_x));
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
    public void readQR(String qr, double peso) throws AppException {
        validateQR(qr);
        if(peso < 0 ) throw new AppException(getString(R.string.ingrese_peso_valido));

        RecepcionEntity entity = new RecepcionEntity();

        entity.setIdplanta(plantaSelected.getIdPlanta());
        entity.setQrpallet(qr);
        entity.setIduser(AppCosecha.getUserLogin().getIdUsuario());
        entity.setImeiequipo(AppCosecha.getImei());
        entity.setFecharecep(AppCosecha.getDate());
        entity.setHorapallet(AppCosecha.getDate());
        entity.setNroequipo("");
        entity.setPesopallet(peso);

        recepcion.add(entity);

        if(listener != null){
            listener.onRecepcionChange();
        }

    }

    @Override
    public List<RecepcionEntity> getRecepcion() {
        return recepcion;
    }

    @Override
    public PlantaEntity getPlantaSelected() {
        return plantaSelected;
    }

    @Override
    public void setOnRecepcionListener(IRecepPalletChangeListener listener) {
        this.listener = listener;
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
                    return new RecepSelPlantaFragment_().setOnStepChangeListener(that);
                case 1:
                    return new RecepPalletLeerFragment_().setOnStepChangeListener(that);
                case 2:
                    return new RecepResumenFragment_().setOnStepChangeListener(that);
                default:
                    throw new RuntimeException("Tab position invalid " + position);
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.planta);
                case 1:
                    return getString(R.string.qr);
                case 2:
                    return getString(R.string.resumen);
                default:
                    throw new RuntimeException("Tab position invalid " + position);
            }
        }

    }

}