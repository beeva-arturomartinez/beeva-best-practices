# Code Optimization Best Practices Guide
At this point we're going to talk about the best practices for optimize the code, independant the programming language.

[Replace this logo] ![alt text](https://github.com/beeva/beeva-best-practices/blob/master/static/horizontal-beeva-logo.png "BEEVA")

## Index

* [Introduction](#introduction)
* [Concepts](#concepts)
	* [O Notation](#o-notation)
	* [Unnecessary code](#unnecessary-code)
* [Simple Data Structures](#simple-data-structures)
	* [Route](#route)
	* [Searching](#searching)
	* [Ordering](#ordering)
	* [Constants propagation](#constants-propagation)
* [Recursivity](#recursivity)
* [Complex Data Structures](#complex-data-structures)
	* [Hash tables](#hash-tables)
	* [Trees](#trees)
	* [Graphs](#graphs)
* [Profiling](#profiling)
	* [Profilers types depending on the response](#profilers-types-depending-on-the-response)
	* [Granularity](#granularity)
	* [Statistic's profilers](#statistics-profilers)
* [Instrumentation](#instrumentation)
* [Bucle optimization](#bucle-optimization)
* [Efficient exceptions management](#efficient-exceptions-management)
* [Tools](#tools)
	* [Loggers](#loggers)
	* [Death code detection](#death-code-detection)
* [References](#references)

## Introduction

In computer science, program optimization or software optimization is the process of modifying a software system to make some aspect of it work more efficiently or use fewer resources.In general, a computer program may be optimized so that it executes more rapidly, or is capable of operating with less memory storage or other resources, or draw less power.
Therefore we must try to convert existing programs in other programs that perform the same tasks in less time, with less memory requirements, or generally use resources optimally. Optimization can make sense on several levels, from the lowest (circuit development, code writing machine designed especially for architecture) to the highest levels of processing implementation, use and design of algorithms.
One might reduce the amount of time that a program takes to perform some task at the price of making it consume more memory or an application where memory space is at a premium, one might deliberately choose a slower algorithm in order to use less memory always thinking what best fits our needs.

**Levels of optimization**

- Design level.

- Algorithms and data structures.

- Source code level.

- Build level.

- Compile level.

- Assembly level.

- Run time.

We mention some **techniques for optimizing software**:

- Adequately designing applications

- Choose good algorithms

- Choose an appropriate data structure.

- Try to squeeze the full potential of the hardware used.

- Reuse of code.

- Types of variables used.

- Clean code.

- Strength Reduction.

- Perspective.

- Know the compiler to optimize.

- Loop unrolling.

- Merger of cycles.

- Distribution of cycles.

- Exchange of cycles.


## Concepts

### O Notation

The O (pronounced big-oh) is the formal method of expressing the upper bound of an algorithm's running time. It's a measure of the longest amount of time it could possibly take for the algorithm to complete.

More formally, for non-negative functions, f(n) and g(n), if there exists an integer n_{0} and a constant c > 0 such that for all integers n>n_{0}, f(n) ≤ cg(n), then f(n) is Big O of g(n). This is denoted as "f(n) = O(g(n))". If graphed, g(n) serves as an upper bound to the curve you are analyzing, f(n).

Note that if f can take on finite values only (as it should happen normally) then this definition implies that there exists some constant C (potentially larger than c) such that for all values of n, f(n) ≤ Cg(n). An appropriate value for C is the maximum of c and  \max_{1\le n\le n_0} f(n)/g(n).

Some examples of Big O notation include:

| Notation | Name | Examples |
|:-------------------|:--------|:-------------------:|
|  O(1) 	|  constant 	|   Determining if a number is even or odd; Using a constant-size lookup table; Using a suitable hash function for looking up an item. |
|O(log n)| 	logarithmic | 	Finding an item in a sorted array with a binary search or a balanced search tree as well as all operations in a Binomial heap.|
|O(n) | 	linear | Finding an item in an unsorted list or a malformed tree (worst case) or in an unsorted array; Adding two n-bit integers by ripple carry.|
| O(n log n) | 	linearithmic, loglinear, or quasilinear|Performing a Fast Fourier transform; heapsort, quicksort (best and average case), or merge sort|
|O(n^2) | 	quadratic |	Multiplying two n-digit numbers by a simple algorithm; bubble sort (worst case or naive implementation), Shell sort, quicksort (worst case), selection sort or insertion sort|
| O(c^n) , c>1 |	exponential | Finding the (exact) solution to the travelling salesman problem using dynamic programming; determining if two logical statements are equivalent using brute-force search |

![alt text](static/notationO.png "notation O")

Big O notation characterizes functions according to their growth rates: different functions with the same growth rate may be represented using the same O notation. The letter O is used because the growth rate of a function is also referred to as order of the function. A description of a function in terms of big O notation usually only provides an upper bound on the growth rate of the function. Associated with big O notation are several related notations, using the symbols o, Ω, ω, and Θ, to describe other kinds of bounds on asymptotic growth rates.

Big O notation is also used in many other fields to provide similar estimates

For more information click [here][linkonotation] .

[linkonotation]: https://en.wikipedia.org/wiki/Big_O_notation


### Unnecessary code

Several types of unnecessary code which would have to proceed to its removal:

* __Dead store__

    In computer programming, a local variable that is assigned a value but is not read by any subsequent instruction is referred to as a dead store. Dead stores waste processor time and memory, and may be detected through the use of static program analysis.
~~~
        DeadStoreExample
        
        import java.util.ArrayList;
        import java.util.Arrays;
        import java.util.List;
        
        public class DeadStoreExample {
         public static void main(String[] args) {
           List list = new ArrayList(); // This is a Dead Store, as the ArrayList is never read. 
           list = getList();
           System.out.println(list);
         }
        
         private static List getList() {
           return new ArrayList(Arrays.asList("Hello"));
         }
        }
 
~~~

* __Redundant code__

    Redundant code is source code or compiled code in a computer program that is unnecessary, such as recomputing a value that has previously been calculated[1] and is still available, code that is never executed (known as unreachable code), or code which is executed but has no external effect (e.g., does not change the output produced by a program; known as dead code).

~~~
        int foo(int iX)
        {
            int iY = iX*2;
        
            return iX*2;
        }
~~~
    
        The second iX*2 expression is redundant code and can be replaced by a reference to the variable iY. Alternatively, the definition int iY = iX*2 can instead be removed.

* __Duplicate code__

    Duplicate code is a sequence of source code that occurs more than once, either within a program or across different programs owned or maintained by the same entity. Duplicate code is generally considered undesirable for a number of reasons.A minimum requirement is usually applied to the quantity of code that must appear in a sequence for it to be considered duplicate rather than coincidentally similar. Sequences of duplicate code are sometimes known as code clones or just clones, the automated process of finding duplications in source code is called clone detection.
    
* __Unreachable code__

    Unreachable code is part of the source code of a program which can never be executed because there exists no control flow path to the code from the rest of the program.
Unreachable code is sometimes also called dead code, although dead code may also refer to code that is executed but has no effect on the output of a program.

~~~
        int foo (int iX, int iY)
         {
          return iX + iY;
          int iZ = iX*iY;
         }
~~~
    
        The definition int iZ = iX*iY; is never reached as the function returns before the definition is reached. Therefore the definition of iZ can be discarded.
        
* __Oxbow code__

    Oxbow code refers to fragments of program code that were once needed but which are now never used. Such code is typically formed when a program is modified, either when an item is superseded with a newer version but the old version is not removed, or when an item is removed or replaced, but the item's supporting code is not removed.

## Simple Data Structures

### Route

Traversing a singly linked list is the same as that of traversing a doubly linked
list . You start at the head of the list and continue until you
come across a node that is ∅. The two cases are as follows:
1. node = ∅, we have exhausted all nodes in the linked list; or
2. we must update the node reference to be node.Next.
The algorithm described is a very simple one that makes use of a simple
while loop to check the first case.

* 1) algorithm Traverse(head)
* 2) Pre: head is the head node in the list
* 3) Post: the items in the list have been traversed
* 4) n ← head
* 5) while n 6= 0
* 6) yield n.Value
* 7) n ← n.Next
* 8) end while
* 9) end Traverse

### Searching

* **Sequential Search**

    A simple algorithm that search for a specific item inside a list. It operates
    looping on each element O(n) until a match occurs or the end is reached.

    * 1) algorithm SequentialSearch(list, item)
    * 2) Pre: list 6= ∅
    * 3) Post: return index of item if found, otherwise −1
    * 4) index ← 0
    * 5) while index < list.Count and list[index] 6= item
    * 6) index ← index + 1
    * 7) end while
    * 8) if index < list.Count and list[index] = item
    * 9) return index
    * 10) end if
    * 11) return −1
    * 12) end SequentialSearch
 
* **Probability Search**

    Probability search is a statistical sequential searching algorithm. In addition to
    searching for an item, it takes into account its frequency by swapping it with
    it’s predecessor in the list. The algorithm complexity still remains at O(n) but
    in a non-uniform items search the more frequent items are in the first positions,
    reducing list scanning time.
    
    * 1) algorithm ProbabilitySearch(list, item)
    * 2) Pre: list 6= ∅
    * 3) Post: a boolean indicating where the item is found or not;
    * in the former case swap founded item with its predecessor
    * 4) index ← 0
    * 5) while index < list.Count and list[index] 6= item
    * 6) index ← index + 1
    * 7) end while
    * 8) if index ≥ list.Count or list[index] 6= item
    * 9) return false
    * 10) end if
    * 11) if index > 0
    * 12) Swap(list[index], list[index − 1])
    * 13) end if
    * 14) return true
    * 15) end ProbabilitySearch
    
