package uk.co.marionete.sparkMonoid

import org.scalatest._
import org.apache.spark.sql.SparkSession

class AggreateTest extends WordSpec with Matchers {
  def sparkStart: SparkSession = {
    val spark: SparkSession = SparkSession.builder()
      .appName("Testing Population Magic Squares wiht Spark")
      .master("local[*]")
      .getOrCreate()
    spark.sparkContext.setLogLevel("WARN")
    spark
  }

  "A new RDD of char" when {
    "with element 'a'" should {
      "produce string \"a\"" in {
	val spark = sparkStart
        Aggregate(Seq('a'), spark).agg shouldBe "a"
        spark.stop()
      }
    }
  }
}
