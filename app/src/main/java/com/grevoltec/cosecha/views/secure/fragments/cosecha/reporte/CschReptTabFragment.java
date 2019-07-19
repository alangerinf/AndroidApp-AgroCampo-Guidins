package com.grevoltec.cosecha.views.secure.fragments.cosecha.reporte;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import android.support.v7.widget.Toolbar;

import com.grevoltec.cosecha.R;
import com.grevoltec.cosecha.views.secure.fragments.core.AppViewPager;
import com.grevoltec.cosecha.views.secure.fragments.core.fragment.AbsFragment;
import com.grevoltec.cosecha.views.secure.fragments.core.fragment.AbsPagerAdapter;
import com.grevoltec.cosecha.views.secure.fragments.cosecha.reporte.core.CschReptCosechadorFragment_;
import com.grevoltec.cosecha.views.secure.fragments.cosecha.reporte.core.CschReptDniFragment_;
import com.grevoltec.cosecha.views.secure.fragments.cosecha.reporte.core.CschReptTurnoFragment_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.core_tabs)
public class CschReptTabFragment extends AbsFragment {

    @ViewById(R.id.toolbar)
    protected Toolbar toolbar;
    @ViewById(R.id.tabs)
    protected TabLayout tabs;
    @ViewById(R.id.viewpager)
    protected AppViewPager viewpager;
    protected PagerAdapter adapter;

    @AfterViews
    protected void onAfterViews() {
        setupViewPager(viewpager);
    }

    private void setupViewPager(final AppViewPager viewPager) {
        toolbar.setTitle(R.string.cschrept_reporte);
        tabs.addTab(tabs.newTab().setText(R.string.cschrept_cosechador));
        tabs.addTab(tabs.newTab().setText(R.string.cschrept_turno));
        tabs.addTab(tabs.newTab().setText(R.string.cschrept_dni));
        tabs.setTabGravity(TabLayout.GRAVITY_FILL);
        viewPager.setPagingEnabled(false);
        viewPager.setAdapter(adapter = new PagerAdapter(getActivity().getSupportFragmentManager(), 3));
        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }
            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });
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
                    return new CschReptCosechadorFragment_();
                case 1:
                    return new CschReptTurnoFragment_();
                case 2:
                    return new CschReptDniFragment_();
                default:
                    throw new RuntimeException("Tab position invalid " + position);
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getContext().getString(R.string.cschrept_cosechador);
                case 1:
                    return getContext().getString(R.string.cschrept_turno);
                case 2:
                    return getContext().getString(R.string.cschrept_dni);
                default:
                    throw new RuntimeException("Tab position invalid " + position);
            }
        }

    }

}