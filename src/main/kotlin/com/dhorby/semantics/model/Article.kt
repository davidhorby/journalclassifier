package com.dhorby.semantics.model

import java.io.Serializable

data class Article(val journalId:String, val articleId:String, val articleText:String):Serializable

data class ArticleExternal(val internalClass: Article)
