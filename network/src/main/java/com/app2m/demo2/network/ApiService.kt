package com.app2m.demo2.network

import com.app2m.demo2.network.data.CommonCodeItem
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    companion object {
        val BASE_URL: String = "http://fepapi.beta.web.sdp.101.com/v1/"
    }

    @GET("commonapi/get_codes")
    fun getCommonCodes() : Observable<List<CommonCodeItem>>
}