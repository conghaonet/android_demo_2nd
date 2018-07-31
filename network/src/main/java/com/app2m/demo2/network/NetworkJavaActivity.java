package com.app2m.demo2.network;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.app2m.demo2.network.databinding.ActivityNetworkJavaBinding;

import org.jetbrains.anko.ToastsKt;

public class NetworkJavaActivity extends AppCompatActivity {
    private ActivityNetworkJavaBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_network_java);
        mBinding.setActivity(this);
    }

    public void onClickA() {
        ToastsKt.toast(this, "NetworkJavaActivity by ToastsKt");
//        Toast.makeText(this, "NetworkJavaActivity by Toast.makeText", Toast.LENGTH_SHORT).show();
    }
}