### Ordering

The most popular algorithms are, click on each to learn more about them: 

[Bubble Sort][linkbubbleSort] , [Merge Sort][linkmergeSort] , [Quick Sort][linkquickSort] , [Insertion Sort][linkinsertionSort], [Shell Sort][linkshellSort] and
[Radix Sort][linkradixSort] .

[linkbubbleSort]: https://en.wikipedia.org/wiki/Bubble_sort
[linkmergeSort]: https://en.wikipedia.org/wiki/Merge_sort
[linkquickSort]: https://en.wikipedia.org/wiki/Quicksort
[linkinsertionSort]: https://en.wikipedia.org/wiki/Insertion_sort
[linkshellSort]: https://en.wikipedia.org/wiki/Shellsort
[linkradixSort]: https://en.wikipedia.org/wiki/Radix_sort


### Constants propagation

Constant propagation is the process of substituting the values of known constants in expressions at compile time. Such constants include those defined above, as well as intrinsic functions applied to constant values. 

Example:

In the code fragment below, the value of x can be propagated to the use of x.

~~~

        x = 4;
        y = x + 5;

~~~
  
Below is the code fragment after constant propagation and constant folding.

~~~

        x = 4;
        y = 9;

~~~

Some compilers perform constant propagation within basic blocks; some compilers perform constant propagation in more complex control flow.

