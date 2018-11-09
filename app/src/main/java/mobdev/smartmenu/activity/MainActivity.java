package mobdev.smartmenu.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;

import mobdev.smartmenu.R;

public class MainActivity extends AppCompatActivity {

    SurfaceView cameraPreview;
    TextView txtResult;
    BarcodeDetector barcodeDetector;
    CameraSource cameraSource;
    final int REQUESTCAMERAPERMISSION = 1001;

    Button btnNext, btnLocal, btnProducts, btnReview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnNext = (Button) findViewById(R.id.btn_next);
        btnLocal = (Button) findViewById(R.id.btn_nextLocal);
        btnProducts = (Button) findViewById(R.id.btn_products);
        btnReview = (Button) findViewById(R.id.btn_review);

        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);

        // if previous table exists => show button
        if (!sharedPreferences.getString("tafelID", "0").substring(0, 1).contains("0")) {
            String text = sharedPreferences.getString("tafelID", "Table1");
            btnLocal.setText(text);
            saveData(text);
            btnLocal.setVisibility(View.VISIBLE);
            btnLocal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkInternet()) {
                        startActivity(new Intent(MainActivity.this, MasterActivity.class));
                    } else {
                        startActivity(new Intent(MainActivity.this, NoInternetActivity.class));
                    }
                    // startActivity(new Intent(MainActivity.this, MasterActivity.class));
                }
            });
        }

        // check if user is admin
        if (FirebaseAuth.getInstance().getCurrentUser().getEmail().equals("admin@hotmail.com")) {
            btnProducts.setVisibility(View.VISIBLE);
            btnProducts.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkInternet()) {
                        startActivity(new Intent(MainActivity.this, RestaurantProductActivity.class));
                    } else {
                        startActivity(new Intent(MainActivity.this, NoInternetActivity.class));
                    }
                    // startActivity(new Intent(MainActivity.this, RestaurantProductActivity.class));
                }
            });
        }

        // check if user is admin
        if (FirebaseAuth.getInstance().getCurrentUser().getEmail().equals("admin@hotmail.com")) {
            btnReview.setVisibility(View.VISIBLE);
            btnReview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkInternet()) {
                        startActivity(new Intent(MainActivity.this, ReviewsActivity.class));
                    } else {
                        startActivity(new Intent(MainActivity.this, NoInternetActivity.class));
                    }
                    //  startActivity(new Intent(MainActivity.this, ReviewsActivity.class));
                }
            });
        }

        // vip table
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkInternet()) {
                    saveData("VIP");
                    startActivity(new Intent(MainActivity.this, MasterActivity.class));
                } else {
                    startActivity(new Intent(MainActivity.this, NoInternetActivity.class));
                }
                //  saveData("VIP");
                // startActivity(new Intent(MainActivity.this, MasterActivity.class));
            }
        });

        cameraPreview = (SurfaceView) findViewById(R.id.camera);
        txtResult = (TextView) findViewById(R.id.txtTable);

        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();
        cameraSource = new CameraSource
                .Builder(this, barcodeDetector).setRequestedPreviewSize(640, 540)
                .build();

        cameraPreview.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, REQUESTCAMERAPERMISSION);
                    return;
                }

                try {
                    cameraSource.start(cameraPreview.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> qrcodes = detections.getDetectedItems();
                if (qrcodes.size() != 0) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Vibrator vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                            vibrator.vibrate(1000);
                            saveData(qrcodes.valueAt(0).displayValue);
                            cameraSource.stop();
                            cameraPreview.setVisibility(View.INVISIBLE);
                            if (checkInternet()) {
                                startActivity(new Intent(MainActivity.this, MasterActivity.class));
                            } else {
                                startActivity(new Intent(MainActivity.this, NoInternetActivity.class));
                            }
                            // startActivity(new Intent(MainActivity.this, MasterActivity.class));
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUESTCAMERAPERMISSION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }

                    try {
                        cameraSource.start(cameraPreview.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            break;
        }
    }

    // save table id in sharedpreferences
    public void saveData(String tafelId) {
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("tafelID", tafelId);
        editor.commit();
    }
    //method to check internet connectivity
    public boolean checkInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            return true;
        } else
            return false;
    }
}
