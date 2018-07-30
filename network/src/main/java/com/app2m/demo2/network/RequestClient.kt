package com.app2m.demo2.network

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RequestClient {
    companion object {
        @Volatile
        private lateinit var mkHttpClient: OkHttpClient

        private fun buildClient(): OkHttpClient {
            mkHttpClient = OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
//                            .addInterceptor(SlpRequestInterceptor())
                    .build()
/*
            if (mkHttpClient == null) {
                synchronized(RequestClient::class.java) {
                    if (mkHttpClient == null) {

                    }
                }
            }
*/
            return mkHttpClient
        }

        /**
         * 使用默认的BASE_API构建retrofit实例
         */
        fun <T> buildService(serviceClass: Class<T>): T {
            return retrofit(BASE_API).create(serviceClass)
        }

        /**
         * 使用自定义的hosturl构建对应的retrofit实例
         *
         * @param baseApi 带http开头并且以'/'结束符的url地址
         */
        fun <T> buildService(baseApi: String, serviceClass: Class<T>): T {
            return retrofit(baseApi).create(serviceClass)
        }

        private fun retrofit(baseUrl: String): Retrofit {
            return Retrofit.Builder()
                    .baseUrl(baseUrl)
//                .addConverterFactory(SlpGsonConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(buildClient())
                    .build()
        }

/*
        fun <T> ioToMainThread(o: Observable<T>, s: Subscriber<T>) {
            o.subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeBy(s)
        }
*/
    }
}