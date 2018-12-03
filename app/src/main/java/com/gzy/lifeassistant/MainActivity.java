package com.gzy.lifeassistant;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gzy.lifeassistant.model.WordBookBean;
import com.gzy.lifeassistant.utils.AssetsUtils;

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

        initData();
    }

    private void initData() {
        String jsonString = AssetsUtils.getString("cet_4_word_database.json", MainActivity.this);
        Type listType = new TypeToken<WordBookBean>() {
        }.getType();
        WordBookBean wordBookBean = new Gson().fromJson(jsonString, listType);

    }
}
