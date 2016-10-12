package com.xia.xmlparser.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.xia.xmlparser.R;
import com.xia.xmlparser.XiaXmlParser;
import com.xia.xmlparser.demo.DemoBean;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG, "=============xmlToSingleObje==============");

        DemoBean mDemoBean = XiaXmlParser.xmlToSingleObje(DemoBean.TEST_DATA, DemoBean.class);
        Log.i(TAG, "Name:" + mDemoBean.getName());
        Log.i(TAG, "Age:" + mDemoBean.getAge());
        Log.i(TAG, "Sex:" + mDemoBean.getSex());

        Log.i(TAG, "============xmlToListObj===============");

        List<DemoBean> mDemoBeans = XiaXmlParser.xmlToListObj(DemoBean.TEST_DATA_LIST, DemoBean.class, "DemoBean");
        for (DemoBean data : mDemoBeans) {
            Log.i(TAG, "Name:" + data.getName());
            Log.i(TAG, "Age:" + data.getAge());
            Log.i(TAG, "Sex:" + data.getSex());
        }

        Log.i(TAG, "===========================");
    }
}
