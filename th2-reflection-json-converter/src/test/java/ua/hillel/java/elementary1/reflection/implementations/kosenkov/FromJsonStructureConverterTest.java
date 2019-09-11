package ua.hillel.java.elementary1.reflection.implementations.kosenkov;

import org.junit.Test;

import java.time.Month;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static ua.hillel.java.elementary1.reflection.implementations.kosenkov.FromJsonStructureConverter.fromJson;


public class FromJsonStructureConverterTest {

    // Test null arg jsonStructure
    @Test
    public void nullArgJsonStructure() {
        assertEquals(null, fromJson(null, Integer.class));
    }

    // Test null arg clazz
    @Test
    public void nullArgClazz() {
        assertEquals(null, fromJson("Whatever", null));
    }

    // Test the instantiation of an integer
    @Test
    public void clazzIntegerArgDouble() {
        // Create a test instance of int
        int a = 10;
        // Create a jsonStructure for an int
        double d = 10.0;

        assertEquals(a, fromJson(d, Integer.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void clazzIntegerArgInteger() {
        int d = 10;
        int a = 10;
        fromJson(d, Integer.class);
    }

    // Test the instantiation of a double
    @Test
    public void clazzDouble() {
        // Create a test instance of double
        double a = 100.0;
        // Create a jsonStructure for a double
        double d = 100.0;

        assertEquals(a, fromJson(d, Double.class));
    }

    // Test the instantiation of a boolean
    @Test
    public void clazzBoolean() {
        // Create a test instance of boolean
        boolean a = true;
        // Create a jsonStructure for a double
        boolean d = true;

        assertEquals(a, fromJson(d, Boolean.class));
    }

    // Test the instantiation of a string
    @Test
    public void clazzStringArgString() {
        assertEquals("Json", fromJson("Json", String.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void clazzStringArgNonstring() {
        int a = 10;
        fromJson(a, String.class);
    }

    // Test the instantiation of Enum
    @Test
    public void clazzEnum() {
        assertEquals(Month.MAY, fromJson("MAY", Month.class));
    }

    // Test the instantiation of an array of int
    @Test
    public void clazzArrayInt() {
        // Create a test array of int
        int[] a = new int[] {1, 2, 3};
        // Create a jsonStructure for an array of int
        double[] jsonStructure = new double[] {1.0, 2.0, 3.0};
        assertArrayEquals(a, (int[]) fromJson(jsonStructure, a.getClass()));
    }

    // Test the instantiation of an array of Enum
    @Test
    public void clazzArrayEnum() {
        // Create a test array of Month
        Month[] summer = new Month[] {Month.JUNE, Month.JULY, Month.AUGUST};
        // Create a jsonStructure for an array of Enum
        String[] jsonStructure = new String[] {"JUNE", "JULY", "AUGUST"};

        assertArrayEquals(summer, (Month[]) fromJson(jsonStructure, summer.getClass()));
    }

    // Test the instantiation of a 2dim array of Strings
    @Test
    public void clazzArrayArrayString() {
        // Create a test array of strings
        String[][] arr = new String[][] {{"ab", "cd", "efg"}, {"hi", "jk", "lmnop"}, {"qrst", "uvw", "xyz"}};
        // Create a jsonStructure for an array of Strings, which is the same array
        String[][] jsonStructure = new String[][] {{"ab", "cd", "efg"},  {"hi", "jk", "lmnop"}, {"qrst", "uvw", "xyz"}};

        assertArrayEquals(arr, (String[][]) fromJson(jsonStructure, arr.getClass()));
    }

    // Test the instantiation of a 2dim array of Enum
    @Test
    public void clazzArrayArrayEnum() {
        // Create a test 2dim array of Month
        Month[][] seasons = new Month[][] {
                {Month.DECEMBER, Month.JANUARY, Month.FEBRUARY},
                {Month.MARCH, Month.APRIL, Month.MAY},
                {Month.JUNE, Month.JULY, Month.AUGUST},
                {Month.SEPTEMBER, Month.OCTOBER, Month.NOVEMBER}
        };
        // Create a jsonStructure for a 2dim array of Enum
        String[][] jsonStructure = new String[][] {
                {"DECEMBER", "JANUARY", "FEBRUARY"},
                {"MARCH", "APRIL", "MAY"},
                {"JUNE", "JULY", "AUGUST"},
                {"SEPTEMBER", "OCTOBER", "NOVEMBER"}
        };

        assertArrayEquals(seasons, (Month[][]) fromJson(jsonStructure, seasons.getClass()));
    }

    // Test the instantiation of a simple Object
    @Test
    public void clazzSimpleObject() {
        // Create a test simple object instance
        SimpleObject simpleObject = new SimpleObject();
        simpleObject.setIntField(100);
        simpleObject.setStringField("bar");

        // Create jsonStructure, corresponding to the test simple object instance
        HashMap<String, Object> jsonStructure = new HashMap<>();
        jsonStructure.put("intField", 100.0);
        jsonStructure.put("stringField", "bar");

        assertEquals(simpleObject, (SimpleObject) fromJson(jsonStructure, SimpleObject.class));
    }

    // Test the instantiation of a complex Object
    @Test
    public void clazzComplexObject() {

        // Create a test simple object instance
        SimpleObject simpleObject = new SimpleObject();
        simpleObject.setIntField(10);
        simpleObject.setStringField("foo");

        // Create a test complex object instance
        ComplexObject complexObject = new ComplexObject();
        complexObject.setArrString(new String[] {"ab", "cd", "efg"});
        complexObject.setDoubleField(15.0);
        complexObject.setMonth(Month.AUGUST);
        complexObject.setSo(simpleObject);

        // Create a test simple object jsonStructure
        HashMap<String, Object> simpleStructure = new HashMap<>();
        simpleStructure.put("intField", 10.0);
        simpleStructure.put("stringField", "foo");

        // Create a test complex object jsonStructure
        HashMap<String, Object> complexStructure = new HashMap<>();
        complexStructure.put("doubleField", 15.0);
        complexStructure.put("arrString", new String[] {"ab", "cd", "efg"});
        complexStructure.put("month", "AUGUST");
        complexStructure.put("so", simpleStructure);

        assertEquals(complexObject, (ComplexObject) fromJson(complexStructure, ComplexObject.class));
    }

    // Simple class to test instantiation of Objects by fromJson()
    public static class SimpleObject {

        private int intField = 0;
        private String stringField;

        void setIntField(int intField) {
            this.intField = intField;
        }

        void setStringField(String stringField) {
            this.stringField = stringField;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SimpleObject that = (SimpleObject) o;
            return intField == that.intField &&
                    Objects.equals(stringField, that.stringField);
        }

        @Override
        public int hashCode() {
            return Objects.hash(intField, stringField);
        }
    }

    // Complex class to test instantiation of Objects by fromJson()
    public static class ComplexObject {

        double doubleField;
        Month month;
        String[] arrString;
        SimpleObject so;

        public void setArrString(String[] arrString) {
            this.arrString = arrString;
        }

        public void setDoubleField(double doubleField) {
            this.doubleField = doubleField;
        }

        public void setMonth(Month month) {
            this.month = month;
        }

        public void setSo(SimpleObject so) {
            this.so = so;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ComplexObject that = (ComplexObject) o;
            return Double.compare(that.doubleField, doubleField) == 0 &&
                    month == that.month &&
                    Arrays.equals(arrString, that.arrString) &&
                    Objects.equals(so, that.so);
        }

        @Override
        public int hashCode() {
            int result = Objects.hash(doubleField, month, so);
            result = 31 * result + Arrays.hashCode(arrString);
            return result;
        }
    }
}