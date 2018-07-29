package com.app2m.demo2.network.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Parcelize 注解表示自动实现Parcelable接口的相关方法
 *
 * 不过若想正常编译，还需修改模块的编译文件build.gradle中添加下面几行，表示增加对安卓插件的编译支持：
 * android {
 *     androidExtensions {
 *         experimental = true
 *     }
 * }
 *
 */
@Parcelize
data class CommonCodeItem(var code_type: String="", var name:String="", var items: List<SubItem>) : Parcelable {
    /**
     * 这是个静态内部类，非静态内部类在class关键字前要加inner关键字
     */
    @Parcelize
    data class SubItem(var code: String="", var name:String="", var hidden:Boolean=true)  : Parcelable
}

