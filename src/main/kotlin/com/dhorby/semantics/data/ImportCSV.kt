package com.dhorby.semantics.data

import com.dhorby.semantics.model.Article
import java.io.File

object ImportCSV {

    fun readFile(fileName: String): List<String> = File(fileName).useLines { it.toList() }

}

fun List<String>.toArticleList(): List<Article> {
    return this.map { line ->
        val split: List<String> = line.split("\t")
        Article(split[0], split[1], split[2].replace("\"", ""))
    }
}
