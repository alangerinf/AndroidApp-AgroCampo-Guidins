package com.grevoltec.cosecha;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.grevoltec.cosecha.app.AppCosecha;
import com.grevoltec.cosecha.entities.UsuarioEntity;
import com.grevoltec.cosecha.views.access.fragments.auth.LoginFragment_;
import com.grevoltec.cosecha.views.access.fragments.auth.SplashFragment_;

import java.sql.SQLException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onPostViews(savedInstanceState);

        try {
            for(UsuarioEntity user : AppCosecha.getHelper().getUsuarioDao().queryForAll()){
                AppCosecha.setUserLogin(user);
                loadActivitySecure();
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    protected void onPostViews(Bundle savedInstanceState){
        /* Load Fragment */
        if (findViewById(R.id.main_fragment) != null) {
            if (savedInstanceState != null) {
                return;
            }
            SplashFragment_ firstFragment = new SplashFragment_();
            firstFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.enter_fade, R.anim.exit_fade)
                    .add(R.id.main_fragment, firstFragment).commit();
        }
    }

    public void loadFragmentLogin() {
        LoginFragment_ firstFragment = new LoginFragment_();
        firstFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
                .add(R.id.main_fragment, firstFragment).commit();
    }

    public void loadActivitySecure() {
        Intent intent = new Intent(this, SecureActivity.class);
        finish();
        startActivity(intent);
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