package com.dhorby.semantics.parsers

import com.dhorby.semantics.model.Article

fun List<String>.toArticleList(): List<Article> {
    return this.map { line ->
        val split: List<String> = line.split("\t")
        Article(split[0], split[1], split[2].replace("\"", ""))
    }
}
