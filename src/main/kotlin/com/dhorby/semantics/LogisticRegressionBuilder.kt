package com.dhorby.semantics

import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.ml.evaluation.BinaryClassificationEvaluator
import org.apache.spark.ml.feature.StringIndexer
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.DataTypes.DoubleType
import org.apache.spark.sql.types.DataTypes.StringType
import org.apache.spark.sql.types.Metadata
import org.apache.spark.sql.types.StructField
import org.apache.spark.sql.types.StructType


class LogisticRegressionBuilder(val spark: SparkSession) {


    fun build() {

        // Load the training data
        val schema = StructType(
            arrayOf(
                StructField("score1", DoubleType, false, Metadata.empty()),
                StructField("score2", DoubleType, false, Metadata.empty()),
                StructField("result", StringType, false, Metadata.empty())
            )
        )

        val training = spark.read()
            .format("csv")
            .option("header", true)
            .option("delimiter", ",")
            .option("mode", "DROPMALFORMED")
            .schema(schema)
            .load("src/main/resources/exams.csv")
            .cache()


        training.show()
        training.printSchema()

        // Add a feature column

        // columns that need to added to feature column
        val cols = arrayOf("score1", "score2")

        // VectorAssembler to add feature column
        // input columns - cols
        // feature column - features
        val assembler = VectorAssembler()
            .setInputCols(cols)
            .setOutputCol("features")
        val featureDf = assembler.transform(training)
        featureDf.show()

        // StringIndexer define new 'label' column with 'result' column
        val indexer = StringIndexer()
            .setInputCol("result")
            .setOutputCol("label")
        val labelDf = indexer.fit(featureDf).transform(featureDf)
        labelDf.show()


        // split data set training and test
        // training data set - 70%
        // test data set - 30%
        val seed = 5043L
        val splitArray = DoubleArray(2)
        splitArray[0] = 0.7
        splitArray[1] = 0.3
        val randomSplit = labelDf.randomSplit(splitArray)

        val trainingSet = randomSplit.get(0)
        val testingSet = randomSplit.get(1)

        println("Random split size ${randomSplit.get(0).count()}")
        println("Random split size ${randomSplit.get(1).count()}")

        // train logistic regression model with training data set
        val logisticRegression = LogisticRegression()
            .setMaxIter(100)
            .setRegParam(0.02)
            .setElasticNetParam(0.8)
        val logisticRegressionModel = logisticRegression.fit(trainingSet)

        // run model with test data set to get predictions
        // this will add new columns rawPrediction, probability and prediction
        val predictionDf = logisticRegressionModel.transform(testingSet)
        predictionDf.show(10)//        val testData = spark.read().format("libsvm").load("src/main/resources/examspredict.txt")

        // evaluate model with area under ROC
        val evaluator = BinaryClassificationEvaluator()
            .setLabelCol("label")
            .setRawPredictionCol("prediction")
            .setMetricName("areaUnderROC")

        // measure the accuracy
        val accuracy = evaluator.evaluate(predictionDf)
        println(accuracy)
        /*
         * output
        0.85625
        */




    }


}
