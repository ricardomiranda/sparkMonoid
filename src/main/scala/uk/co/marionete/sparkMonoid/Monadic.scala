package uk.co.marionete.sparkMonoid

import org.apache.spark.sql.SparkSession
import org.apache.spark.rdd._

case class Monadic(charactersRDD: RDD[Char],
	           spark: SparkSession) {
  def agg: String = {
    ???
  }
}

object Monadic {
  def apply(cs: Seq[Char], 
	    spark: SparkSession) = {
    val size = 1
    val charactersRDD =
      spark.sparkContext.parallelize(cs)

    new Monadic(charactersRDD, spark)
  }
}
