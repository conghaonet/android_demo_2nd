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
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.util.concurrent.Future

class NetworkActivity : AppCompatActivity() {
    private var networkTask: Future<Unit>? = null
    private var compositeDisposable = CompositeDisposable()
    companion object {
        private val TAG = "NetworkActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_network)
        btnJavaActivity.setOnClickListener {
            startActivity<NetworkJavaActivity>()
        }
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

        btnRxObserver.setOnClickListener{
            val apiService = RequestClient.buildService(ApiService::class.java)
            val observable = apiService.getCommonCodes()
            var observer = object : Observer<List<CommonCodeItem>> {
                override fun onComplete() {
                    toast("onComplete====")
                }
                override fun onError(e: Throwable) {
                    tvValue.text = e.message
                }

                override fun onNext(t: List<CommonCodeItem>) {
                    tvValue.text = "observer onNext==== CommonCode size is ${t.size}"
                }

                override fun onSubscribe(d: Disposable) {
                    toast("onSubscribe==== disposable.isDisposed is ${d.isDisposed}")
                    //不能在这里调用dispose(), 否则onNext()将不会执行
                    //d.dispose()
                    compositeDisposable.add(d)
                }
            }
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer)
        }

        btnRxSubscribeBy.setOnClickListener{
            val apiService = RequestClient.buildService(ApiService::class.java)
            val observable = apiService.getCommonCodes()
            var disposable = observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeBy(
                            onError = {
                                tvValue.text = it.message
                            },
                            onComplete = {
                                toast("onComplete====")
                            },
                            onNext = {
                                tvValue.text = "subscribeBy onNext==== CommonCode size is ${it.size}"
                            })
            compositeDisposable.add(disposable)
        }

        btnListActivity.setOnClickListener {
            val apiService = RequestClient.buildService(ApiService::class.java)
            val observable = apiService.getCommonCodes()
            var disposable = observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeBy(
                            onError = {
                                tvValue.text = it.message
                            },
                            onNext = {
                                startActivity<MyListActivity>("data" to it)
                            })
            compositeDisposable.add(disposable)
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

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }
}
