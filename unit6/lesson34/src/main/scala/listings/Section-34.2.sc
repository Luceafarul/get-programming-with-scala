Set() + 1
// val res0: scala.collection.immutable.Set[Int] = Set(1)
// adding 1 to the empty set

Set(1) + 2
// val res1: scala.collection.immutable.Set[Int] = Set(1, 2)
// adding 2 to a set containing one element

Set(1) + 1
// val res2: scala.collection.immutable.Set[Int] = Set(1)
// a set contains no duplicates

Set(1, 2) - 2
// val res3: scala.collection.immutable.Set[Int] = Set(1)
// remove the element 2 from the set

Set(1, 2) - 3
// val res4: scala.collection.immutable.Set[Int] = Set(1, 2)
// removing an element not in the set returns the same set

Set() - 2
// val res5: scala.collection.immutable.Set[Int] = Set()
// removing an element from the empty set, returns the empty set
