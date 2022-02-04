package com.dhorby.semantics

import org.apache.spark.SparkConf
import org.apache.spark.api.java.JavaRDD
import org.apache.spark.api.java.JavaSparkContext
import org.apache.spark.sql.SparkSession


class NaiveBayesBuilder(val spark: SparkSession)  {

    fun build() {

        val conf = SparkConf().setAppName("spark config").setMaster("master")
        val sc = JavaSparkContext(conf)

        val data = mutableListOf<Int>(1,2,3,4,5)

        val distData: JavaRDD<Int> = sc.parallelize(data)
    }

    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }
}
