package com.gzy.lifeassistant.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

/**
 * SharedPreferences 工具类
 *
 * @author gaozongyang
 * @date 2018/12/4
 */
public class SpUtils {

    private static final String SP_NAME = "life_assistant_sp";

    /**
     * 存字符串
     *
     * @param context 上下文
     * @param key     键
     * @param values  取值
     */
    public static void putString(Context context, String key, String values) {
        SharedPreferences sp = getSharedPreferences(context);
        sp.edit().putString(key, values).apply();
    }

    /**
     * 取字符串
     *
     * @param context 上下文
     * @param key     键
     * @param values  默认值
     * @return 取值
     */
    public static String getString(Context context, String key, String values) {
        SharedPreferences sp = getSharedPreferences(context);
        return sp.getString(key, values);
    }

    /**
     * 存字符串集合
     *
     * @param context 上下文
     * @param key     键
     * @param values  取值
     */
    public static void putStrings(Context context, String key, Set<String> values) {
        SharedPreferences sp = getSharedPreferences(context);
        sp.edit().putStringSet(key, values).apply();
    }

    /**
     * 取字符串集合
     *
     * @param context 上下文
     * @param key     键
     * @return 取值
     */
    public static Set<String> getStrings(Context context, String key) {
        SharedPreferences sp = getSharedPreferences(context);
        return sp.getStringSet(key, null);
    }


    /**
     * 存布尔值
     *
     * @param context 上下文
     * @param key     键
     * @param values  取值
     */
    public static void putBoolean(Context context, String key, boolean values) {
        SharedPreferences sp = getSharedPreferences(context);
        sp.edit().putBoolean(key, values).apply();
    }


    /**
     * 取布尔值
     *
     * @param context 上下文
     * @param key     键
     * @param values  默认值
     * @return true/false
     */
    public static boolean getBoolean(Context context, String key, boolean values) {
        SharedPreferences sp = getSharedPreferences(context);
        return sp.getBoolean(key, values);
    }


    /**
     * 存int值
     *
     * @param context 上下文
     * @param key     键
     * @param values  值
     */
    public static void putInt(Context context, String key, int values) {
        SharedPreferences sp = getSharedPreferences(context);
        sp.edit().putInt(key, values).apply();
    }

    /**
     * 取int值
     *
     * @param context 上下文
     * @param key     键
     * @param values  默认值
     * @return 取值
     */
    public static int getInt(Context context, String key, int values) {
        SharedPreferences sp = getSharedPreferences(context);
        return sp.getInt(key, values);
    }

    /**
     * 删除一条字段
     *
     * @param context 上下文
     * @param key     键
     */
    public static void deleShare(Context context, String key) {
        SharedPreferences sp = getSharedPreferences(context);
        sp.edit().remove(key).apply();
    }

    /**
     * 删除全部数据
     *
     * @param context 上下文
     */
    public static void deleShareAll(Context context) {
        SharedPreferences sp = getSharedPreferences(context);
        sp.edit().clear().apply();
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(SpUtils.SP_NAME, Context.MODE_PRIVATE);
    }
}
