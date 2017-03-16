package uk.co.marionete.sparkMonoid

import org.scalatest._
import org.apache.spark.sql.SparkSession

class AggreateTest extends WordSpec with Matchers {
  def sparkStart: SparkSession = {
    val spark: SparkSession = SparkSession.builder()
      .appName("Testing joning characters with Spark's aggregate")
      .master("local[*]")
      .getOrCreate()
    spark.sparkContext.setLogLevel("WARN")
    spark
  }

  "A new RDD of char" when {
    "with elementis 'a', 'b', 'c' and 'd'" should {
      "produce string \"abcd\"" in {
	val spark = sparkStart
        Aggregate(Seq('a','b','c','d'), spark).agg.toSet shouldBe Set('a', 'b', 'c', 'd')
        spark.stop()
      }
    }

    "with element 'a'" should {
      "produce string \"a\"" in {
	val spark = sparkStart
        Aggregate(Seq('a'), spark).agg.toSet shouldBe Set('a')
        spark.stop()
      }
    }

    "with elements 'a' and 'b'" should {
      "produce string \"ab\"" in {
	val spark = sparkStart
        Aggregate(Seq('a','b'), spark).agg.toSet shouldBe Set('a', 'b')
        spark.stop()
      }
    }
  }
}
