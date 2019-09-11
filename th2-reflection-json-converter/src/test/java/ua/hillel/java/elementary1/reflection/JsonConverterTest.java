package ua.hillel.java.elementary1.reflection;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(Parameterized.class)
public class JsonConverterTest {


    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {new A()},
                {new A(1, 2.0, "test string")},
                {new B(new int[0], new A[0])},
                {new B(new int[]{1, 2, 3}, new A[]{new A(1, 1.0, "A1"), new A(2, 2.0, "A2")})},
                {new C(C.Type.A, new C.Type[]{C.Type.B, C.Type.C})},
                {new D(new B[]{new B(new int[]{1, 2, 3}, new A[]{new A(1, 1.0, "A1"), new A(2, 2.0, "A2")})})}
        });
    }

    private Object object;

    private Collection<JsonConverter> converters;
    private Gson gson;

    public JsonConverterTest(Object object) {
        this.object = object;
        this.gson = new Gson();
    }

    @Before
    public void setUp() throws Exception {
        converters = Utils.implementations(JsonConverter.class);
    }

    @Test
    public void testSerialization() {
        String expected = gson.toJson(object);
        for (JsonConverter converter : converters) {
            try {
                assertEquals(String.format("Failed on toJson using : %s %s", converter.getClass(), object),
                        expected, converter.toJson(object));
            } catch (Exception e) {
                e.printStackTrace();
                fail(String.format("Failed on toJson using : %s %s : %s", converter.getClass(), object, e));
            }
        }
    }

    @Test
    public void testDeserialization() {
        String expected = gson.toJson(object);
        for (JsonConverter converter : converters) {
            try {
                Object o2 = converter.fromJson(expected, object.getClass());
                Assert.assertEquals(String.format("Failed on fromJson using : %s %s", converter.getClass(), object),
                        o2, object);
            } catch (Exception e) {
                e.printStackTrace();
                fail(String.format("Failed on fromJson using : %s %s : %s", converter.getClass(), object, e));
            }
        }
    }

    /////////////////////
    // TEST CLASSES
    /////////////////////

    public static class A {
        int a;
        double b;
        String c;

        public A() {
        }

        A(int a, double b, String c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            A a1 = (A) o;
            return a == a1.a &&
                    Double.compare(a1.b, b) == 0 &&
                    Objects.equals(c, a1.c);
        }

        @Override
        public int hashCode() {
            return Objects.hash(a, b, c);
        }
    }

    public static class B {
        int[] ints;
        A[] as;


        public B() {
        }

        public B(int[] ints, A[] as) {
            this.ints = ints;
            this.as = as;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            B b = (B) o;
            return Arrays.equals(ints, b.ints) &&
                    Arrays.equals(as, b.as);
        }

        @Override
        public int hashCode() {
            int result = Arrays.hashCode(ints);
            result = 31 * result + Arrays.hashCode(as);
            return result;
        }
    }

    public static class C {
        enum Type {
            A, B, C, D
        }

        Type a;
        Type[] b;

        public C() {
        }

        public C(Type a, Type[] b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            C c = (C) o;
            return a == c.a &&
                    Arrays.equals(b, c.b);
        }

        @Override
        public int hashCode() {
            int result = Objects.hash(a);
            result = 31 * result + Arrays.hashCode(b);
            return result;
        }
    }

    public static class D {
        private B[] bs;

        public D() {
        }

        public D(B[] bs) {
            this.bs = bs;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            D d = (D) o;
            return Arrays.equals(bs, d.bs);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(bs);
        }
    }
}