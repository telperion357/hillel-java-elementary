***Iterators and Lists***

I suggest for training start with writing reverse iterator. Same array iterator written on 
lection by which returns element in reverse order.

1. https://www.geeksforgeeks.org/linked-list-set-1-introduction/
2. https://www.interviewbit.com/courses/programming/topics/linked-lists/
3. https://introcs.cs.princeton.edu/java/43stack/

**Tasks**

1. Create RandomAccess iterator by extending 
```ua.hillel.java.elementary1.iterators.AbstractRandomAccessIterator```.
   You iterator will be initialized with array and each next() call must return 
   some random element from the remaining ones.
   For inspiration algorithm please follow https://www.rosettacode.org/wiki/Knuth_shuffle#Java
   All details inside class.
   
2. Create circular infinitive iterator. Iterator will be initialized with array. 
   When last element of the array is reached then pointer should point to first one.
   Extends class ```ua.hillel.java.elementary1.iterators.AbstractCircularIterator``` <br>
   Example: <p>
      ```CIter t = new CIter({1, 2, 3});```<br>
      ```t.next()``` returns 1 <br>
      ```t.next()``` returns 2 <br>
      ```t.next()``` returns 3 <br>
      ```t.next()``` returns 1 <br>
      ```t.next()``` returns 2 <br>
      ```t.next()``` returns 3 <br>
      
3. Implement all left operation of the ```ua.hillel.java.elementary1.iterators.AbstractLinkedQueue```.
   ```offer``` and ```add``` operations are already implemented. Please implement the rest.

3. Implement all left operation of the ```ua.hillel.java.elementary1.iterators.AbstractArrayList```.  


Put you code under ```ua.hillel.java.elementary1.iterators.implementations``` package. 