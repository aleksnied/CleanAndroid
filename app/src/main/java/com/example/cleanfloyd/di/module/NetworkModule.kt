package com.example.cleanfloyd.di.module

import android.content.Context
import com.example.cleanfloyd.data.repository.AlbumsRepository
import com.example.cleanfloyd.data.source.remote.AlbumsService
import com.example.cleanfloyd.utls.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module(includes = [ApplicationModule::class])
class NetworkModule {

  @Provides
  @Singleton
  fun providesRetrofit(
    moshiConverterFactory: MoshiConverterFactory,
    rxJava2CallAdapterFactory: RxJava2CallAdapterFactory,
    okHttpClient: OkHttpClient
  ): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(moshiConverterFactory)
        .addCallAdapterFactory(rxJava2CallAdapterFactory)
        .client(okHttpClient)
        .build()
  }

  @Provides
  @Singleton
  fun providesOkHttpClient(context: Context/*, isNetworkAvailable: Boolean*/): OkHttpClient {
    val cacheSize = (5 * 1024 * 1024).toLong()
    val mCache = Cache(context.cacheDir, cacheSize)
    val interceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
      override fun log(message: String) {
        Timber.tag("OkHttp").d(message)
      }
    })
    interceptor.level = HttpLoggingInterceptor.Level.BODY
    val client = OkHttpClient.Builder()
        .cache(mCache)
        .connectTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS)
        .addInterceptor(interceptor).addInterceptor { chain ->
          var request = chain.request()
          request =
              request.newBuilder().header("Cache-Control", "public, max-stale=" + 60 * 60 * 24 * 7)
                  .build()
          chain.proceed(request)
        }
    return client.build()
  }

  @Provides
  @Singleton
  fun providesMoshiConverterFactory(): MoshiConverterFactory {
    return MoshiConverterFactory.create()
  }

  @Provides
  @Singleton
  fun providesRxJavaCallAdapterFactory(): RxJava2CallAdapterFactory {
    return RxJava2CallAdapterFactory.create()
  }

  @Singleton
  @Provides
  fun provideAlbumsService(retrofit: Retrofit): AlbumsService {
    return retrofit.create(AlbumsService::class.java)
  }

  @Singleton
  @Provides
  fun provideAlbumsRepository(retrofitService: AlbumsService): AlbumsRepository {
    return AlbumsRepository(retrofitService)
  }
}
