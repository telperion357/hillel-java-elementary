package ua.hillel.java.elementary1.eventbus.impl.kosenkov;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import ua.hillel.java.elementary1.eventbus.Event;
import ua.hillel.java.elementary1.eventbus.EventBus;
import ua.hillel.java.elementary1.eventbus.Handle;
import ua.hillel.java.elementary1.eventbus.Handler;

public class BasicEventBus implements EventBus {

    // Wrapper class to bundle together a handler object and a handler method,
    // extracted from the object by reflection
    private static class HandlerBundle {
        private Object handler;
        private Method handlerMethod;

        public HandlerBundle() {
            super();
        }

        public HandlerBundle(Object handler, Method handlerMethod) {
            this.handler = handler;
            this.handlerMethod = handlerMethod;
        }

        public Object getHandler() {
            return handler;
        }

        public void setHandler(Object handler) {
            this.handler = handler;
        }

        public Method getHandlerMethod() {
            return handlerMethod;
        }

        public void setHandlerMethod(Method handlerMethod) {
            this.handlerMethod = handlerMethod;
        }
    }

    // Handlers database which maps event types
    // with lists of handlers for the type
    Map<String, List<HandlerBundle>> subscribers;

    public BasicEventBus() {
        this.subscribers = new HashMap<>();
    }

    // Takes the list of subscribed handlers from the subscribers database
    // and invokes the handler method on each subscribed handler
    @Override
    public void fire(Event event) {
        // Get the list of handlers for the event type
        List<HandlerBundle> handlerList = subscribers.get(event.getType());
        // If the list of handlers exist for the specified event type, invoke all handler methods
        if (handlerList != null) {
            for (HandlerBundle handlerBundle : handlerList) {
                // Unwrap the handler method from the bundle
                Method handlerMethod = handlerBundle.getHandlerMethod();
                // Unwrap handler instance from the bundle
                Object handler = handlerBundle.getHandler();
                // Invoke handler method on handler instance
                try {
                    handlerMethod.invoke(handler, event);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    // Extracts the method handle() from handler object by reflection,
    // wraps it with handler into a handler bundle,
    // and ads the bundle to the list of handlers of the specified type
    // in the subscribers database
    @Override
    public void subscribe(String type, Handler handler) {
        // Extract method handle() by reflection
        Method handlerMethod;
        try {
            handlerMethod = handler.getClass().getDeclaredMethod("handle", Event.class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException();
        }
        // Wrap handler and method in the bundle,
        // add the bundle to the list and put it into the database
        subscribe(type, handler, handlerMethod);
    }

    // Get methods annotated by @Handle annotation,
    // if such methods are present in the handler object,
    // and put them properly to subscriber database
    @Override
    public void subscribe(Object handler) {
        // Get an array of declared methods by reflection
        Method[] methods = handler.getClass().getDeclaredMethods();
        for (Method method : methods) {
            // Check if method is annotated with Handle annotation
            // and get the annotation instance
            Handle handlerAnnotation = method.getDeclaredAnnotation(Handle.class);
            // If the method is annotated
            if (handlerAnnotation != null) {
                // Get from annotation the type of event to subscribe for
                String type = handlerAnnotation.type();
                // Wrap handler and method in the bundle,
                // add the bundle to the list and put it into the database
                subscribe(type, handler, method);
            }
        }
    }

    // wraps the handler with the handlerMethod into a handler bundle,
    // ads the bundle to the list of handlers of the specified type
    // in the subscribers database
    //
    private void subscribe(String type, Object handler, Method handlerMethod) {
        // Get the list of handlers for the type from the database
        List<HandlerBundle> typeHandlerList = subscribers.get(type);
        // If there is no list for that type yet, create one
        if(typeHandlerList == null) {
            typeHandlerList = new ArrayList<>();
        }
        // Wrap handler and method together in a bundle
        HandlerBundle handlerBundle = new HandlerBundle(handler, handlerMethod);
        // add the bundle to the list of handlers for the type
        typeHandlerList.add(handlerBundle);
        // put the list back into a database under the type key
        subscribers.put(type, typeHandlerList);
    }
}
