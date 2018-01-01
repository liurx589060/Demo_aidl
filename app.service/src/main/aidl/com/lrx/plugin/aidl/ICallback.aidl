// ICallback.aidl
package com.lrx.plugin.aidl;

// Declare any non-default types here with import statements
import com.lrx.plugin.aidl.SampleInfo;

interface ICallback {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
//    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
//            double aDouble, String aString);
    void onSuccess(inout SampleInfo info);
    void onFail(int errorCode,String errorMsg);
}
