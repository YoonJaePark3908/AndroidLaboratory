package com.example.pagingmultiview.network

import android.content.Context
import android.util.Log
import com.example.pagingmultiview.BuildConfig
import com.example.pagingmultiview.util.NetworkUtils
import okhttp3.*
import okhttp3.internal.http2.ConnectionShutdownException
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit

class RetrofitClient(
    private val context: Context,
) {
    enum class BaseServerURL(val url: String) {
        DataPortal("http://apis.data.go.kr")
    }

    private val TAG = RetrofitClient::class.java.simpleName
    private val CONNECT_TIMEOUT_OUT_MINUTE: Long = 3
    private val READ_TIMEOUT_OUT_MINUTE: Long = 3

    fun getService(baseServerURL: BaseServerURL = BaseServerURL.DataPortal): RetrofitService {
        val interceptor: Interceptor = CustomInterceptor(context, baseServerURL = BaseServerURL.DataPortal)
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder().apply {
            addInterceptor(interceptor)
            if (BuildConfig.DEBUG) {
                addInterceptor(httpLoggingInterceptor)
            }
            retryOnConnectionFailure(true)
            connectTimeout(CONNECT_TIMEOUT_OUT_MINUTE, TimeUnit.MINUTES)
            readTimeout(READ_TIMEOUT_OUT_MINUTE, TimeUnit.MINUTES)
        }.build()

        val retrofit = Retrofit.Builder().apply {
            baseUrl(baseServerURL.url)
            client(client)
            addConverterFactory(GsonConverterFactory.create()) // 파싱등록
        }.build()

        return retrofit.create(RetrofitService::class.java)
    }

    class CustomInterceptor(
        private val context: Context,
        private val baseServerURL: BaseServerURL
    ) : Interceptor {
        private val TAG = CustomInterceptor::class.java.simpleName
        override fun intercept(chain: Interceptor.Chain): Response {
            val builder = getClientBuilderWithToken(context, chain)
            val request = chain.request()
            try {
                val response: Response = chain.proceed(builder.build()).apply {
                    peekBody(Int.MAX_VALUE.toLong())
                }
                if (!response.isSuccessful) {
                    Log.d(TAG, "getClient() error code: " + response.code)
                }
                return response
            } catch (e: Exception) {
                e.printStackTrace()
                var msg = ""
                when (e) {
                    is SocketTimeoutException -> {
                        msg = "Timeout - Please check your internet connection"
                    }
                    is UnknownHostException -> {
                        msg = "Unable to make a connection. Please check your internet"
                    }
                    is ConnectionShutdownException -> {
                        msg = "Connection shutdown. Please check your internet"
                    }
                    is IOException -> {
                        msg = "Server is unreachable, please try again later."
                    }
                    is IllegalStateException -> {
                        msg = "${e.message}"
                    }
                    else -> {
                        msg = "${e.message}"
                    }
                }

                return Response.Builder()
                    .request(request)
                    .protocol(Protocol.HTTP_1_1)
                    .code(999)
                    .message(msg)
                    .build()
            }

        }

        private fun getClientBuilderWithToken(
            context: Context,
            chain: Interceptor.Chain
        ): Request.Builder {
            val builder = chain.request().newBuilder().addHeader("Content-Type", "application/json")
            return builder
//            .addHeader("Content-Type", "application/json")
//            .addHeader("user-index", "10005")
        }
    }

}