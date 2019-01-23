package com.gzy.lifeassistant.permission;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.PermissionChecker;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 动态权限申请工具类
 *
 * @author gaozongyang
 * @date 2019/1/22
 */
public class PermissionUtil {

    private static Map<Integer, RequestCallBack> requestPermissionCallBackMap = new HashMap<>();

    public static void requestPermission(@NonNull Context context, @NonNull String[] permissions,
                                         @NonNull RequestCallBack callBack) {
        if (hasPermission(context, permissions)) {
            callBack.granted();
        } else {
            Intent intent = new Intent(context, PermissionActivity.class);
            intent.putExtra(PermissionActivity.PERMISSION_ARRAY, permissions);
            int uuid = generateRandomNum();
            intent.putExtra(PermissionActivity.PERMISSION_REQUEST_CODE, uuid);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            requestPermissionCallBackMap.put(uuid, callBack);
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

    private static int generateRandomNum() {
        int num;
        do {
            num = new Random().nextInt(1000);
        } while (requestPermissionCallBackMap.containsKey(num));
        return num;
    }

    static RequestCallBack getRequestPermissionCallBack(int requestCode) {
        return requestPermissionCallBackMap.get(requestCode);
    }

    static void removeRequestPermissionCallBack(int packageCodePath) {
        requestPermissionCallBackMap.remove(packageCodePath);
    }
}