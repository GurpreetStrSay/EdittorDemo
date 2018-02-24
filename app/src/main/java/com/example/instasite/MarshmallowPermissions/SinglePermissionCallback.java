package com.example.instasite.MarshmallowPermissions;


public interface SinglePermissionCallback {

    void onPermissionResult(boolean permissionGranted, boolean isPermissionDeniedForever);
}
