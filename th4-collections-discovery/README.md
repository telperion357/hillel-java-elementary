***Service Registry***

Your task will be create registry for holding services.


1. Please extend ```ua.hillel.java.elementary1.discovery.Registry``` with your 
own interface with consumes only ```ua.hillel.java.elementary1.discovery.Service```
subclasses.

2. Implement your service registry using Iterators, Collections and Lists.

**Weights** 

Each of service you will register must implement method ```weight()``` which is weight
of service in the registry. When selection of service this parameter must be counted
to return random service with 'weght-probability'. See example below:

1. Imagine you have services called UserService1 and UserService2 
and both have name 'user-service'.
2. UserService1 has weight 0.1 and UserService2 has weight 0.4
3. Some of all weights is 0.5 which must be counted as 100%
4. Then calling ```discover``` with name 'user-service' in 20% (0.1/0.5 ) cases will 
return  UserService1 and in 80% (0.4 / 0.5) rest will return UserService2
5. Imagine we will add new service UserService3 with same name 'user-service' 
and weight 1.0
6. After this UserService1 will be given 6% (0.1 / 1.5) of calls, UserService2 - 26%, 
UserService3 - 67% of all calls.

**TTL**

During register of the service you have to specify TTL (time-to-live) parameter.
Idea is next: when this time passed after register this service will be removed 
from the collection.

Implementation you will do must be simple and services must be removed on demand.
Meaning only when calling ```discover``` method.

Consider for time elapsed following code:
```
  long millisPassed = System.currentTimeInMillis() - timeOfRegisterInMillis;
  long ttlInMillis = unit.toMillis(ttl);
  if (millisPassed > ttlInMillis) {
      // Remove service from registry
  }
```

**LRU**

Note! This is optional. Consider removing service using LRU (least-recentry-used) fashion.
Each time service with name is selected you have to update call time of the service.
If TTL is passed since last time of the call with given name - service must be removed.


**Tests**

Write test to check you code. Check trivial cases and exceptional cases. 

Put you code under ```ua.hillel.java.elementary1.discovery.implementations``` package. 

**Implementation**

In Java you might use java.util.Random class to general random numbers.
Random numbers is general equally (!). 

Lets imagine you have 2 services with 30% and 70% weight distribution (see above).
You generate some number in range of 0 will 100 for example using 

```int n = new Random().nextInt(100);```

How do we know which service is register? 
If n selected is in (0, 30) interval then first one (30% of numbers)
if n > 30 then second one (70%) leftover. 
This is granted by Java random generator.

**Good luck!** 