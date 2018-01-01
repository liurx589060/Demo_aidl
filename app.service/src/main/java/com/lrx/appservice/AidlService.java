package com.lrx.appservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.lrx.plugin.aidl.ICallback;
import com.lrx.plugin.aidl.IRemoteService;
import com.lrx.plugin.aidl.SampleInfo;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/12/31.
 */

public class AidlService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return remoteService;
    }

    private IRemoteService.Stub remoteService = new IRemoteService.Stub() {
        @Override
        public void callToJsonString(SampleInfo info, ICallback callback) throws RemoteException {
            if(info == null) {
                info = new SampleInfo();
            }
            Log.e("yy","service info=" + info.toString());

            JSONObject object = new JSONObject();
            try {
                object.put("userName",info.getUserName());
                object.put("userId",info.getUserId());
                object.put("userAge",String.valueOf(info.getUserAge()));
                object.put("userSex",info.getUserSex());
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("yy",e.toString());
            }

            info.setJsonString(object.toString());

            if(callback == null) return;
            if(info.getUserAge()%2 == 0) {
                callback.onSuccess(info);
                AidlHelper.getInstance().setSampleInfo(info);
            }else {
                callback.onFail(info.getUserAge(),"age=" + info.getUserAge() + "% 2 is not 0");
            }
        }

        @Override
        public void success() throws RemoteException {
            if(AidlHelper.getInstance().getCallback() != null) {
                AidlHelper.getInstance().getCallback().onSuccess();
            }
        }

        @Override
        public void onError(int errorCode, String errorMsg) throws RemoteException {
            if(AidlHelper.getInstance().getCallback() != null) {
                AidlHelper.getInstance().getCallback().onFail(errorCode,errorMsg);
            }
        }
    };
}
