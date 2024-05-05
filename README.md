# Median Tree Heap using-Java
Final project in course - data structures and algorithms.
The implementation includes 2 stages. The heaps were implemented using a binary tree where each node has a left and right child.
Given assumptions are:
* no two elements with the same key will be entered into any of the data structures.
* there is no assumption on upper limit on the number of elements in any of the data structures.

  
**First Stage of Implementation - MinTreeHeap & MaxTreeHeap Classes**'

1)
I created class called *MinTreeHeap* (MinTreeHeap.java).
This class represents a minimum heap and includes the following methods:
* public static MinTreeHeap BuildHeapT(int[] A): Takes an array A of size n and returns a MinTreeHeap object where the keys are the minimums of the array A. Required complexity: O(n).
* public void HeapInsert(int k): Method that inserts an item with key k into the heap. Note that there is no upper limit to the number of items that can be inserted. Required complexity: O(log(n)).
* public int HeapExtractMin(): Extracts and returns the key of the node with the minimum key in the heap. Required complexity: O(log(n)).
* public void printByLayer(DataOutputStream out): This method takes a reference to a DataOutputStream object and prints the keys of the heap's elements, layer by layer, under the following requirements:
  Print keys of elements that belong to the i-th level of the heap in the i+1-th line of the output stream. Keys of elements at the same level are separated by commas. The keys for each level are printed from       left to right.

2)
*MaxTreeHeap Class* (MaxTreeHeap.java):
This class represents a maximum heap and includes methods similar to those in the MinTreeHeap class, adjusted for maximum heap operations.


**Second Stage of Implementation - MedianDS Class**

I defined a class named MedianDS.java.
The MedianDS class uses the MinTreeHeap and MaxTreeHeap classes to implement the following public methods:
* public MedianDS(int[] A): Constructor that takes an array and stores its elements in the class's data structures. Required complexity: O(n).
* public void insert(int x): Takes a new key and stores it in the data structures. There is no upper limit on the number of items that can be inserted. Required complexity: O(log(n)).
* public void delMedian(): Deletes the element with the median key in the data structures. Required complexity: O(log(n)).
* public int findMedian(): Returns the key of the median element among those still in the data structures, those given to the constructor, or those not deleted by the delMedian method. Required complexity: O(1).
  

**Imports used:**
import java.io.DataOutputStream
import java.io.IOException

Also I didn't use System class in my code except for System.lineSeparator().

