package uk.co.marionete.sparkMonoid

import org.scalatest._
import org.apache.spark.sql.SparkSession

class MonadicTest extends WordSpec with Matchers {
  def sparkStart: SparkSession = {
    val spark: SparkSession = SparkSession.builder()
      .appName("Testing Population Magic Squares wiht Spark")
      .master("local[*]")
      .getOrCreate()
    spark.sparkContext.setLogLevel("WARN")
    spark
  }

  "A new RDD of char" when {
    "with elementis 'a', 'b', 'c' and 'd'" should {
      "produce string \"abcd\"" in {
	val spark = sparkStart
        Monadic(Seq('a','b','c','d'), spark).agg shouldBe "abcd"
        spark.stop()
      }
    }

    "with element 'a'" should {
      "produce string \"a\"" in {
	val spark = sparkStart
        Monadic(Seq('a'), spark).agg shouldBe "a"
        spark.stop()
      }
    }

    "with elements 'a' and 'b'" should {
      "produce string \"ab\"" in {
	val spark = sparkStart
        Monadic(Seq('a','b'), spark).agg shouldBe "ab"
        spark.stop()
      }
    }
  }
}
