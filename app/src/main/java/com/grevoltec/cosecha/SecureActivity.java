package com.grevoltec.cosecha;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.grevoltec.cosecha.views.secure.fragments.DashboardFragment_;
import com.grevoltec.cosecha.views.secure.fragments.cosecha.jaba.CschJabaTabFragment_;
import com.grevoltec.cosecha.views.secure.fragments.cosecha.reporte.CschReptTabFragment_;
import com.grevoltec.cosecha.views.secure.fragments.despacho.DespViajeCrearTabFragment_;
import com.grevoltec.cosecha.views.secure.fragments.pallet.PaltPalletCrearTabFragment_;
import com.grevoltec.cosecha.views.secure.fragments.pallet.core.PaltPalletCrearFragment;
import com.grevoltec.cosecha.views.secure.fragments.pallet.core.PaltPalletCrearFragment_;
import com.grevoltec.cosecha.views.secure.fragments.recepcion.RecepPalletTabFragment_;

public class SecureActivity extends AppCompatActivity {

    private static final String BACK_STACK_ROOT_TAG = "root_fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secure);
        /* Load Fragment */
        if (findViewById(R.id.main_fragment) != null) {
            if (savedInstanceState != null) {
                return;
            }
            Fragment firstFragment = new DashboardFragment_();
            firstFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().popBackStack(BACK_STACK_ROOT_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.enter_fade, R.anim.exit_fade)
                    .add(R.id.main_fragment, firstFragment)
                    .addToBackStack(BACK_STACK_ROOT_TAG)
                    .commit();
        }
    }

    public void SetFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.enter_fade, R.anim.exit_fade)
                .replace(R.id.main_fragment, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackPressed() {
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.main_fragment);
        int fragments = getSupportFragmentManager().getBackStackEntryCount();


        if(     f instanceof CschJabaTabFragment_ ||
                f instanceof PaltPalletCrearTabFragment_ ||
                f instanceof DespViajeCrearTabFragment_ ||
                f instanceof RecepPalletTabFragment_
        ){
            final Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.dialog_return);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            Button dialog_positive = dialog.findViewById(R.id.dialog_positive);
            Button dialog_negative = dialog.findViewById(R.id.dialog_negative);

            dialog_positive.setOnClickListener(v->{
                dialog.dismiss();
                super.onBackPressed();
            });
            dialog_negative.setOnClickListener(v->{
                dialog.dismiss();
            });
            dialog.show();
        }else{
           // Toast.makeText(this,f.getClass().getSimpleName(),Toast.LENGTH_SHORT).show();

            if (fragments == 1) {
                finish();
                return;
            }
            super.onBackPressed();

        }


    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(ev.getAction() == MotionEvent.ACTION_UP) {
            final View view = getCurrentFocus();
            if(view != null) {
                final boolean consumed = super.dispatchTouchEvent(ev);
                final View viewTmp = getCurrentFocus();
                final View viewNew = viewTmp != null ? viewTmp : view;
                if(viewNew.equals(view)) {
                    final Rect rect = new Rect();
                    final int[] coordinates = new int[2];
                    view.getLocationOnScreen(coordinates);
                    rect.set(coordinates[0], coordinates[1], coordinates[0] + view.getWidth(), coordinates[1] + view.getHeight());
                    final int x = (int) ev.getX();
                    final int y = (int) ev.getY();
                    if(rect.contains(x, y)) {
                        return consumed;
                    }
                }
                else if(viewNew instanceof EditText) {
                    return consumed;
                }
                final InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(viewNew.getWindowToken(), 0);
                viewNew.clearFocus();
                return consumed;
            }
        }
        return super.dispatchTouchEvent(ev);
    }

}