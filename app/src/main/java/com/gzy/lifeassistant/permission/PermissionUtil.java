package com.gzy.lifeassistant.permission;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.PermissionChecker;

import java.util.HashMap;
import java.util.Map;

/**
 * 动态权限申请工具类
 *
 * @author gaozongyang
 * @date 2019/1/22
 */
public class PermissionUtil {

    private static Map<String, RequestPermissionCallBack> requestPermissionCallBackMap = new HashMap<>();

    public static void requestPermission(@NonNull Context context, @NonNull RequestPermissionCallBack callBack,
                                         @NonNull String... permissions) {
        if (hasPermission(context, permissions)) {
            callBack.granted();
        } else {
            Intent intent = new Intent(context, PermissionActivity.class);
            intent.putExtra("permission", permissions);
            intent.putExtra("packageCodePath", context.getPackageCodePath());
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            requestPermissionCallBackMap.put(context.getPackageCodePath(), callBack);
        }
    }

    private static boolean hasPermission(Context context, String... permissions) {
        boolean isAllGranted = true;
        for (String permission : permissions) {
            if (PermissionChecker.checkSelfPermission(context, permission) != PermissionChecker.PERMISSION_GRANTED) {
                isAllGranted = false;
            }
        }
        return isAllGranted;
    }

    static RequestPermissionCallBack getRequestPermissionCallBack(String packageCodePath) {
        return requestPermissionCallBackMap.get(packageCodePath);
    }

    static void removeRequestPermissionCallBack(String packageCodePath) {
        requestPermissionCallBackMap.remove(packageCodePath);
    }
}