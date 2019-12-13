package com.grevoltec.cosecha.views.secure.fragments.core.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.PointF;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.FrameLayout;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.grevoltec.cosecha.PointsOverlayView;
import com.grevoltec.cosecha.R;

import java.util.Date;

public abstract class AbsQrFragment extends AbsFragment implements ActivityCompat.OnRequestPermissionsResultCallback,git s {

    protected static final int MY_PERMISSION_REQUEST_CAMERA = 0;

    protected FrameLayout qrLayout;
    protected View qrImage;
    protected QRCodeReaderView qrCodeReaderView;
    protected PointsOverlayView pointsOverlayView;

    protected void setupQRCodeReader(){
        qrLayout = getView().findViewById(R.id.layQR);
        qrImage = getView().findViewById(R.id.qrImage);
        qrCodeReaderView = getView().findViewById(R.id.qrdecoderview);
        pointsOverlayView = getView().findViewById(R.id.points_overlay_view);

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            initQRCodeReaderView();
        } else {
            requestCameraPermission();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(qrCodeReaderView != null){
            stopCameraQR();
        }
    }

    protected void onClickQRImage(){
        startCameraQR();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != MY_PERMISSION_REQUEST_CAMERA) {
            return;
        }
        if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Snackbar.make(qrLayout, "Camera permission was granted.", Snackbar.LENGTH_SHORT).show();
            initQRCodeReaderView();
        } else {
            Snackbar.make(qrLayout, "Camera permission request was denied.", Snackbar.LENGTH_SHORT)
                    .show();
        }
    }

    @Override
    public void onQRCodeRead(String text, PointF[] points) {
        pointsOverlayView.setPoints(points);
        onQRCodeRead(text);
    }

    protected void onQRCodeRead(String text){}

    protected void requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA)) {
            Snackbar.make(qrLayout, "Camera access is required to display the camera preview.",
                    Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ActivityCompat.requestPermissions(getActivity(), new String[] {
                            Manifest.permission.CAMERA
                    }, MY_PERMISSION_REQUEST_CAMERA);
                }
            }).show();
        } else {
            Snackbar.make(qrLayout, "Permission is not available. Requesting camera permission.",
                    Snackbar.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(getActivity(), new String[] {
                    Manifest.permission.CAMERA
            }, MY_PERMISSION_REQUEST_CAMERA);
        }
    }

    protected void initQRCodeReaderView() {
        qrCodeReaderView.setAutofocusInterval(2000L);
        qrCodeReaderView.setOnQRCodeReadListener(this);
        qrCodeReaderView.setBackCamera();
        qrImage.setOnClickListener(new View.OnClickListener() {
            private Date iNow = new Date();
            @Override
            public void onClick(View v) {
                if(pointsOverlayView.getVisibility() == View.GONE){
                    onClickQRImage();
                    iNow = new Date();
                }else{
                    Date now = new Date();
                    long sus = now.getTime() - iNow.getTime();
                    if(sus > 800){
                        stopCameraQR();
                    }
                }
            }
        });
    }

    protected void startCameraQR(){
        qrCodeReaderView.setVisibility(View.VISIBLE);
        pointsOverlayView.setVisibility(View.VISIBLE);
        qrCodeReaderView.startCamera();
    }

    protected void stopCameraQR(){
        qrCodeReaderView.setVisibility(View.GONE);
        pointsOverlayView.setVisibility(View.GONE);
        qrCodeReaderView.stopCamera();
    }

}
