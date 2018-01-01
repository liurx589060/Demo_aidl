// IRemoteService.aidl
package com.lrx.plugin.aidl;

// Declare any non-default types here with import statements
import com.lrx.plugin.aidl.ICallback;
import com.lrx.plugin.aidl.SampleInfo;

interface IRemoteService {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
//    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
//            double aDouble, String aString);

    void callToJsonString(inout SampleInfo info,ICallback callback);
    void success();
    void onError(int errorCode,String errorMsg);
}
