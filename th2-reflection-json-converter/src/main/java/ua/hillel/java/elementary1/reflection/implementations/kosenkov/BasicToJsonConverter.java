package ua.hillel.java.elementary1.reflection.implementations.kosenkov;


import java.lang.reflect.Array;
import java.lang.reflect.Field;

public class BasicToJsonConverter {

    // Debugging method to get information about
    // what getClass returns for primitive classes
    static void printClass(Object o) {
        System.out.println(o.getClass());
    }

    public static String toJson(Object o) {
        // {"a": 10, "b": "test}"
        // "t"
        // ["a", "b"]
        // {"a": 10, "b": [1, 2]}

        if (o == null) {
            return null;
        }

        Class<?> clazz = o.getClass();

        // o is a primitive
        if (clazz == Integer.class ||
            clazz == Boolean.class ||
            clazz == Double.class)
        {
            return o.toString();
        }


        // o is a String or Enum
        if (clazz == String.class || clazz.isEnum()) {
            return "\"" + o.toString() + "\"";
        }

        // o is Array
        if (clazz.isArray()) {
            int arrLength = Array.getLength(o);
            StringBuilder arrBuilder = new StringBuilder();
            arrBuilder.append("[");
            for (int i = 0; i < arrLength; i++) {
                Object item = Array.get(o, i);
                arrBuilder.append(toJson(item));
                if (i + 1 < arrLength) {
                    arrBuilder.append(",");
                }
            }
            arrBuilder.append("]");
            return arrBuilder.toString();
        }

        // o is Object
        Field[] fields = clazz.getDeclaredFields();
        StringBuilder objBuilder = new StringBuilder();
        objBuilder.append("{");
        boolean needComma = false;
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            if(!field.isAccessible()) {
                field.setAccessible(true);
            }
            String name = field.getName();

            Object fieldValue = null;
            try {
                fieldValue = field.get(o);
            } catch (IllegalAccessException e) {
                System.out.println("Oops IllegalAccessException" + e);
            }

            if (fieldValue != null) {
                if (needComma) {
                    objBuilder.append(",");
                }
                String value = toJson(fieldValue);
                objBuilder.append("\"" + name + "\"" + ":");
                objBuilder.append(value);
                if (i + 1 < fields.length) {
                    needComma = true;
                }
            }
        }
        objBuilder.append("}");

        return objBuilder.toString();
    }

    public static class ToJsonExample{
        public int a = 10;
        public boolean flagg = true;
        public int[] arr =  {1, 2, 3};
        public String string = "Hello Json!";
    }

    public static void main(String[] args) {

        int b = 10;
        // class java.lang.Integer
        // not class java.lang.int!
        //
        printClass(b);

        boolean t = true;
        // class java.lang.Boolean
        // not class java.lang.boolean!
        //
        printClass(t);

        System.out.println(toJson(b));

        ToJsonExample example = new ToJsonExample();
        System.out.println(toJson(example));
    }
}
