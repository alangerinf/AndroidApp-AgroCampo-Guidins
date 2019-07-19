package com.grevoltec.cosecha.views.secure.fragments.pallet;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.grevoltec.cosecha.R;
import com.grevoltec.cosecha.util.AppException;
import com.grevoltec.cosecha.util.QrUtils;
import com.grevoltec.cosecha.views.secure.fragments.core.AppViewPager;
import com.grevoltec.cosecha.views.secure.fragments.core.fragment.AbsFragment;
import com.grevoltec.cosecha.views.secure.fragments.core.fragment.AbsPagerAdapter;
import com.grevoltec.cosecha.views.secure.fragments.pallet.core.PaltPalletEditarFragment_;
import com.grevoltec.cosecha.views.secure.fragments.pallet.core.PaltPalletSelectFragment;
import com.grevoltec.cosecha.views.secure.fragments.pallet.core.PaltPalletSelectFragment_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.core_tabs)
public class PaltPalletModificaTabFragment extends AbsFragment implements IPaltModifica {

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
    protected PaltPalletModificaTabFragment that;

    protected String qrHeadPalletSelected;
    protected IPaltModificaChangeListener listener;

    @AfterViews
    protected void onAfterViews() {
        this.that = this;
        setupViewPager(viewpager);
        btnAction.setText(R.string.grabar);
    }

    private void setupViewPager(final AppViewPager viewPager) {
        goToFirstStep();
        toolbar.setTitle(R.string.modificar_pallete);
        tabs.addTab(tabs.newTab().setText(R.string.pallet));
        tabs.addTab(tabs.newTab().setText(R.string.qr));
        tabs.setTabGravity(TabLayout.GRAVITY_FILL);
        viewPager.setPagingEnabled(false);
        viewPager.setAdapter(adapter = new PagerAdapter(getActivity().getSupportFragmentManager(), 2));
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
                        btnAction.setVisibility( tab.getPosition() == 1 ? View.VISIBLE : View.INVISIBLE );
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
    public void goToSecondStep(String qrPalletSelected) {
        this.qrHeadPalletSelected = qrPalletSelected;
        step = 2;
        setTab(1);
    }

    @Override
    public void readQR(String qr) throws AppException {
        QrUtils.QR_PALLETS.validateQR(qr);
    }

    @Override
    public void validateQR(String qr) throws AppException {
        QrUtils.QR_PALLETS.validateQR(qr);
        if(this.qrHeadPalletSelected.compareToIgnoreCase(qr) == 0) throw new AppException(getString(R.string.qr_debe_ser_diferente));
    }

    @Override
    public Button getActionButton() {
        return btnAction;
    }

    @Override
    public String getQrHeadPallet() {
        return this.qrHeadPalletSelected;
    }

    @Override
    public void setListener(IPaltModificaChangeListener listener) {
        this.listener = listener;
    }

    public void setTab(int idx){
        if(viewpager!= null) viewpager.setCurrentItem(idx);
        if(tabs!= null){
            TabLayout.Tab tab1 = tabs.getTabAt(idx);
            if(tab1 != null) tab1.select();
        }
    }

    @Click(R.id.btnAction)
    protected void onClickActionButton(){
        if(listener != null){
            listener.onClickActionButton();
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
                    return new PaltPalletSelectFragment_().setType(PaltPalletSelectFragment.TYPE_EDIT_PALLET).setListenerModifica(that);
                case 1:
                    return new PaltPalletEditarFragment_().setOnStepChangeListener(that);
                default:
                    throw new RuntimeException("Tab position invalid " + position);
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.pallets);
                case 1:
                    return getString(R.string.qr);
                default:
                    throw new RuntimeException("Tab position invalid " + position);
            }
        }

    }

}