package com.app2m.demo2.network

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.app2m.demo2.network.data.CommonCodeItem
import org.jetbrains.anko.*

class MyListActivity: AppCompatActivity() {
    lateinit var mAdapter: ItemAdapter
    lateinit var data: List<CommonCodeItem>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        data = intent.getSerializableExtra("data") as List<CommonCodeItem>
        mAdapter = ItemAdapter(this, data)
        MyListActivityUI().setContentView(this)
        mAdapter.setOnItemClickListener(object : ItemAdapter.ItemClickListener{
            override fun onClickItem(position: Int) {
                toast(data[position].toString())
            }
        })
    }
}

class MyListActivityUI : AnkoComponent<MyListActivity> {
    override fun createView(ui: AnkoContext<MyListActivity>) = ui.apply {
        frameLayout {
            lparams(matchParent, matchParent)
            listView {
                adapter = owner.mAdapter
            }.lparams(matchParent, wrapContent)
        }
    }.view

}