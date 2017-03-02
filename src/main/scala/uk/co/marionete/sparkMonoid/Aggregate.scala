package com.marionete.magicsquare

import org.apache.spark.sql.SparkSession
import org.apache.spark.rdd._

case class Aggregate(characters: RDD[Char]) {
}

object Aggregate {
	/*
  def apply(populationSize: Int, 
            chromosomeSize: Long,
	    r: Random,
	    spark: SparkSession) = {
    def individuals(i: Long, list: Seq[Individual]): Seq[Individual] = 
      i match {
        case 0 => list
	case _ => individuals(i-1, Individual(chromosomeSize, r) +: list)
    }
    val individualsRDD =
      spark.sparkContext.parallelize((1 to populationSize).map(_ => (-1, Individual(chromosomeSize, r))))

    new Population(individualsRDD, spark)
  }
  */
}
