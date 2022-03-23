package jc.iakakpo.spacejam.di

import android.content.Context
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jc.iakakpo.spacejam.BuildConfig
import jc.iakakpo.spacejam.enums.StringResourceProvider
import jc.iakakpo.spacejam.models.SpaceJamDb
import jc.iakakpo.spacejam.utils.graphQlUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * @author Isaac Akakpo
 * Created on 3/5/2022 2:27 PM
 *
 * Dagger Hilt Module Class for providing Injections
 *
 *
 */
@Module
@InstallIn(SingletonComponent::class)
object SpaceJamModule {



  @Singleton
  @Provides
  fun provideSpaceJamDb():SpaceJamDb{
    return SpaceJamDb()
  }

  @Provides
  fun provideLogInterceptor(): HttpLoggingInterceptor {
    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.apply {
      loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    }
    return loggingInterceptor
  }

  /**
   * Provide okhttp client for GraphQl Apollo Client Builder.
   * Works out with the Hilt Magic :).
   * use [@Singleton] if your TODO()
   * @return [OkHttpClient]
   */
  @Provides
  @Singleton
  fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
    val okHttpClient = OkHttpClient().newBuilder()
    okHttpClient.callTimeout(10L, TimeUnit.SECONDS)
    okHttpClient.connectTimeout(10L, TimeUnit.SECONDS)
    okHttpClient.readTimeout(10L, TimeUnit.SECONDS)
    okHttpClient.writeTimeout(10L, TimeUnit.SECONDS)
    okHttpClient.build()
    if (BuildConfig.DEBUG) {
      okHttpClient.addInterceptor(loggingInterceptor)
    }
    return okHttpClient.build()
  }


  /**
   * Apiclient
   *
   * @param okHttpClient -> gets [OkHttpClient] from [provideOkHttpClient] method
   * @return an [ApolloClient]
   */
  @Provides
  @Singleton
  fun apiClient(okHttpClient: OkHttpClient): ApolloClient {
    return ApolloClient.Builder()
      .serverUrl(graphQlUrl)
      .okHttpClient(okHttpClient)
      .build()
  }


}