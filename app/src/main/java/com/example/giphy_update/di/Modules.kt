package com.example.giphy_update.di

import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.giphy_update.api.NaverApis
import com.example.giphy_update.api.NaverApis.Companion.BaseUrl
import com.example.giphy_update.data.repository.SearchRepository
import com.example.giphy_update.data.repository.SearchRepositoryImpl
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

//모듈 : 사용하고자 하는 객체를 실제로 생성/제공해주는 역할. 코인이던 힐트던 특정 객체가 필요할때, 그 객체를 제공해주는 모듈이 있는지 확인하고 있다면 제공받는다.
//컴포넌트란? : 외부에서 객체를 제공받을 시 모듈로부터 직접 받는 것은 아니다. 컴포넌트라는 인터페이스를 통해 받는다. 즉 컴포넌트란 외부객체를 주입받기 위한 인터페이스다. 컴포넌트에 여러 모듈들이 들어간다 생각하면 된다.
//@InstallIn : 힐트엔 표준적으로 존재하는 컴포넌트들이 있다. @InstallIn 통해 표준 컴포넌트들에 모듈을 넣을 수 있다. 모든 모듈은 @InstallIn 를 통해 특정 컴포넌트에 속해야한다.
//val viewModelModule = module {
//    viewModel { MovieViewModel() }
//}


@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {
    @Provides
    @ViewModelScoped
    fun provideSearchRepository(naverApis: NaverApis): SearchRepository =
        SearchRepositoryImpl(naverApis) // SavedStateHandle 를 통한 주입이 가능해짐 // 첫 화면 노출시에는 `handle.get<String>("id")` 에 값이 없다. // 화면이 죽었다 살아난 경우 값이 있는걸 확인 할 수 있다.
}



@Module
@InstallIn(SingletonComponent::class)
object NetWorkModule {

    @Singleton
    @Provides
    fun provideBaseUrl() = BaseUrl


    @Provides
    fun provideOkHttpInterceptor(): Interceptor {
        return Interceptor { chain: Interceptor.Chain ->
            val original: Request = chain.request()
            val requestBuilder: Request.Builder = original.newBuilder()
                .addHeader("X-Naver-Client-Id", "fQMlV3oBJS7smzQdEnhV")
                .addHeader("X-Naver-Client-Secret", "_VUQwz6ZKw")

            val request: Request = requestBuilder.build()
            chain.proceed(request)
        }
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        interceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30000, TimeUnit.MILLISECONDS)
            .writeTimeout(30000, TimeUnit.SECONDS)
            .readTimeout(30000, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor(interceptor)
            .addNetworkInterceptor(StethoInterceptor())
            .build()
    }

    @ExperimentalSerializationApi
    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, baseUrl: String): Retrofit {
        return Retrofit.Builder().client(okHttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(Json {
                isLenient = true; ignoreUnknownKeys = true;coerceInputValues =
                true; allowStructuredMapKeys = true; prettyPrint = true; encodeDefaults = true;
                allowStructuredMapKeys = true;
            }.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): NaverApis = retrofit.create(NaverApis::class.java)

}



val storageModule = module {
    // shared
    single {
        EncryptedSharedPreferences.create(
            androidContext(),
            "Giphy_Update_EncryptedSharedPreferences",
            MasterKey.Builder(androidContext(), MasterKey.DEFAULT_MASTER_KEY_ALIAS)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build(),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }
}