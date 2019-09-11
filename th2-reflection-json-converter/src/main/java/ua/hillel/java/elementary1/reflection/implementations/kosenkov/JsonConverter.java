package ua.hillel.java.elementary1.reflection.implementations.kosenkov;

public class JsonConverter implements ua.hillel.java.elementary1.reflection.JsonConverter {

    @Override
    public String toJson(Object o) {
        return BasicToJsonConverter.toJson(o);
    }

    @Override
    public Object fromJson(String json, Class<?> clazz) {

        JsonParserToStructure parserToStructure = new JsonParserToStructure();
        Object jsonStructure = parserToStructure.parseJson(json);

        return FromJsonStructureConverter.fromJson(jsonStructure, clazz);
    }
}
