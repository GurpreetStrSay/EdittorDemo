package com.example.instasite.edittordemo;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class SplashAct extends AppCompatActivity {
    public static final int RequestPermissionCode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_splash);
        startAnimation();
    }

    public void startAnimation() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (checkPermission()) {
                    ContinueApp();
                } else {
                    requestPermission();
                }
            }
        }, 2000);
    }

    public void ContinueApp() {
        Intent intent = new Intent(SplashAct.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(SplashAct.this, new String[]{
                CAMERA, WRITE_EXTERNAL_STORAGE}, RequestPermissionCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RequestPermissionCode:
                if (grantResults.length > 0) {
                    boolean CameraPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean ReadExternalStoragePermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (CameraPermission && ReadExternalStoragePermission) {
                        Toast.makeText(SplashAct.this, "Permission Granted", Toast.LENGTH_LONG).show();
                        ContinueApp();
                    } else {
                        Toast.makeText(SplashAct.this, "Permission Denied", Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }

    public boolean checkPermission() {
        int CameraPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        int ExternalStoragePermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        return CameraPermissionResult == PackageManager.PERMISSION_GRANTED &&
                ExternalStoragePermissionResult == PackageManager.PERMISSION_GRANTED;
    }
}
