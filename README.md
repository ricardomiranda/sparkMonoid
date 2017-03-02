Project sparkMonoid is an example of using a monoid. We chose the Spark framework because it has function aggregate for non-monaidic operations and function reduce for monadic operations. Lets look at a function that concatenates characters (example from [F# for fun and profit](https://fsharpforfunandprofit.com/posts/monoids-part3/ "F# for fun and profit")):

```
'a' + 'b' => what should I do?
```

Our non-monadic approach will be to convert the result to a string:

```
'a' + 'b' => "ab"
```

For the monadic solution we will use lists:

```
List('a') ++ List('b') => List('a', 'b') //A string is a list of characters!
```
(c) Ricardo Miranda, 2017.
