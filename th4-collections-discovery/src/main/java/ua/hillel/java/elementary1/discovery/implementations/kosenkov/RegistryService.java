package ua.hillel.java.elementary1.discovery.implementations.kosenkov;

import ua.hillel.java.elementary1.discovery.Registry;
import ua.hillel.java.elementary1.discovery.Service;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

//  1. Please extend ```ua.hillel.java.elementary1.discovery.Registry```
//     with your own interface witch consumes only
//     ```ua.hillel.java.elementary1.discovery.Service``` subclasses.
//
public interface RegistryService <T extends Service> extends Registry<T> {

}

// Vocabulary:
// Type parameters
// Type arguments
// Type erasure

/*
https://stackoverflow.com/questions/976441/java-generics-why-is-extends-t-allowed-but-not-implements-t

Q: I wonder if there is a special reason in Java for using always
"extends" rather than "implements" for defining bounds of type parameters.

Example:
public interface C {}
public class A<B implements C>{}
        is prohibited but
public class A<B extends C>{}
    is correct. What is the reason for that?

A: There is no semantic difference in the generic constraint language
between whether a class 'implements' or 'extends'.
The constraint possibilities are 'extends' and 'super' - that is,
is this class to operate with assignable to that other one (extends),
or is this class assignable from that one (super).
*/
