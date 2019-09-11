package ua.hillel.java.elementary1.reflection.implementations.kosenkov;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class FromJsonStructureConverter {

    // Lets assume that we have parsed the Json string somehow
    // and obtained an object "jsonStructure" with the structure
    // representing the structure of a json string.
    //
    // A json object is represented with a HashMap of String names and Object substructures
    // A json array is represented with an array of Object substructures
    // A json enum is represented with a string value
    // A json string is represented with a string value
    // A json numerical primitive is represented with a Double value
    // A json boolean primitive is represented with a Boolean value
    // A json null is represented with a null
    //
    // fromJason creates an instance of "clazz" class from a jsonStructure defined above
    public static Object fromJson(Object jsonStructure, Class<?> clazz) {

        // null
        if (jsonStructure == null || clazz == null) {
            return null;
        }

        // Integer
        if (clazz == int.class || clazz == Integer.class) {
            // Check if jsonStructure has corresponding type
            if (!(jsonStructure instanceof Double)) {
                throw new IllegalArgumentException("Expected Double, get " + jsonStructure.getClass());
            }
            return ((Double) jsonStructure).intValue();
        }

        // Double
        if (clazz == double.class || clazz == Double.class) {
            // Check if jsonStructure has corresponding type
            if (!(jsonStructure instanceof Double)) {
                throw new IllegalArgumentException("Expected Double, get " + jsonStructure.getClass());
            }
            return (double) jsonStructure;
        }

        // Boolean
        if (clazz == boolean.class || clazz == Boolean.class) {
            // Check if jsonStructure has corresponding type
            if (!(jsonStructure instanceof Boolean)) {
                throw new IllegalArgumentException("Expected Boolean, get " + jsonStructure.getClass());
            }
            return (boolean) jsonStructure;
        }

        // String
        if (clazz == String.class) {
            // Check if jsonStructure has corresponding type
            if (!(jsonStructure instanceof String)) {
                throw new IllegalArgumentException("Expected String, get " + jsonStructure.getClass());
            }
            return (String) jsonStructure;
        }

        // Enum
        if (clazz.isEnum()) {
            // Check if jsonStructure has corresponding type
            if ( ! (jsonStructure instanceof String)) {
                throw new IllegalArgumentException("Expected String, get " + jsonStructure.getClass());
            }
            return enumFromJson(jsonStructure,clazz);
        }

        // Array
        if (clazz.isArray()) {
            // Check if jsonStructure has corresponding structure
            if ( ! jsonStructure.getClass().isArray()) {
                throw new IllegalArgumentException();
            }
            return arrayFromJson(jsonStructure, clazz);
        }

        // Object
        // Check if jsonStructure has corresponding structure
        if ( ! (jsonStructure instanceof Map)) {
            throw new IllegalArgumentException();
        }
        return objectFromJson(jsonStructure, clazz);

    }

    // Instantiates an enum of type clazz from the jsonStructure
    // jsonStructure is a string
    //
    private static Object enumFromJson(Object jsonStructure, Class<?> clazz) {

        return Enum.valueOf((Class<Enum>) clazz, (String) jsonStructure);
    } // enumFromJson

    // Instantiates an array of type clazz from the jsonStructure
    // jsonStructure is an array
    //
    private static Object arrayFromJson(Object jsonStructure, Class<?> clazz) {

        // Get the type of array components of clazz array
        Class<?> clazzComponentType = clazz.getComponentType();
        // Get array length
        int arrLength = Array.getLength(jsonStructure);

        // Instantiate new array of clazz components type
        Object newArray = Array.newInstance(clazzComponentType, arrLength);

        // Set newArray values
        for (int i = 0; i < arrLength; i++) {
            // Extract a subStructure from i-th position of jsonStructure array
            Object subStructure = Array.get(jsonStructure, i);
            // Construct an object of clazzComponentType from the substructure
            Object newArrComponent = fromJson(subStructure, clazzComponentType);
            // Set a value on i-th position of the new array with newArrComponent
            Array.set(newArray, i, newArrComponent);
        }
        return newArray;

    } // arrayFromJson

    // Instantiates an object of type clazz from the jsonStructure
    // jsonStructure is a HashMap
    //
    private static Object objectFromJson(Object jsonStructure, Class<?> clazz) {

        // Instantiate new object of a clazz type
        Object newObject = null;
        try {
            newObject = clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Get fields of clazz and set them accordingly in new object
        Field[] fields = clazz.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];

            // Set accessibility for private fields
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }

            // Get field name
            String fieldName = field.getName();
            // Get field type
            Class<?> fieldType = field.getType();

            // Get a substructure from jsonStructure by reflection
            Object subJasonStructure = null;
            try {
                // invoke a get method on jsonStructure
                Method get = jsonStructure.getClass().getDeclaredMethod("get", Object.class);
                subJasonStructure = get.invoke(jsonStructure, fieldName);
            } catch (Exception e) {
                throw new RuntimeException("Failed setting fields in object", e);
            }

            //  Transform subJasonStructure into the proper object according to the field type
            Object fieldValue = fromJson(subJasonStructure, fieldType);

            // Set field value
            try {
                field.set(newObject, fieldValue);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return newObject;

    } // objectFromJson
}
