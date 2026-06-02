

package com.ele.telecallerapp.network

import android.content.Context
import android.util.Log
import com.ele.telecallerapp.TokenStore
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "https://telecaller-bn20.onrender.com/"


    private lateinit var context: Context

    fun init(ctx: Context) {
        context = ctx.applicationContext
    }

    private val logger = HttpLoggingInterceptor().apply {
        level =
            if (android.util.Log.isLoggable("DEBUG", Log.DEBUG))
                HttpLoggingInterceptor.Level.BODY
            else
                HttpLoggingInterceptor.Level.NONE
    }

    private val authInterceptor = Interceptor { chain ->
        val request = chain.request()

        if (request.url.encodedPath.contains("/auth/login")) {
            return@Interceptor chain.proceed(request)
        }

        val token = TokenStore.getToken(context)

        val newRequest = if (!token.isNullOrBlank()) {
            request.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        } else {
            request
        }

        chain.proceed(newRequest)
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .addInterceptor(logger)
        .build()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}


