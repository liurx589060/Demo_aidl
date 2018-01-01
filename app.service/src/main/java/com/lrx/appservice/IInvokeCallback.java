package com.lrx.appservice;

/**
 * Created by Administrator on 2017/12/31.
 */

public interface IInvokeCallback {
    void onSuccess();
    void onFail(int errorCode,String errorMsg);
}
