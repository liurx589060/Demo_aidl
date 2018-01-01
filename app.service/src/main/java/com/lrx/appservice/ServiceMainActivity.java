package com.lrx.appservice;

import android.content.Intent;
import android.content.ServiceConnection;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ServiceMainActivity extends AppCompatActivity {
    private Button mInvokeBtn;
    private TextView mResultTxv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_main);

        mInvokeBtn = (Button) findViewById(R.id.btn_invoke);
        mResultTxv = (TextView) findViewById(R.id.text_result);

        mInvokeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AidlHelper.getInstance().invokeClient(ServiceMainActivity.this, new IInvokeCallback() {
                    @Override
                    public void onSuccess() {
                        mResultTxv.setText(AidlHelper.getInstance().getSampleInfo().getJsonString());
                    }

                    @Override
                    public void onFail(int errorCode, String errorMsg) {
                        mResultTxv.setText("errorCode=" + errorCode + "--errorMsg=" + errorMsg);
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        AidlHelper.getInstance().onActivityResult(requestCode,resultCode,data);
    }
}
