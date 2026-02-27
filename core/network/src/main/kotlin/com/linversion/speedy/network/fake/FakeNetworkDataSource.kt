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

package com.linversion.speedy.network.fake

import JvmUnitTestFakeAssetManager
import com.linversion.speedy.common.network.Dispatcher
import com.linversion.speedy.common.network.NiaDispatchers
import com.linversion.speedy.network.NetworkDataSource
import com.linversion.speedy.network.model.NetworkArticleResource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.serialization.json.Json
import javax.inject.Inject

/**
 * [NetworkDataSource] implementation that provides static news resources to aid development
 */
class FakeNetworkDataSource @Inject constructor(
    @Dispatcher(NiaDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val networkJson: Json,
    private val assets: FakeAssetManager = JvmUnitTestFakeAssetManager,
) : NetworkDataSource {
    override suspend fun getHomeArticleList(page: Int): NetworkArticleResource? {
        return null
    }


    companion object {
        private const val NEWS_ASSET = "news.json"
        private const val TOPICS_ASSET = "topics.json"
    }
}
