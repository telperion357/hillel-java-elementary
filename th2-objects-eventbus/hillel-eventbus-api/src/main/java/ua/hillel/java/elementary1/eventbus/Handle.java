package ua.hillel.java.elementary1.eventbus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation tells event bus about concrete subscription type of
 * the event.
 * <p>
 * Example of the usage: @Handle("a") public void handle(Object o);
 * This will register current handler to handle events with type "a" by method "handle".
 */
// Tells when this annotation is visible.
@Retention(RetentionPolicy.RUNTIME)
// Tells exact elements this annotation should be applied
@Target(ElementType.METHOD)
public @interface Handle {
    /**
     * Type of the event to handle.
     *
     * @return the string
     */
    String type();
}
