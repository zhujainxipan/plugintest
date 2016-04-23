package com.example.annuoaichengzhang.host;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import dalvik.system.DexClassLoader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        useDexClassLoader();
    }

    private void useDexClassLoader() {
        Intent intent = new Intent("com.example.annuoaichengzhang.plugin", null);
        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> plugins = packageManager.queryIntentActivities(intent, 0);
        ResolveInfo rinfo = plugins.get(0);
        ActivityInfo ainfo = rinfo.activityInfo;
        String packageName = ainfo.packageName;
        String sourceDir = ainfo.applicationInfo.sourceDir;
        String dataDir = getApplicationInfo().dataDir;
        String libraryDir = ainfo.applicationInfo.nativeLibraryDir;
        DexClassLoader cl = new DexClassLoader(sourceDir, dataDir, libraryDir, this.getClass().getClassLoader());
        try {
            Class<?> aClass = cl.loadClass(packageName + ".PluginClass");
            Object o = aClass.newInstance();
            Class[] params = new Class[2];
            params[0] = Integer.TYPE;
            params[1] = Integer.TYPE;
            Method action = aClass.getMethod("function1", params);
            Integer ret = (Integer) action.invoke(o, 12, 34);
            TextView tv = (TextView) findViewById(R.id.tv_result);
            tv.setText(ret.toString());
            Log.d("nht...", ret + "");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