Some compilers perform constant propagation for integer constants, but not floating-point constants.

Few compilers perform constant propagation through bitfield assignments.
Few compilers perform constant propagation for address constants through pointer assignments.

For more information click [here][linkconstantspropagation] .

[linkconstantspropagation]: https://en.wikipedia.org/wiki/Constant_folding


## Recursivity

Recursion in computer science is a method where the solution to a problem depends on solutions to smaller instances of the same problem (as opposed to iteration).The approach can be applied to many types of problems, and recursion is one of the central ideas of computer science.

"The power of recursion evidently lies in the possibility of defining an infinite set of objects by a finite statement. In the same manner, an infinite number of computations can be described by a finite recursive program, even if this program contains no explicit repetitions."

Most computer programming languages support recursion by allowing a function to call itself within the program text. Some functional programming languages do not define any looping constructs but rely solely on recursion to repeatedly call code. Computability theory proves that these recursive-only languages are Turing complete; they are as computationally powerful as Turing complete imperative languages, meaning they can solve the same kinds of problems as imperative languages even without iterative control structures such as “while” and “for”.

When we write a method for solving a particular problem, one of the basic design techniques is to break the task into smaller subtasks. For example, the problem of adding (or multiplying) n consecutive integers can be reduced to a problem of adding (or multiplying) n-1consecutive integers:
~~~

        1 + 2 + 3 +... + n = n + [1 + 2 + 3 + .. + (n-1)]
        
        1 * 2 * 3 *... * n = n * [1 * 2 * 3 * .. * (n-1)]

~~~

Therefore, if we introduce a method sumR(n) (or timesR(n)) that adds (or multiplies) integers from 1 to n, then the above arithmetics can be rewritten as

~~~

        sumR(n) = n + sumR(n-1)
        
        timesR(n) = n * timesR(n-1)

~~~

Such functional definition is called a recursive definition, since the definition contains a call to itself. On each recursive call the argument of sumR(n) (or timesR(n)) gets smaller by one. It takes n-1 calls until we reach the base case - this is a part of a definition that does not make a call to itself. Each recursive definition requires base cases in order to prevent infinite recursion.
In the following example we provide iterative and recursive implementations for the addition and multiplication of n natural numbers.

