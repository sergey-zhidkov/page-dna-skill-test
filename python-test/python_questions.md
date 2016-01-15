# PageDNA python exercises

1. Implement `flatten_list` such that `ListTestCase` passes

~~~
import unittest

def flatten_list(l):
	result = []
	for value in l:
	  if isinstance(value, list):
		  result.extend(flatten_list(value))
	  else:
		  result.append(value)
	return result

class ListTestCase(unittest.TestCase):
    def test_flatten_list(self):
        l = [[[1, 2, 3], [4, 5]], 6]
        self.assertEqual(flatten_list(l), [1,2,3,4,5,6])
        l = [None, [], [[1, 2, [[]], [3]], [[4], 5]], 6]
        self.assertEqual(flatten_list(l), [None,1,2,3,4,5,6])
~~~

2. What would be a better name for the following function?

~~~
def function(n):
    if n < 2:
        return n
    else:
        return function(n-1) + function(n-2)
~~~

Better name is Fibonacci.

3. Write a better implementation of function(n).

Easest way to improve speed of this function - use memoization, for example:
def memoize(f):
    cache = {}
    def helper(x):
        if x not in cache:
            cache[x] = f(x)
        return cache[x]
    return helper

@memoize
def fibonacci(n):
    if n < 2:
        return n
    else:
        return fibonacci(n-1) + fibonacci(n-2)

print(fibonacci(113))

Also, as I know, you can use formula to calculate Fibonacci value from any N.

4. What is the value of function(113)?

Fist time I tried to write this function in Java (my most comfortable language), but
there is not enough "long" type space (after the 92th value overflow happened), so you need
to use "BigInteger" and this solution becomes ugly.
In python there is no such problem.
Answer: 184551825793033096366333

5. Given the following:

~~~
| src         | dst         |
|-------------+-------------|
| nyc         | chicago     |
| chicago     | kansas city |
| chicago     | phoenix     |
| kansas city | nyc         |
| nyc         | kansas city |
~~~

   Represent the data statically, and implement a function `all_trips(src, dst)`
   that returns all possible trips between `src` and `dst`. For example,

   all_trips('nyc', 'kansas city')

   should return

   [['nyc', 'kansas city'], ['nyc', 'chicago', 'kansas city']]


You can find answer in the python-test/src/main/FindAllTrips.java file.
Java is my most comfortable language.
Almost all code was taken from the Algorithms (Robert Sedgewick) book. I made some modifications
to create this solution. Main part of this code - DirectedDFS class.
