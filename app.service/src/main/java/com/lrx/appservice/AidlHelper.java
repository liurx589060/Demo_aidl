package com.lrx.appservice;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.lrx.plugin.aidl.SampleInfo;

/**
 * Created by Administrator on 2017/12/31.
 */

public class AidlHelper {
    private static AidlHelper instance = null;
    private IInvokeCallback callback;
    private SampleInfo sampleInfo;

    private final int INVOKE_REQUEST_CODE = 100;

    public static AidlHelper getInstance() {
        if(instance == null) {
            instance = new AidlHelper();
        }
        return instance;
    }

    public void invokeClient(Activity activity, IInvokeCallback callback) {
        this.callback = callback;
        Intent intent = new Intent("com.lrx.demo_aidl.client.aidl");
        Bundle bundle = new Bundle();
        bundle.putString("packageName",activity.getPackageName());
        intent.putExtras(bundle);
        if(intent.resolveActivity(activity.getPackageManager()) == null) {
            Toast.makeText(activity,"activity is not found",Toast.LENGTH_LONG).show();
            return;
        }
        activity.startActivityForResult(intent,INVOKE_REQUEST_CODE);
    }

    public IInvokeCallback getCallback() {
        return callback;
    }

    public SampleInfo getSampleInfo() {
        if(sampleInfo == null) {
            sampleInfo = new SampleInfo();
        }
        return sampleInfo;
    }

    public void setSampleInfo(SampleInfo sampleInfo) {
        this.sampleInfo = sampleInfo;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == INVOKE_REQUEST_CODE) {
            if(callback == null) return;
            if(resultCode != 0) {//不成功
                if(data != null && data.getExtras() != null) {
                    callback.onFail(requestCode,data.getStringExtra("errorMsg"));
                }
            }
        }
    }
}
