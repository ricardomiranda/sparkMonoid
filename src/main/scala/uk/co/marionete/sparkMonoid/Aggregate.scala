package uk.co.marionete.sparkMonoid

import org.apache.spark.sql.SparkSession
import org.apache.spark.rdd._

case class Aggregate(charactersRDD: RDD[Char],
	             spark: SparkSession) {
  def agg: String = {
    charactersRDD.aggregate("") (
	    (acc, c) => acc + String.valueOf(c),
	    (acc1, acc2) => acc1 + acc2
    )
  }
}

object Aggregate {
  def apply(cs: Seq[Char], 
	    spark: SparkSession) = {
    val charactersRDD = spark.sparkContext.parallelize(cs)
    new Aggregate(charactersRDD, spark)
  }
}
