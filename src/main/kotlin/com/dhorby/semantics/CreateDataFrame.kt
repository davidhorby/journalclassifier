package com.dhorby.semantics

import com.dhorby.semantics.model.Article
import com.dhorby.semantics.model.ArticleExternal
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.Dataset
import org.apache.spark.sql.Row
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.catalyst.expressions.GenericRowWithSchema
import org.jetbrains.kotlinx.spark.api.toDS

class CreateDataFrame(val sparkSession: SparkSession) {

    fun build() {

        val article = Article("1234", "5678", "The rain in Spain falls mainly on the plain")
        val article2 = Article("ABC", "DEF", "The rain in Spain falls mainly on the plain")
        val articles = mutableListOf(article)
        val rdd: RDD<ArticleExternal> = sparkSession.toDS(listOf(ArticleExternal(article), ArticleExternal(article2)))
            .rdd()


        val dataFrame: Dataset<Row> = sparkSession.createDataFrame(rdd, ArticleExternal::class.java)

        val filter = dataFrame.filter {
            val article: GenericRowWithSchema = it.get(0) as GenericRowWithSchema
            val journalId = article.get(0)
            journalId.equals("DEF")
        }

        filter.show(false)
        println(filter.count())
    }
}
