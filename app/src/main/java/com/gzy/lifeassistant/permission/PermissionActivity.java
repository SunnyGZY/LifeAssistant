package com.gzy.lifeassistant.permission;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

/**
 * 权限申请
 *
 * @author gaozongyang
 * @date 2019/1/22
 */
public class PermissionActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 0x01;

    private String[] permissions;
    private RequestPermissionCallBack requestPermissionCallBack;
    private String packageCodePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        permissions = intent.getStringArrayExtra("permission");
        packageCodePath = intent.getStringExtra("packageCodePath");
        requestPermissionCallBack = PermissionUtil.getRequestPermissionCallBack(packageCodePath);
        ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean hasAllGranted = true;
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                for (int i = 0; i < grantResults.length; ++i) {
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        hasAllGranted = false;
                        if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) {
                            goAppSettingDialog();
                        }
                    }
                }
                break;
            default:
                break;
        }
        if (hasAllGranted) {
            requestCallBack(true);
        }
    }

    private void goAppSettingDialog() {
        new AlertDialog.Builder(this).setTitle("PermissionTest")
                .setMessage("获取相关权限失败:" +
                        "将导致部分功能无法正常使用，需要到设置页面手动授权")
                .setPositiveButton("去授权", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getApplicationContext().getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        requestCallBack(false);
                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        requestCallBack(false);
                    }
                }).show();
    }

    private void requestCallBack(boolean isAllGranted) {
        if (isAllGranted) {
            requestPermissionCallBack.granted();
            finish();
        } else {
            requestPermissionCallBack.denied();
        }
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PermissionUtil.removeRequestPermissionCallBack(packageCodePath);
    }
}
