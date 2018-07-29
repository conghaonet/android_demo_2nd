package com.app2m.demo2.network

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.app2m.demo2.network.data.CommonCodeItem
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_network.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.net.URL
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.toast
import java.util.concurrent.Future


class NetworkActivity : AppCompatActivity() {
    private var networkTask: Future<Unit>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_network)
        buttonDoAsync.setOnClickListener{
            buttonDoAsync.isEnabled = false
            networkTask = doAsync {
                for (timer in 0..2 step 2) {
                    uiThread{toast("timer in 0..2 step 2 = $timer")}
                    Thread.sleep(2000)
                }
                for (timer in 3 until 5) {
                    uiThread{toast("timer in 3 until 5 = $timer")}
                    Thread.sleep(2000)
                }

                val strCommonCode = URL(COMMON_CODE_URL).readText()
                val typeToken = object : TypeToken<List<CommonCodeItem>>() {}.type
                val items:List<CommonCodeItem> = Gson().fromJson(strCommonCode,  typeToken)
                uiThread {
                    tvValue.text = ((items[0]).items[0]).toString()
                    buttonDoAsync.isEnabled = true
                }
            }
        }
    }

    override fun onBackPressed() {
        networkTask?.let {
            if(!it.isDone && !it.isCancelled) {
                it.cancel(true)
            }
        }
        super.onBackPressed()
    }
}
