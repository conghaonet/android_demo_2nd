package com.app2m.demo2

import android.databinding.BaseObservable
import android.databinding.Bindable

class MainVM : BaseObservable() {
    @Bindable
    public fun getName() : String{
        return "姓名：张三"
    }

    @Bindable
    var age = "年龄：22"
}