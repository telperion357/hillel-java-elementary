package ua.hillel.java.elementary1.arrays;

import org.reflections.Reflections;

import java.util.Collection;
import java.util.stream.Collectors;

public class Utils {
    public static  <T> Collection<T> implementations(Class<T> clazz) {
        Reflections reflections = new Reflections("ua.hillel.java.elementary1.arrays.implementations");
        return reflections.getSubTypesOf(clazz).stream().map(cl -> {
            try {
                return cl.newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
    }
}
