package com.lrx.demo_aidl;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lrx.plugin.aidl.ICallback;
import com.lrx.plugin.aidl.IRemoteService;
import com.lrx.plugin.aidl.SampleInfo;

import java.util.Random;

public class ClientActivity extends Activity {
    private TextView mResultTxv;
    private Button mCloseBtn;

    private String mPackageName = "";

    private IRemoteService mRemoteService;
    private Handler mHandler;

    private final int RESULT_BIND_FAIL = -11001;
    private final int RESULT_CANCEL = -11002;

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e("yy","service connect");
            mRemoteService = IRemoteService.Stub.asInterface(service);
            invokeServiceToJsonString();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e("yy","service disconnect");
            mServiceConnection = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        mHandler = new Handler();
        mResultTxv = (TextView) findViewById(R.id.text_result);
        mCloseBtn = (Button) findViewById(R.id.btn_close);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            mPackageName = bundle.getString("packageName","");
        }

        bindRemoteService();
    }

    private boolean bindRemoteService() {
        Intent intent = new Intent();
        intent.setPackage(mPackageName);
        String data = String.format("%s://aidl_service",mPackageName);
        intent.setData(Uri.parse(data));
        Log.e("yy","action=" + intent.getAction());
        boolean bindResult = bindService(intent,mServiceConnection, Context.BIND_AUTO_CREATE);
        Log.e("yy","bindResult=" + bindResult);
        if(!bindResult) {
            Intent intent1 = new Intent();
            intent1.putExtra("errorMsg","bind service fail");
            setResultForReturn(RESULT_BIND_FAIL,intent1);
        }
        return  bindResult;
    }

    private void invokeServiceToJsonString() {
        if (mRemoteService == null) {
            bindRemoteService();
            return;
        }
        Random random = new Random();
        int age = random.nextInt(100);
        SampleInfo info = new SampleInfo();
        info.setUserName("Song yue");
        info.setUserAge(age);
        info.setUserSex("男");
        info.setUserId("10002469");
        Log.e("yy","invokeServiceToJsonString--" + info.toString());
        try{
            mRemoteService.callToJsonString(info, new ICallback.Stub() {
                @Override
                public void onSuccess(SampleInfo info) throws RemoteException {
                    Toast.makeText(ClientActivity.this,"onSuccess",Toast.LENGTH_SHORT).show();
                    mResultTxv.setText(info.getJsonString());
                    invokeServiceSuccess();
                }

                @Override
                public void onFail(int errorCode, String errorMsg) throws RemoteException {
                    Toast.makeText(ClientActivity.this,"errorCode=" + errorMsg + "--errorMsg=" + errorMsg,Toast.LENGTH_SHORT).show();
                    mResultTxv.setText("errorCode=" + errorCode + "--errorMsg=" + errorMsg);
                    invokeServiceError(errorCode,errorMsg);
                }
            });
        }catch (Exception e) {
            Log.e("yy",e.toString());
        }
    }

    private void invokeServiceSuccess() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mRemoteService != null) {
                    try {
                        mRemoteService.success();
                        finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("yy",e.toString());
                    }
                }
            }
        },3000);
    }

    private void invokeServiceError(final int code, final String errorMsg) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mRemoteService != null) {
                    try {
                        mRemoteService.onError(code,errorMsg);
                        finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("yy",e.toString());
                    }
                }
            }
        },3000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mServiceConnection != null) {
            unbindService(mServiceConnection);
        }
        Log.e("yy","aidl ClientActivity onDestroy");
    }

    public void setResultForReturn(int code) {
        setResult(code);
        finish();
    }

    public void setResultForReturn(int code,Intent data) {
        setResult(code,data);
        finish();
    }
}
