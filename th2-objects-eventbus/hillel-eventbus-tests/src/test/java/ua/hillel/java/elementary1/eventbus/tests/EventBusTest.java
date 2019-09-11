package ua.hillel.java.elementary1.eventbus.tests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.reflections.Reflections;
import ua.hillel.java.elementary1.eventbus.Event;
import ua.hillel.java.elementary1.eventbus.EventBus;
import ua.hillel.java.elementary1.eventbus.Handle;
import ua.hillel.java.elementary1.eventbus.Handler;

import java.util.Collection;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EventBusTest {

    @Mock(lenient = true)
    private Event event;
    @Mock(lenient = true)
    private Event another;
    @Mock(lenient = true)
    private Handler handler;

    private Collection<EventBus> eventBuses;

    @Before
    public void setUp() {
        eventBuses = implementations(EventBus.class);
        when(event.getType()).thenReturn("test");
        when(another.getType()).thenReturn("a");
    }

    @Test
    public void testFire() {
        for (EventBus eventBus : eventBuses) {
            try {
                eventBus.fire(event);
            } catch (Exception e) {
                Assert.fail("Failed to fire event due to " + eventBus.getClass() + " : " + e);
            }
        }
    }

    @Test
    public void testHandle() {
        for (EventBus eventBus : eventBuses) {
            try {
                eventBus.subscribe("test", handler);
                eventBus.fire(event);

                // Check handler was handled.
                verify(handler, times(1)).handle(event);
            } catch (Exception e) {
                e.printStackTrace();
                Assert.fail("Failed to fire event due to " + eventBus.getClass() + " : " + e);
            }
        }
    }

    @Test
    public void testNotHandle() {
        for (EventBus eventBus : eventBuses) {
            try {
                eventBus.subscribe("test", handler);
                eventBus.fire(another);

                // Check handler never handled.
                verify(handler, never()).handle(event);
            } catch (Exception e) {
                e.printStackTrace();
                Assert.fail("Failed to fire event due to " + eventBus.getClass() + " : " + e);
            }
        }
    }

    @Test
    public void testReflectHandle() {
        for (EventBus eventBus : eventBuses) {
            try {
                TestHandler handler = spy(new TestHandler());
                eventBus.subscribe(handler);
                eventBus.fire(another);
                // Check handler was not handled.
                verify(handler, never()).doSomething(event);
            } catch (Exception e) {
                e.printStackTrace();
                Assert.fail("Failed to fire event due to " + eventBus.getClass() + " : " + e);
            }
        }
    }

    @Test
    public void testReflectNotHandle() {
        for (EventBus eventBus : eventBuses) {
            try {
                TestHandler handler = spy(new TestHandler());
                eventBus.subscribe(handler);
                eventBus.fire(event);
                // Check handler was handled.
                verify(handler, times(1)).doSomething(event);
            } catch (Exception e) {
                e.printStackTrace();
                Assert.fail("Failed to fire event due to " + eventBus.getClass() + " : " + e);
            }
        }
    }

    public static <T> Collection<T> implementations(Class<T> clazz) {
        Reflections reflections = new Reflections("ua.hillel.java.elementary1.eventbus");
        return reflections.getSubTypesOf(clazz).stream().map(cl -> {
            try {
                return cl.newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
    }

    public static class TestHandler {
        @Handle(type = "test")
        public void doSomething(Object o) {

        }
    }
}
