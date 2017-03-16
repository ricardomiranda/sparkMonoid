package uk.co.marionete.sparkMonoid

import org.apache.spark.sql.SparkSession
import org.apache.spark.rdd._

case class Monadic(charactersRDD: RDD[Seq[Char]],
	           spark: SparkSession) {
  def red: Seq[Char]= this.charactersRDD.reduce{ (x, y) => x ++ y }
}

object Monadic {
  def apply(cs: Seq[Char], 
	    spark: SparkSession) = {
    val charactersRDD = spark.sparkContext.parallelize(cs.map(Seq(_)))
    new Monadic(charactersRDD, spark)
  }
}
