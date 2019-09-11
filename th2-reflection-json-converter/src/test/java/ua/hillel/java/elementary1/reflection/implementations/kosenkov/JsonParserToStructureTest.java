package ua.hillel.java.elementary1.reflection.implementations.kosenkov;

import org.junit.Test;

import static org.junit.Assert.*;

public class JsonParserToStructureTest {

    @Test
    public void parseInteger() {
        JsonParserToStructure parser = new JsonParserToStructure();
        System.out.println(parser.parseJson("357"));
    }

    @Test
    public void parseFraction() {
        JsonParserToStructure parser = new JsonParserToStructure();
        System.out.println(parser.parseJson("357.55"));
    }

    @Test
    public void parseExponent() {
        JsonParserToStructure parser = new JsonParserToStructure();
        System.out.println(parser.parseJson("357.77e67"));
    }


    @Test
    public void parseObject() {
        JsonParserToStructure parser = new JsonParserToStructure();
        System.out.println(parser.parseJson("{\"a\":0,\"b\":0.0}"));
    }

    @Test
    public void parseEmptyString() {
        JsonParserToStructure parser = new JsonParserToStructure();
        String s = (String) parser.parseJson("\"\"");
        System.out.println(s);
    }

    @Test
    public void stringSubstring() {
        String a = "bbb";
        String b = a.substring(1,1);
        System.out.println(b);
    }



}