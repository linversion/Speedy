package com.linversion.speedy.domain

import com.linversion.speedy.data.repository.HomeRepository
import com.linversion.speedy.model.ArticleResource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * @author linversion
 * on 2023/12/23
 */
class GetHomeArticleListUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    operator fun invoke(page: Int): Flow<ArticleResource?> = homeRepository.getHomeArticle(page)
}