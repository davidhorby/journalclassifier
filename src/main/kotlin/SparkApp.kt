import com.dhorby.semantics.LogisticRegressionBuilder
import org.apache.spark.sql.SparkSession


data class InternalClass(val content: String)
data class A(val internalClass: InternalClass)

object SparkApp {



    @JvmStatic
    fun main(args: Array<String>) {
        val sparkSession = SparkSession
            .builder()
            .config("spark.master", "local")
            .appName("Spark Application").orCreate

        LogisticRegressionBuilder(sparkSession).build()


        sparkSession.stop()
    }


}
