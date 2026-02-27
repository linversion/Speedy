/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.linversion.speedy.network.retrofit

import com.linversion.speedy.network.BuildConfig
import com.linversion.speedy.network.NetworkDataSource
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.linversion.speedy.network.model.NetworkArticleResource
import io.github.rotbolt.flakerokhttpcore.FlakerInterceptor
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Retrofit API declaration for NIA Network API
 */
private interface RetrofitNetworkApi {
    @GET("article/list/{page}/json")
    suspend fun getHomeArticleList(@Path("page") page: Int): NetworkResponse<NetworkArticleResource>
}

private const val NIA_BASE_URL = BuildConfig.BACKEND_URL

/**
 * Wrapper for data provided from the [NIA_BASE_URL]
 */
@Serializable
private data class NetworkResponse<T>(
    val data: T?,
    val errorCode: Int,
    val errorMsg: String
)


/**
 * [Retrofit] backed [NetworkDataSource]
 */
@Singleton
class RetrofitNiaNetwork @Inject constructor(
    networkJson: Json,
    okhttpCallFactory: Call.Factory,
) : NetworkDataSource {

    private val networkApi = Retrofit.Builder()
        .client(
            OkHttpClient.Builder()
            .addInterceptor(FlakerInterceptor.Builder().build())
            .build()
        )
        .baseUrl(NIA_BASE_URL)
        .callFactory(okhttpCallFactory)
        .addConverterFactory(
            networkJson.asConverterFactory("application/json".toMediaType()),
        )

        .build()
        .create(RetrofitNetworkApi::class.java)

    override suspend fun getHomeArticleList(page: Int): NetworkArticleResource? = networkApi.getHomeArticleList(page = page).data
}
