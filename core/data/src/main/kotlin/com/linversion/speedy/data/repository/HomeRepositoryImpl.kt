package com.linversion.speedy.data.repository

import com.linversion.speedy.model.ArticleResource
import com.linversion.speedy.network.NiaNetworkDataSource
import com.linversion.speedy.network.model.asExternalModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * @author linversion
 * on 2023/12/23
 */
class HomeRepositoryImpl @Inject constructor(
    private val network: NiaNetworkDataSource
) : HomeRepository {

    override fun getHomeArticle(page: Int): Flow<ArticleResource?> = flow {
        emit(network.getHomeArticleList(page)?.asExternalModel())
    }.flowOn(Dispatchers.IO).catch {
        it.printStackTrace()
        emit(null)
    }
}