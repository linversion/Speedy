package com.linversion.speedy.network.model

import com.linversion.speedy.model.Article
import com.linversion.speedy.model.ArticleResource
import kotlinx.serialization.Serializable

/**
 * @author linversion
 * on 2023/12/23
 */
@Serializable
data class NetworkArticleResource(
    val curPage: Int,
    val datas: List<NetworkArticle>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)

fun NetworkArticleResource.asExternalModel() = ArticleResource(
    curPage = curPage,
    datas = datas.map { it.asExternalModel() },
    over = over
)

@Serializable
data class NetworkArticle(
    val adminAdd: Boolean,
    val apkLink: String,
    val audit: Int,
    val author: String,
    val canEdit: Boolean,
    val chapterId: Int,
    val chapterName: String,
    val collect: Boolean,
    val courseId: Int,
    val desc: String,
    val descMd: String,
    val envelopePic: String,
    val fresh: Boolean,
    val host: String,
    val id: Int,
    val isAdminAdd: Boolean,
    val link: String,
    val niceDate: String,
    val niceShareDate: String,
    val origin: String,
    val prefix: String,
    val projectLink: String,
    val publishTime: Long,
    val realSuperChapterId: Int,
    val selfVisible: Int,
    val shareDate: Long,
    val shareUser: String,
    val superChapterId: Int,
    val superChapterName: String,
    val tags: List<NetworkTag>,
    val title: String,
    val type: Int,
    val userId: Int,
    val visible: Int,
    val zan: Int
)

fun NetworkArticle.asExternalModel() = Article(
    author = author.ifEmpty { shareUser },
    title = title,
    envelopePic = envelopePic.ifEmpty { null },
    date = shareDate,
    link = link
)

@Serializable
data class NetworkTag(
    val name: String,
    val url: String
)