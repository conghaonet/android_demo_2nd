package com.app2m.demo2

import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.app2m.demo2.databinding.Demo2ActivityMainBinding
import com.app2m.demo2.permissions.PermissionsActivity

class MainActivity : AppCompatActivity() {
//    private lateinit var mBinding: Demo2ActivityMainBinding
    private val mBinding: Demo2ActivityMainBinding by lazy {
        DataBindingUtil.setContentView<Demo2ActivityMainBinding>(this, R.layout.demo2_activity_main)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        mBinding = DataBindingUtil.setContentView<Demo2ActivityMainBinding>(this, R.layout.demo2_activity_main)
        mBinding.activity = this
        mBinding.model = MainVM()
    }

    fun onClickPermissions(view: View) {
        var intent = Intent(this, PermissionsActivity::class.java)
        startActivity(intent)
    }
}
