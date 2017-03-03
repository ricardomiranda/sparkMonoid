package uk.co.marionete.sparkMonoid

import org.apache.spark.sql.SparkSession
import org.apache.spark.rdd._

case class Monadic(charactersRDD: RDD[Seq[Char]],
	           spark: SparkSession) {
  def agg: String = {
    this.charactersRDD.reduce{ (x, y) => x ++ y }.mkString
  }
}

object Monadic {
  def apply(cs: Seq[Char], 
	    spark: SparkSession) = {
    val charactersRDD = spark.sparkContext.parallelize(cs.map(Seq(_)))
    new Monadic(charactersRDD, spark)
  }
}
