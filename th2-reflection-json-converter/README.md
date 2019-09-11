***JSON converter***

Your task will be to create JSON (https://www.json.org/, https://en.wikipedia.org/wiki/JSON) converter.
Please implement ```ua.hillel.java.elementary1.reflection.JsonConverter``` interface
and put you code under ```ua.hillel.java.elementary1.reflection.implementations``` package.
Names of the JSON fields should be same as field names.

1. Convertion should be done using reflection.
2. Please make first convertion without any nested objects. 
All fields of the object can be considered primitives or strings. 
3. (*) When 1 and 2 parts are done - you can add also support for arrays.
4. (*) When 3 is done. You can add support for sub-objects.

Example : 
  
  ``` class A {int a; A(int a) {this.a = a}}```<br/>
  ```A a = new A(10)```<br/>
  ```new JsonConverter().toJson(a) ==  {"a": 10} ```
 
**Tests**

Write test to check you code. Check trivial cases and exceptional cases. 

**Op-Optionally (or part 2)**

1. Add new annotation called ```@JsonField``` with optional parameter ``value`` which can override
field name in serialization.

Example: 
  
     class Test { int v; } will be converted as {"v": 0}
     class Test { @JsonField("test") int v; } will be converted as {"test": 0}

2. Please add annotation ```@Accessor``` on the type level with enum as value ``enum Type {FIELD, METHOD}``
which tell the way how data will be get and set: Field - via ```java.lang.reflect.Field```, 
Method - via getters and setters. Default - Field.

Example
    
    @Accessor class Test {}


**Implementation**

I suggest the following in basic implementations:

1. When creating of the result object use ```StringBuilder``` class to add strings in the loop.
2. Get list of all fields of the class using ```getDeclaredFields```
3. If field is private -> to get or set his value use ```java.lang.reflect.Field.setVisible(true)```
https://stackoverflow.com/questions/1196192/how-to-read-the-value-of-a-private-field-from-a-different-class-in-java
4. Craate object during decoding using ```newInstance```.


**Good luck!**