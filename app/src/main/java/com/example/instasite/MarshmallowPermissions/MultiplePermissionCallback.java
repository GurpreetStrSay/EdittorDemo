package com.example.instasite.MarshmallowPermissions;

import java.util.List;

public interface MultiplePermissionCallback {

    void onPermissionGranted(boolean allPermissionsGranted, List<Permission> grantedPermissions);

    void onPermissionDenied(List<Permission> deniedPermissions, List<Permission> foreverDeniedPermissions);
}
