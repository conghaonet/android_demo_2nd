package com.app2m.demo2.permissions

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class PermissionsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.title = PermissionsActivity::class.java.simpleName
        setContentView(R.layout.demo2_activity_permissions)
    }
}