~~~

        public int sum(int n)                   public int sumR(int n)
        {                                       {
           int res = 0;                           if(n == 1)
           for(int i = 1; i = n; i++)                return 1;
              res = res + i;                      else
                                                     return n + sumR(n-1);
           return res;                          }
        }

~~~

To solve a problem recursively means that you have to first redefine the problem in terms of a smaller subproblem of the same type as the original problem. In the above summation problem, to sum-up n integers we have to know how to sum-up n-1 integers. Next, you have to figure out how the solution to smaller subproblems will give you a solution to the problem as a whole. This step is often called as a recursive leap of faith. Before using a recursive call, you must be convinced that the recursive call will do what it is supposed to do. You do not need to think how recursive calls works, just assume that it returns the correct result.

For more information click [here][linkrecursivity] .

[linkrecursivity]: https://en.wikipedia.org/wiki/Recursion_(computer_science)

## Complex Data Structures
### Hash tables

A Hash table is simply an array that is addressed via a hash function.

Hash tables are a simple and effective method to implement dictionaries. Average time to search for an element is *O(1)*, while worst-case time is *O(n)*.

A Hash table needs the following:

 - A data structure to hold the data.
 - A hashing function to map keys to locations in the data structure.
 - A collision-resolution policy that specifies what should be done when keys collide.

It is recommended to use Hash tables when you need faster searches in your data with this conditions:

 - The hash table occupation is not elevated.
 - we use function that generates uniformly distributed keys.

This operations may be slower in Hash tables:

 - Browse all elements of the Hash.
 - Rescaling the Hash size.

### Trees

Trees are highly recursive data structures that you can use to store hierarchical data and model decision processes.

A tree is a structure compounded with nodes, each node have the data and the relations with others nodes:

 - Root (actual node).
 - Children (relationship with their lower nodes).
 - Leaf is a node without children.

All the trees have a principal **root node**, that is the upper node in the tree.

There are different types of trees based on their design. We are not going to describe how to implement this data structure, we will only talk about the performance properties.

#### Binary trees

In this design we only have two children per node.

Binary trees are useful in many algorithms, partly because lots of problems can be modeled using binary choices and partly because binary trees are relatively easy to understand.

 - Searching operations on a *perfect tree* (a full tree where all the leaves are at the same level). that contains N nodes from the root to a leaf node, you must know that the algorithm needs only *O(log(N))* steps.
 - For randomly inserted data, search time is *O(lgn)*.
 - Worst-case behavior occurs when ordered data is inserted. In this case the search time is *O(n)*.

#### Balanced trees (AVL)

An AVL tree is a sorted binary tree in which the heights of two subtrees at any given node differ by at most 1. When a node is added or removed, the tree is rebalanced if necessary to ensure that the subtrees again have heights differing by at most 1.

Like other sorted trees, balanced trees let a program store and find values quickly. By keeping themselves balanced, ensure that they don’t grow too tall and thin, which would ruin their performance.

The better property of this types of trees is in their design, they are optimized for searches, trying to get the *perfect tree* approaching and their benefits.

Adding and removing values in a balanced tree takes longer than it does in an ordinary (nonbalanced) sorted tree. Those operations still take only *O(log N)* time, however, so the theoretical run time is the same even if the actual time is slightly longer. Spending that extra time lets the algorithm guarantee that those operations don’t grow to linear time.


### Graphs

## Profiling

### Profilers types depending on the response

### Granularity

### Statistic's profilers

## Instrumentation

## Bucle optimization

## Efficient exceptions management

## Tools

###Loggers

### Death code detection



### References

* [Program Optimization](https://en.wikipedia.org/wiki/Program_optimization)
* [Big O notation](https://en.wikipedia.org/wiki/Big_O_notation)
* [Dead code elimination](https://en.wikipedia.org/wiki/Dead_code_elimination)
* [Unreachable code](https://en.wikipedia.org/wiki/Unreachable_code)
* [Recursivity](https://en.wikipedia.org/wiki/Recursion_(computer_science))
* [Constant folding](https://en.wikipedia.org/wiki/Constant_folding)
* [Bubble sort](https://en.wikipedia.org/wiki/Bubble_sort)
* [Merge Sort](https://en.wikipedia.org/wiki/Merge_sort)
* [Quick Sort](https://en.wikipedia.org/wiki/Quicksort)
* [Insertion Sort](https://en.wikipedia.org/wiki/Insertion_sort)
* [Shell Sort](https://en.wikipedia.org/wiki/Shellsort)
* [Radix Sort](https://en.wikipedia.org/wiki/Radix_sort)

___

[BEEVA](http://www.beeva.com) | 2015
