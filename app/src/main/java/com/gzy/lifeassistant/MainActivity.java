package com.gzy.lifeassistant;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

/**
 * MainActivity
 *
 * @author gaozongyang
 * @date 2018/11/28
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String jsonString = getStringFromAssets("cet_4_word_database.json", MainActivity.this);
        Type listType = new TypeToken<WordBookBean>() {
        }.getType();
        WordBookBean wordBookBean = new Gson().fromJson(jsonString, listType);
        for (WordBookBean.WordbookBean.ItemBean itemBean : wordBookBean.getWordbook().getItem()) {
            Log.e("gzy", itemBean.toString());
        }
    }

    public static String getStringFromAssets(String fileName, Context context) {
        //将json数据变成字符串
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //获取assets资源管理器
            AssetManager assetManager = context.getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
