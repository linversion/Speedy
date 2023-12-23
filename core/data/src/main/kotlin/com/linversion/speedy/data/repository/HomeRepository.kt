package com.linversion.speedy.data.repository

import com.linversion.speedy.model.ArticleResource
import kotlinx.coroutines.flow.Flow

/**
 * @author linversion
 * on 2023/12/23
 */
interface HomeRepository {
    fun getHomeArticle(page: Int): Flow<ArticleResource?>
}