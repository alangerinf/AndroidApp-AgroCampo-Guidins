package com.grevoltec.cosecha.views.secure.fragments.pallet;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.grevoltec.cosecha.R;
import com.grevoltec.cosecha.app.AppCosecha;
import com.grevoltec.cosecha.entities.CosechaEntity;
import com.grevoltec.cosecha.entities.PalletEntity;
import com.grevoltec.cosecha.util.AppException;
import com.grevoltec.cosecha.util.QrUtils;
import com.grevoltec.cosecha.views.secure.fragments.core.AppViewPager;
import com.grevoltec.cosecha.views.secure.fragments.core.fragment.AbsFragment;
import com.grevoltec.cosecha.views.secure.fragments.core.fragment.AbsPagerAdapter;
import com.grevoltec.cosecha.views.secure.fragments.pallet.core.PaltJabaLeerFragment_;
import com.grevoltec.cosecha.views.secure.fragments.pallet.core.PaltPalletCrearFragment_;
import com.grevoltec.cosecha.views.secure.fragments.pallet.core.PaltPalletResumenFragment_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EFragment(R.layout.core_tabs)
public class PaltPalletCrearTabFragment extends AbsFragment implements IPaltCrear {

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
    protected PaltPalletCrearTabFragment that;

    protected String qrHeadPalletSelected;
    protected List<PalletEntity> items;
    protected IPaltJabaChangeListener listener;

    @AfterViews
    protected void onAfterViews() {
        this.that = this;
        this.items = new ArrayList<>();
        setupViewPager(viewpager);
        btnAction.setText(R.string.grabar);
    }

    private void loadDataQRS(){
        String[] data = new String[]{
                "J0462243602950AMARANTAJUAN0340170501",
                "J0462244602950AMARANTEJUAN0440170501",
                "J0462245602950AMARANTIJUAN0540170501",
                "J0462246602950AMARANTOJUAN0640170501",
                "J0462247602950AMARANTUJUAN0740170501",
                "J0462248602950AMARANTOJUAN0840170501",
                "J0462249602950AMARENTOJUAN0940170501",
                "J0462243702950AMARINTOJUAN1040170501",
                "J0462243802950AMARONTOJUAN1140170501",
                "J0462243902950AMORUNTOJUAN1240170501",
                "J0462243612950AMARANTOJUAN1340170501",
                "J0462243622950AMERANTOJUAN1440170501",
                "J0462243632950AMIRANTOJUAN1540170501"
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
        toolbar.setTitle(R.string.crear_pallete);
        tabs.addTab(tabs.newTab().setText(R.string.palt_crear_pallet));
        tabs.addTab(tabs.newTab().setText(R.string.palt_leer_jaba));
        tabs.addTab(tabs.newTab().setText(R.string.palt_resumen));
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
    public void goToSecondStep(String qrHeadPalletSelected) {
        this.qrHeadPalletSelected = qrHeadPalletSelected;
        step = 2;
        setTab(1);
        if(AppCosecha.IsTest) {
            loadDataQRS();
        }
    }



    @Override
    public void validateQRPallet(String qr) throws AppException {
        QrUtils.QR_PALLETS.validateQR(qr);
    }

    @Override
    public void validateQRJaba(String qr) throws AppException {
        QrUtils.QR_JABAS.validateQR(qr,false);
        for (PalletEntity entity: items){
            if(qr.equalsIgnoreCase(entity.getQrjaba())) throw new AppException(getString(R.string.qr_jaba_ya_leido));
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
        validateQRJaba(qr);
        PalletEntity entity = new PalletEntity();

        entity.setIduser(AppCosecha.getUserLogin().getIdUsuario());
        entity.setImeiequipo(AppCosecha.getImei());
        entity.setNroequipo("");
        entity.setQrpallet(qrHeadPalletSelected);
        entity.setQrjaba(qr);
        entity.setPalletcomp(0);
        entity.setHorajaba(AppCosecha.getDate());
        entity.setHorapallet(AppCosecha.getDate());

        try{
            CosechaEntity cosechaEntity = AppCosecha.getHelper().getCosechaDao().queryBuilder()
                    .where().eq("codigo_qr", qr).queryForFirst();
            entity.setCosecha(cosechaEntity);
        }catch (Exception ex){
            ex.printStackTrace();
        }

        items.add(entity);

        if(listener != null){
            listener.onJabaChange();
        }
    }

    @Override
    public List<PalletEntity> getPallets() {
        return items;
    }

    @Override
    public String getQrHeadPallet() {
        return qrHeadPalletSelected;
    }

    @Override
    public void setOnPalletsListener(IPaltJabaChangeListener listener) {
        this.listener = listener;
    }

    @Override
    public IPaltJabaChangeListener getOnPalletsListener() {
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
                    return new PaltPalletCrearFragment_().setOnStepChangeListener(that);
                case 1:
                    return new PaltJabaLeerFragment_().setOnStepChangeListener(that);
                case 2:
                    return new PaltPalletResumenFragment_().setOnStepChangeListener(that);
                default:
                    throw new RuntimeException("Tab position invalid " + position);
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getContext().getString(R.string.palt_crear_pallet);
                case 1:
                    return getContext().getString(R.string.palt_leer_jaba);
                case 2:
                    return getContext().getString(R.string.palt_resumen);
                default:
                    throw new RuntimeException("Tab position invalid " + position);
            }
        }

    }

}