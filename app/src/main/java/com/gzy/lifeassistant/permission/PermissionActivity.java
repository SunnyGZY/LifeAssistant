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

    public static final String PERMISSION_ARRAY = "permission_array";

    public static final String PERMISSION_REQUEST_CODE = "permission_request_code";

    private String[] permissions;
    private int permissionRequestCode;
    private RequestCallBack requestPermissionCallBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        permissions = intent.getStringArrayExtra(PERMISSION_ARRAY);
        permissionRequestCode = intent.getIntExtra(PERMISSION_REQUEST_CODE, -1);
        requestPermissionCallBack = PermissionUtil.getRequestPermissionCallBack(permissionRequestCode);
        ActivityCompat.requestPermissions(this, permissions, permissionRequestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == permissionRequestCode) {
            for (int i = 0; i < grantResults.length; ++i) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    // 有用户选择了不再提醒的权限
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) {
                        showGoAppSettingDialog();
                    } else {
                        requestCallBack(false);
                    }
                    return;
                }
            }
        }
        requestCallBack(true);
    }

    private void showGoAppSettingDialog() {
        new AlertDialog.Builder(this).setTitle("PermissionTest")
                .setMessage("获取相关权限失败:" +
                        "将导致部分功能无法正常使用，需要到设置页面手动授权")
                .setPositiveButton("去授权", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO: 19-1-23 此处如何监听到用户授予权限？
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getApplicationContext().getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                        dialog.dismiss();
                        finish();
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

    /**
     * 回调接口
     *
     * @param allGranted true 全部授权
     *                   false 未全部授权
     */
    private void requestCallBack(boolean allGranted) {
        if (allGranted) {
            requestPermissionCallBack.granted();
        } else {
            requestPermissionCallBack.denied();
        }
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PermissionUtil.removeRequestPermissionCallBack(permissionRequestCode);
    }
}
