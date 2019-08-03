package yongju.riiidhw.data.remote

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import yongju.riiidhw.BuildConfig

class ApiProvider {
    private fun provideWadizApiProvider(): ApiInterface {
        return makeRetrofit("https://jsonplaceholder.typicode.com/")
            .create(ApiInterface::class.java)
    }

    private fun makeRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                ))
            .client(makeOkHttpClient())
            .build()
    }

    private fun makeOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) {
                addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                addNetworkInterceptor(StethoInterceptor())
            }
        }.build()
    }

    companion object {
        val INSTANCE by lazy {
            ApiProvider().provideWadizApiProvider()
        }
    }
}