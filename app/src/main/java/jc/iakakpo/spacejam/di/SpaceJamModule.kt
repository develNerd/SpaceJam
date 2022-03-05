package jc.iakakpo.spacejam.di

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jc.iakakpo.spacejam.utils.graphQlUrl
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * @author Isaac Akakpo
 * Created on 3/5/2022 2:27 PM
 */
@Module
@InstallIn(SingletonComponent::class)
object SpaceJamModule {



    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val okHttpClient = OkHttpClient().newBuilder()
        okHttpClient.callTimeout(40, TimeUnit.SECONDS)
        okHttpClient.connectTimeout(40, TimeUnit.SECONDS)
        okHttpClient.readTimeout(40, TimeUnit.SECONDS)
        okHttpClient.writeTimeout(40, TimeUnit.SECONDS)
        okHttpClient.build()
        return okHttpClient.build()
    }

    @Provides
    @Singleton
    fun apiClient(okHttpClient: OkHttpClient):ApolloClient {
        return ApolloClient.Builder()
            .serverUrl(graphQlUrl)
            .okHttpClient(okHttpClient)
            .build()
    }


}