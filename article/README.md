# Monoids applied to Spark #

## Introduction ##

This is a short intro to **monoids**. Often times, when I speak with friends and colleagues about [category theory][2], I am asked: "This is all very nice but what is in it for me? Will I ever use it?". This article aims at showing a compelling example of a real world problem where a monoid is a much better approach than a naive, non monoid approach.

## So, what is a monoid? ##

A monoid is a pure operation that takes two arguments and has the following characteristics:

- **closure**, the operation over two elements produces an element of the same kind;

- **associativity**, if you have a set of ordered elements to combine, it produces the same result regardless of the order you do the pairwise operations; and

- **identity element**, there is an element that, operated with any other element, left or right, produces the same element.

Addition of Natural numbers is a monoid. It follows the three above rules: addition of two integers produces an integer; it is associative and zero is the identity element. Addition is also commutative but that is not necessary to be a monoid.

Concatenating lists is also a monoid operation. When you concatenate two lists you get a list. If you have lots of list to concatenate you can do it in any order you want as long as you keep their relative positions. The identity element is the empty list.

If you have closure and associativity but not an identity element you have a **semigroup**.

## Advantages of having a **monoid** ##

A monoid is a parallelizable operation. If you have monoid and a list of elements to whom you want to apply it, you can chop it in any way, apply the operation to each section, and later on collect the partial results - preserving the original order - and apply the operation to them. This is parallelization without headaches.

## A real world exmple in Spark ##

Here I will present a very simple operation, that is not a monoid, and use it in Spark with the aggregate function. Afterwards I will show how to transform this operation into a monoid. Having a monoid we can use Spark's reduce function.

### Joining characters ###

Suppose you want to join characters:

```
'a' + 'b' => What should I get?
```

The first solution that comes to mind is to create a string:

```
'a' + 'b' => "ab"
```

The above operation is not a monoid; there is no closure on this operation. The operation's result is not of the same type as its arguments.

### Using Spark's aggregate function ###

To use the non monoid above described in Spark we need [aggregate][1]:

```
def aggregate[U](zeroValue: U)(seqOp: (U, T) ⇒ U, combOp: (U, U) ⇒ U)(implicit arg0: ClassTag[U]): U

Aggregate the elements of each partition, and then the results for all the partitions, using 
given combine functions and a neutral "zero value". This function can return a different result 
type, U, than the type of this RDD, T. Thus, we need one operation for merging a T into an U and 
one operation for merging two U's, as in scala.TraversableOnce. Both of these functions are 
allowed to modify and return their first argument instead of creating a new U to avoid memory 
allocation.

zeroValue: the initial value for the accumulated result of each partition for the seqOp operator, 
and also the initial value for the combine results from different partitions for the combOp 
operator - this will typically be the neutral element (e.g. Nil for list concatenation or 0 for 
summation)

seqOp: an operator used to accumulate results within a partition

combOp: an associative operator used to combine results from different partitions
```

A possible implementation would be:

```scala
package uk.co.marionete.sparkMonoid

import org.apache.spark.sql.SparkSession
import org.apache.spark.rdd._

case class Aggregate(charactersRDD: RDD[Char], spark: SparkSession) {
  def agg: String = {
    this.charactersRDD.aggregate("") (
      (acc, c) => acc + String.valueOf(c),
      (acc1, acc2) => acc1 + acc2
    )
  }
}

object Aggregate {
  def apply(cs: Seq[Char], spark: SparkSession) = {
    val charactersRDD = spark.sparkContext.parallelize(cs)
    new Aggregate(charactersRDD, spark)
  }
}
```

### Using Spark's reduce ###

A possible solution to transform our join into a monoid is to use lists:

```
['a'] ++ ['b'] => ['a', 'b']
```

Now that we have a monoid - remember that concatenation of lists is a monoid - it is possible to use Spark's reduce function. Lets look at a possible implementation in Scala.

```scala
package uk.co.marionete.sparkMonoid

import org.apache.spark.sql.SparkSession
import org.apache.spark.rdd._

case class Monadic(charactersRDD: RDD[Seq[Char]], spark: SparkSession) {
  def red: String = this.charactersRDD.reduce{ (x, y) => x ++ y }.mkString
}

object Monadic {
  def apply(cs: Seq[Char], spark: SparkSession) = {
    val charactersRDD = spark.sparkContext.parallelize(cs.map(Seq(_)))
    new Monadic(charactersRDD, spark)
  }
}
```

(c) Ricardo C. Miranda, 2017.

[1]: https://spark.apache.org/docs/latest/api/scala/index.html#org.apache.spark.rdd.RDD
[2]: https://en.wikipedia.org/wiki/Category_theory
