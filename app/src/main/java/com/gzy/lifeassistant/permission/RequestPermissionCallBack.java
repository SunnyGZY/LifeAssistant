package com.gzy.lifeassistant.permission;

/**
 * 权限申请结果返回接口
 *
 * @author gaozongyang
 * @date 2019/1/22
 */
public interface RequestPermissionCallBack {
    /**
     * 同意授权
     */
    void granted();

    /**
     * 取消授权
     */
    void denied();
}