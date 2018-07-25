package com.app2m.demo2

import android.databinding.BaseObservable
import android.databinding.Bindable

class MainVM : BaseObservable() {
    @Bindable
    public fun getName() : String{
        return "跳转到:PermissionsActivity"
    }

    @Bindable
    var age = "年龄：22"
}