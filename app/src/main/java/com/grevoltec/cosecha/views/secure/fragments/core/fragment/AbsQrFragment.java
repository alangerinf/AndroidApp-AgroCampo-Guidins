package com.grevoltec.cosecha.views.secure.fragments.core.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.PointF;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.ResultPoint;
import com.google.zxing.client.android.BeepManager;
import com.grevoltec.cosecha.R;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.journeyapps.barcodescanner.DefaultDecoderFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public abstract class AbsQrFragment extends AbsFragment implements ActivityCompat.OnRequestPermissionsResultCallback, DecoratedBarcodeView.TorchListener{

    protected static final int MY_PERMISSION_REQUEST_CAMERA = 0;

    protected FrameLayout qrLayout;
    protected View qrImage;

        private BeepManager beepManager;
    private DecoratedBarcodeView barcodeScannerView;

    protected void setupQRCodeReader(){
        qrLayout = getView().findViewById(R.id.layQR);
        qrImage = getView().findViewById(R.id.qrImage);

        barcodeScannerView = getView().findViewById(R.id.zxing_barcode_scanner);
        barcodeScannerView.setTorchListener(this);

        Collection<BarcodeFormat> formats = Arrays.asList(BarcodeFormat.QR_CODE, BarcodeFormat.CODE_39,BarcodeFormat.CODABAR);
        barcodeScannerView.getBarcodeView().setDecoderFactory(new DefaultDecoderFactory(formats));
        barcodeScannerView.initializeFromIntent(getActivity().getIntent());
        barcodeScannerView.decodeContinuous(callback);

        beepManager = new BeepManager(getActivity());

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            initQRCodeReaderView();
        } else {
            requestCameraPermission();
        }
        list= new ArrayList<>();

    }

    protected void onQRCodeRead(String text) {

    }

    private List<String> list = new ArrayList<>();
    private BarcodeCallback callback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result) {

            if(result!=null){
                for(String temp :list){
                    if(temp.equals(result.getText())){
                        return;
                    }
                }
                list.add(result.getText());
                onQRCodeRead(result.getText());
            }
            /*
            if(!isShowenError) {


                String DNI = result.getText();
                Log.d(TAG, "tamaño " + DNI.length());

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date();
                if (!HOUR.equals("")) {
                    String time[] = HOUR.split(":");
                    int hour = Integer.valueOf(time[0]);
                    int minutes = Integer.valueOf(time[1]);
                    date.setHours(hour);
                    date.setMinutes(minutes);
                    date = removeSeconds(date);
                }

                try {

                    if (!VerifyPersonal.verify(CustomScannerActivity.this,true, TAREOVO, PageViewModel.getMutable(), MY_EXTRA_MODE, DNI, formatter.format(date))) {//si s e rechazo
                        // Prevent duplicate scans
                        Log.d(TAG, DNI + "DUPLICADO");

                        return;
                    } else {
                        TareoDetalleVO tareoDetalleVO = new TareoDetalleVO();


                        TrabajadorVO trabajadorVO = new TrabajadorDAO(ctx).selectByDNI(DNI);
                        if(trabajadorVO==null){
                            trabajadorVO = new TrabajadorVO();
                            trabajadorVO.setDni(DNI);
                            trabajadorVO.setName("Sin Nombre");;
                        }
                        tareoDetalleVO.setTrabajadorVO(trabajadorVO);

                        PageViewModel.addTrabajador(tareoDetalleVO);

                        String mensaje = "ERROR";
                        switch (MY_EXTRA_MODE) {

                            case TabbetActivity.EXTRA_MODE_ADD_TRABAJADOR:
                                tareoDetalleVO.setTimeStart(formatter.format(date));
                                mensaje = "Entrada "+trabajadorVO.getName()+ " " + DNI + " " + formatter.format(date);
                                break;

                            case TabbetActivity.EXTRA_MODE_REMOVE_TRABAJADOR:
                                tareoDetalleVO.setTimeEnd(formatter.format(date));
                                mensaje = "Salida "+trabajadorVO.getName()+ " " + DNI + " " + formatter.format(date);
                                break;
                        }

                        barcodeScannerView.setStatusText(result.getText());


                        Snackbar snackbar = Snackbar.make(root, mensaje, Snackbar.LENGTH_SHORT);
                        snackbar.getView().setBackgroundColor(ContextCompat.getColor(ctx, R.color.colorAccent));
                        snackbar.show();

                        isShowenError = true;
                        handler.post(runnable);
                        handler.postDelayed(()->{
                            isShowenError = false;
                        },1500);

                    }
                } catch (Exception e) {

                    isShowenError = true;
                    handler.post(runnable);

                    handler.post(() -> {
                        Snackbar snackbar = Snackbar.make(root, e.getMessage(), Snackbar.LENGTH_SHORT);
                        snackbar.getView().setBackgroundColor(ContextCompat.getColor(ctx, R.color.red_pastel));
                        snackbar.show();
                    });

                    handler.postDelayed(()->{
                        isShowenError = false;
                    },1500);
                }
            }
        }

        Handler handler = new Handler();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                beepManager.setBeepEnabled(false);
                beepManager.setVibrateEnabled(true);
                beepManager.playBeepSoundAndVibrate();
            }
            */

        };



        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        if(barcodeScannerView != null){
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
            setupQRCodeReader();
        } else {
            Snackbar.make(qrLayout, "Camera permission request was denied.", Snackbar.LENGTH_SHORT)
                    .show();
        }
    }



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

        qrImage.setOnClickListener(new View.OnClickListener() {
            private Date iNow = new Date();
            @Override
            public void onClick(View v) {
                if(barcodeScannerView.getVisibility() == View.GONE){
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
        barcodeScannerView.resume();
        barcodeScannerView.setVisibility(View.VISIBLE);
    }

    protected void stopCameraQR(){

        barcodeScannerView.setVisibility(View.GONE);
        barcodeScannerView.pause();

    }

}
