package com.grevoltec.cosecha.views.secure.fragments.cosecha.jaba;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.grevoltec.cosecha.R;
import com.grevoltec.cosecha.app.AppCosecha;
import com.grevoltec.cosecha.entities.CosechaEntity;
import com.grevoltec.cosecha.entities.FundoEntity;
import com.grevoltec.cosecha.entities.TurnoEntity;
import com.grevoltec.cosecha.util.AppException;
import com.grevoltec.cosecha.util.QrUtils;
import com.grevoltec.cosecha.views.secure.fragments.core.AppViewPager;
import com.grevoltec.cosecha.views.secure.fragments.core.fragment.AbsFragment;
import com.grevoltec.cosecha.views.secure.fragments.core.fragment.AbsPagerAdapter;
import com.grevoltec.cosecha.views.secure.fragments.cosecha.jaba.core.CschJabaLeerFragment_;
import com.grevoltec.cosecha.views.secure.fragments.cosecha.jaba.core.CschJabaResumenFragment_;
import com.grevoltec.cosecha.views.secure.fragments.cosecha.jaba.core.CschSelFundoFragment_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EFragment(R.layout.core_tabs)
public class CschJabaTabFragment extends AbsFragment implements ICschJaba {

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
    protected CschJabaTabFragment that;

    protected FundoEntity fundoSelected;
    protected TurnoEntity turnoSelected;
    protected int cantPersonas;
    protected List<CosechaEntity> cosecha;
    protected ICschJabaChangeListener listener;

    @AfterViews
    protected void onAfterViews() {
        this.that = this;
        this.cosecha = new ArrayList<>();
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
        toolbar.setTitle(R.string.csch_lectura_jaba);
        tabs.addTab(tabs.newTab().setText(R.string.csch_lecturar));
        tabs.addTab(tabs.newTab().setText(R.string.csch_qr));
        tabs.addTab(tabs.newTab().setText(R.string.csch_resumen));
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
    public void goToSecondStep(FundoEntity fundoSelected, TurnoEntity turnoSelected,int cantPersonas) {
        this.fundoSelected = fundoSelected;
        this.turnoSelected = turnoSelected;
        this.cantPersonas = cantPersonas;

        step = 2;
        setTab(1);
        if(AppCosecha.IsTest) {
            loadDataQRS();
        }
    }

    @Override
    public void validateQR(String qr) throws AppException {
        QrUtils.QR_JABAS.validateQR(qr);
        for (CosechaEntity entity: cosecha) {
            if(qr.equalsIgnoreCase(entity.getCodigoqr())) throw new AppException(getString(R.string.qr_ya_leido));
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
        validateQR(qr);
        CosechaEntity entity = new CosechaEntity();

        entity.setIdemp(AppCosecha.getUserLogin().getIdEmpresa());
        entity.setIdtur(turnoSelected.getIdTurno());
        entity.setCantPersonas(cantPersonas);
        entity.setCodigoqr(qr);
        entity.setIduser(AppCosecha.getUserLogin().getIdUsuario());
        entity.setDni(QrUtils.QR_JABAS.getDNI(qr));
        entity.setCosechador(QrUtils.QR_JABAS.getCosechador(qr));
        entity.setImeiequipo(AppCosecha.getImei());

        entity.setNroetiqueta(QrUtils.QR_JABAS.getNroEtiqueta(qr));
        entity.setTotaletiqueta(QrUtils.QR_JABAS.getTotalEtiqueta(qr));

        try{ entity.setTotetiqueta(Integer.parseInt(QrUtils.QR_JABAS.getTotalEtiqueta(qr))); }
        catch (Exception ex){ ex.printStackTrace(); }

        entity.setHoraenvase(AppCosecha.getDate());
        entity.setEnvaseper(0);
        entity.setNroequipo("");
        entity.setEnvaseobs(0);

        cosecha.add(entity);

        if(listener != null){
            listener.onCosechaChange();
        }

    }

    @Override
    public List<CosechaEntity> getCosecha() {
        return cosecha;
    }

    @Override
    public TurnoEntity getTurnoSelected() {
        return turnoSelected;
    }

    @Override
    public void setOnCosechaListener(ICschJabaChangeListener listener) {
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
                    return new CschSelFundoFragment_().setOnStepChangeListener(that);
                case 1:
                    return new CschJabaLeerFragment_().setOnStepChangeListener(that);
                case 2:
                    return new CschJabaResumenFragment_().setOnStepChangeListener(that);
                default:
                    throw new RuntimeException("Tab position invalid " + position);
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getContext().getString(R.string.csch_lecturar);
                case 1:
                    return getContext().getString(R.string.csch_qr);
                case 2:
                    return getContext().getString(R.string.csch_resumen);
                default:
                    throw new RuntimeException("Tab position invalid " + position);
            }
        }

    }

}