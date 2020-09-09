package com.example.mypagingsamples.api

import android.util.Log
import com.example.mypagingsamples.model.GithubSearchResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author: zeting
 * @date: 2020/9/3
 */
interface GitHubService {

    /**
     * 此处需要使用 uspend修饰，挂起函数
     */
    @GET("search/repositories?sort=stars")
    suspend fun getGithubAccount(
            @Query("q") query: String,
            @Query("page") page: Int,
            @Query("per_page") itemsPerPage: Int
    ): GithubSearchResponse

    companion object {
        val TAG = GitHubService.javaClass.name
        const val IN_QUALIFIER = "in:name,description"
        const val BASE_SERVER_URL = "https://api.github.com/"
        fun create(): GitHubService {
            val interceptor = Interceptor { chain ->
                val request = chain.request()
                val startTime = System.currentTimeMillis()
                val response = chain.proceed(chain.request())
                val endTime = System.currentTimeMillis()
                val duration = endTime - startTime
                val mediaType = response.body()?.contentType()
                val content = response.body()?.string()
                Log.e(TAG, "----------Request Start----------------")
                Log.e(TAG, "| $request")
                Log.e(TAG, "| ${request.headers()}")
                Log.e(TAG, "| Response:$content")
                Log.e(TAG, "----------Request End:" + duration + "毫秒----------")
                response.newBuilder()
                        .body(ResponseBody.create(mediaType, content))
                        .build()
            }

            val client = OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build()

            val retrofit = Retrofit.Builder()
                    .client(client)
                    .baseUrl(BASE_SERVER_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            return retrofit.create(GitHubService::class.java)
        }
    }


}