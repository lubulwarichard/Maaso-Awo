package net.unique.awo.di.module

import dagger.Module
import dagger.Provides
import dagger.Reusable
import io.reactivex.schedulers.Schedulers
import net.unique.awo.helpers.Constants
import net.unique.awo.remote.PostApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Module which provides all required dependencies about network
 */
@Module
// Safe here as we are dealing with a Dagger 2 module
class NetworkModule {

    /**
     * Provides the Post service implementation.
     * @param retrofit the Retrofit object used to instantiate the service
     * @return the Post service implementation.
     */
    @Provides
    @Reusable
    internal fun providePostApi(retrofit: Retrofit): PostApi {
        return retrofit.create(PostApi::class.java)
    }

    /**
     * Provides the Retrofit object.
     * @return the Retrofit object
     */
    @Provides
    @Reusable
    internal fun provideRetrofitInterface(): Retrofit {

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()

        httpClient.addInterceptor(interceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)

        val client = httpClient.build()

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL_MTN)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
    }

}