package com.example.annuoaichengzhang.plugin;

import android.util.Log;

/**
 * Created by niehongtao on 16/4/23.
 */
public class PluginClass implements Comm {
    public PluginClass() {
        Log.d("nht...", "PluginClass client initialized");
    }

    @Override
    public int function1(int a, int b) {
        return a + b;
    }
}
