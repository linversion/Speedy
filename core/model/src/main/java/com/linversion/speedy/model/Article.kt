package com.linversion.speedy.model

/**
 * @author linversion
 * on 2023/12/23
 */
data class ArticleResource(
    val curPage: Int,
    val datas: List<Article>,
    val over: Boolean
)

data class Article(
    val author: String,
    val title: String,
    val envelopePic: String? = null, // 封面
    val date: Long,
    val link: String
)

data class Tag(
    val name: String,
    val url: String
)
