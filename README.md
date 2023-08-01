# Skip-List using the Java Sorted Set interface
A skip-list is a data structure similar to a linked list, however while a linked list keeps track of its next, and occasionally its previous node, a skip-list has different layers where each element point the next, above, previous, and below element. In other words, a skip-list is a collection of linked list stacked on top of each other. <br/>

Read more about the thought process behind this implementation: [Project Progress Report](https://github.com/grxdiii/skip-list-implementation/blob/main/Project%20Progress%20Report.pdf)

## Compiling the Program
```
$ javac ./src/SkipListTestHarness.java
```
## Executing the Program
```
$ java ./SkipListTestHarness
```
### Relevant Methods
---
This method perform search operations. Given a specified value, ```search()``` attempts to find the closet value less than or equal to the given object. It's important to note that this is an internal method, meaning that it isn't accessible beyond the scope of the SkipListSet class.
```java
private SkipLIstSetItem<T> search(T value)
```
This method adds specified element in a skip list, depending on whether the element is already contained in the list. This method does not allow duplicates. Before performing the add operation, ```add()``` calls ```search()``` and checks to see if the element is within the list.
```java 
public boolean add(T value)
```
 This method removes specified element in a skip list, depending on whether the element is contained in the list. If the value is present, it is removed and the method returns true, otherwise the method returns false.
```java 
public boolean remove(Object value)
```
This method attempts balance the skip list to improve the performance of the search operations - it's important to note that this method does not achieve this successfully.
```java 
public void reBalance()
```

### Example Output
---
```
CASE: 100,000 strings of length 1,000, 10,000 finds, 5,000 removals.  Generating...
  SkipListSet add:    114ms  find:     13ms  del:      7ms  (5,236 missing) find:     12ms  
                                             bal:     20ms  (5,236 missing) find:     14ms  
  TreeSet     add:     38ms  find:      4ms  del:      3ms  (5,236 missing) find:      4ms  

CASE: 1,000,000 strings of length 1,000, 10,000 finds, 5,000 removals.  Generating...
  SkipListSet add:  2,340ms  find:     18ms  del:     11ms  (5,031 missing) find:     21ms  
                                             bal:     69ms  (5,031 missing) find:     24ms  
  TreeSet     add:    757ms  find:      8ms  del:      4ms  (5,031 missing) find:      7ms  

CASE: 1,000,000 strings of length 1,000, 100,000 finds, 50,000 removals.  Generating...
  SkipListSet add:  2,239ms  find:    220ms  del:    101ms  (52,422 missing) find:    184ms  
                                             bal:     52ms  (52,422 missing) find:    231ms  
  TreeSet     add:    753ms  find:     82ms  del:     52ms  (52,422 missing) find:     86ms  

CASE: 100,000 doubles, 10,000 finds, 5,000 removals.  Generating...
  LinkedList  add:      4ms  find:  1,030ms  del:    371ms  (5,254 missing) find:  1,528ms  
  SkipListSet add:     92ms  find:     11ms  del:      7ms  (5,254 missing) find:      7ms  
                                             bal:      7ms  (5,254 missing) find:      8ms  
  TreeSet     add:     28ms  find:      3ms  del:      2ms  (5,254 missing) find:      1ms  

CASE: 1,000,000 doubles, 10,000 finds, 5,000 removals.  Generating...
  SkipListSet add:  1,852ms  find:     18ms  del:     10ms  (5,023 missing) find:     16ms  
                                             bal:     94ms  (5,023 missing) find:     45ms  
  TreeSet     add:    406ms  find:      6ms  del:      3ms  (5,023 missing) find:      5ms  

CASE: 1,000,000 doubles, 100,000 finds, 50,000 removals.  Generating...
  SkipListSet add:  1,870ms  find:    190ms  del:    109ms  (52,474 missing) find:    210ms  
                                             bal:     82ms  (52,474 missing) find:    199ms  
  TreeSet     add:    399ms  find:     51ms  del:     30ms  (52,474 missing) find:     54ms  

CASE: 100,000 integers, 10,000 finds, 5,000 removals.  Generating...
  LinkedList  add:     11ms  find:    978ms  del:    229ms  (5,239 missing) find:  1,415ms  
  SkipListSet add:    127ms  find:      7ms  del:      4ms  (5,239 missing) find:      6ms  
                                             bal:      7ms  (5,239 missing) find:      7ms  
  TreeSet     add:     24ms  find:      2ms  del:      1ms  (5,239 missing) find:      2ms  

CASE: 1,000,000 integers, 10,000 finds, 5,000 removals.  Generating...
  SkipListSet add:  1,752ms  find:     17ms  del:     30ms  (5,032 missing) find:     22ms  
                                             bal:    124ms  (5,032 missing) find:     25ms  
  TreeSet     add:    383ms  find:      5ms  del:      3ms  (5,032 missing) find:      5ms  

CASE: 10,000,000 integers, 10,000 finds, 5,000 removals.  Generating...
  SkipListSet add: 38,274ms  find:     37ms  del:     20ms  (5,002 missing) find:     37ms  
                                             bal:  1,363ms  (5,002 missing) find:     44ms  
  TreeSet     add: 12,782ms  find:     14ms  del:      5ms  (5,002 missing) find:      8ms  

CASE: 10,000,000 integers, 1,000,000 finds, 500,000 removals.  Generating...
  SkipListSet add: 38,187ms  find:  4,089ms  del:  1,968ms  (524,445 missing) find:  3,672ms  
                                             bal:  1,528ms  (524,445 missing) find:  4,047ms  
  TreeSet     add: 12,992ms  find:  1,386ms  del:    772ms  (524,445 missing) find:  1,483ms  

CASE: 10,000,000 integers, 10,000,000 finds, 5,000,000 removals.  Generating...
  SkipListSet add: 38,854ms  find: 35,781ms  del: 20,818ms  (6,970,784 missing) find: 35,322ms  
                                             bal:    915ms  (6,970,784 missing) find: 36,143ms  
  TreeSet     add: 10,900ms  find:  9,191ms  del:  5,066ms  (6,970,784 missing) find: 10,090ms  

```
---
## Academic Integrity Statement
I, Gradi Tshielekeja Mbuyi, affirm that this program is entirely my own work and that I have neither developed my code together with any other person, nor copied any code from any other person, nor permitted my code to be copied or otherwise used by any other person, nor have I copied, modified, or otherwise used programs created by others. I acknowledge that any violation of the above terms will be treated as academic dishonesty.
