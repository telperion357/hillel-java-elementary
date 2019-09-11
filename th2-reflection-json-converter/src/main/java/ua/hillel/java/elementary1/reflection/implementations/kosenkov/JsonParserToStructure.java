package ua.hillel.java.elementary1.reflection.implementations.kosenkov;

import java.util.ArrayList;
import java.util.HashMap;

public class JsonParserToStructure {

    private String jsonString;
    private int pos;

    public Object parseJson(String json) {
        this.jsonString = json;

        if (jsonString.isEmpty()) {
            throw new IllegalArgumentException("Empty json string");
        }

        return readValue();
    }


    private Object readNull() {

        // Check if null literal fits in json string bounds
        int nullLength = 4;
        if(pos + nullLength > jsonString.length()) {
            throw new IllegalArgumentException("Invalid json structure. Expected null literal, found end of string");
        }

        // Read a substring of 4 characters from position pos
        // and compare it with null string literal
        if(jsonString.substring(pos, pos + nullLength).equals("null")) {
            // Set position one character beyond null literal
            pos += nullLength;
            return null;
        } else {
            throw new IllegalArgumentException("Invalid json structure. Expected null literal, found "
                            + jsonString.substring(pos, pos + nullLength));
        }
    }

    private  Double readNumber() {

        // Memorize the number starting position
        int startingPos = pos;

        // Determine the number ending position
        // and check the valid json number format
        {
            if (jsonString.charAt(pos) == '-') {
                        pos++;
                    }

            // Check if at least one digit is present
            // Skip all consecutive digits
            if ((pos < jsonString.length()) && isAsciiDigit(jsonString.charAt(pos))) {
                skipDigits();
            } else {
                throw new IllegalArgumentException("Wrong json number. Should be at least one digit.");
            }

            // Check for fraction point and skip it
            // If present, check for at least one fraction digit
            // Skip all fraction digits
            if ((pos < jsonString.length()) && jsonString.charAt(pos) == '.') {
                pos++;
                if ((pos < jsonString.length()) && isAsciiDigit(jsonString.charAt(pos))) {
                    skipDigits();
                } else {
                    throw new IllegalArgumentException("Wrong json number format. Should be at least one digit.");
                }
            }

            // Check for exponent
            // If present, skip exponent digits
            if ((pos < jsonString.length()) && ((jsonString.charAt(pos) == 'e') || (jsonString.charAt(pos) == 'E'))) {
                pos++; // skip exp letter

                // skip exp sign
                if ((pos < jsonString.length()) && ((jsonString.charAt(pos) == '+') || (jsonString.charAt(pos) == '-'))) {
                    pos++;
                }

                // Check if exponent has at least one digit
                // Skip all exponent digits
                if ((pos < jsonString.length()) && isAsciiDigit(jsonString.charAt(pos))) {
                    skipDigits();
                } else {
                    throw new IllegalArgumentException("Wrong json number format. Should be at least one digit.");
                }
            }
        }

        // Extract the substring, containing the valid number
        // Parse double
        String number = jsonString.substring(startingPos, pos);
        return Double.parseDouble(number);

    } // End readNumber

    private Boolean readTrue() {
        // Check if null literal fits in json string bounds
        int trueLength = 4;
        if(pos + trueLength > jsonString.length()) {
            throw new IllegalArgumentException("Invalid json structure. Expected true literal, found end of string");
        }

        // Read a substring of 4 characters from position pos
        // and compare it with true string literal
        if(jsonString.substring(pos, pos + trueLength).equals("true")) {
            // Set position one character beyond true literal
            pos += trueLength;
            return true;
        } else {
            throw new IllegalArgumentException("Invalid json structure. Expected true literal, found "
                    + jsonString.substring(pos, pos + trueLength));
        }
    } // End readTrue

    private Boolean readFalse() {
        // Check if null literal fits in json string bounds
        int falseLength = 5;
        if(pos + falseLength > jsonString.length()) {
            throw new IllegalArgumentException("Invalid json structure. Expected true literal, found end of string");
        }

        // Read a substring of 4 characters from position pos
        // and compare it with null string literal
        if(jsonString.substring(pos, pos + falseLength).equals("false")) {
            // Set position one character beyond null literal
            pos += falseLength;
            return false;
        } else {
            throw new IllegalArgumentException("Invalid json structure. Expected false literal, found "
                    + jsonString.substring(pos, pos + falseLength));
        }
    } // End readFalse


    // Reads and returns the substring of jsonString
    // from the current position pos to the next unescaped quote character '"'.
    // Position index was placed one character beyond the opening quote character by the caller method
    // Sets the position index one character beyond the end quote
    //
    private String readString() {
        // Memorise the beginning of string position
        int beginIndex = pos;

        // Search for the next unescaped quote character
        do {

            // Check if position index is in string bounds
            if (pos >= jsonString.length()) {
                throw new IllegalArgumentException("Wrong json. Expected string value, found end of json");
            }

            // Skip any escaped character
            if(jsonString.charAt(pos) == '\\') {
                pos++;
                // Check if position index is in string bounds
                if (pos >= jsonString.length()) {
                    throw new IllegalArgumentException("Wrong json. Expected string value, found end of json");
                }
            }

            // Check for the string value closing quote character
            // Increment the position index
        } while ( ! (jsonString.charAt(pos++) == '\"'));

        return jsonString.substring(beginIndex, pos - 1);

    } // End readString

    // Parses json array and returns an array of parsed objects
    //
    // Reads a json string until the end of array character ']'
    // Saves values, separated by commas, into a new array list.
    // Returns an array from array list.
    // Position index was set by the caller method
    // one character beyond the opening char '[' of array
    // Sets the position index one character beyond the end of array ']'
    //
    private Object[] readArray() {
        //Create new array for return value
        ArrayList<Object> arrayList = new ArrayList<>();

        // Check if array is empty
        skipWhitespaces();
        if (jsonString.charAt(pos) == ']') {
            pos++; // increment position index to one char beyond closing bracket
            return arrayList.toArray();
        }

        // Read values of the json array one by one into an arrayList
        do {
            arrayList.add(readValue());
            skipWhitespaces();

            // Check if the current character is a proper json value separator (comma)
            // or a proper end of array character ']'.
            if ((jsonString.charAt(pos) != ',') && (jsonString.charAt(pos) != ']')) {
                throw new IllegalArgumentException("Wrong json array format");
            }

          // Check for the end of array right bracket character ']'
          // increment position index to one char beyond closing bracket
        } while (jsonString.charAt(pos++) != ']');

        return arrayList.toArray();

    } // End readArray

    // Parses json object and returns a HashMap of name/value pairs
    //
    // Reads a json string until the end of object character '}'
    // Saves name/value pairs into a new hashmap.
    // Name/value pairs are separated by commas in json string
    // Name strings are separated by colon ':' from values;
    //
    // Position index was set by the caller method
    // one character beyond the opening char '{' of the object
    // Sets the position index one character beyond the end char of the object '}'
    //
    private HashMap<String, Object> readObject() {

        // Create new hashmap
        HashMap<String, Object> hashMap = new HashMap<>();

        skipWhitespaces();

        // Check if the object is empty
        if (jsonString.charAt(pos) == '}') {
            pos++; // increment position index to one char beyond closing bracket
            return hashMap;
        }

        // Read name/value pairs one by one until the end of object character '}'
        // add name/value pairs to a new hashmap
        do {
            skipWhitespaces();

            // Check for the opening quote of a name string
            // Place position index one character beyond the opening quote
            if (jsonString.charAt(pos++) != '\"') {
                throw new IllegalArgumentException("Invalid json format. Opening quote expected");
            }

            // Read name
            String name = readString();

            skipWhitespaces();
            // Check for the name and value separator char ':'
            // Place position index one character beyond the colon
            if (jsonString.charAt(pos++) != ':') {
                throw new IllegalArgumentException("Name/value separator ':' expected");
            }

            // Read value object
            Object value = readValue();

            hashMap.put(name, value);

            skipWhitespaces();
            // Check for the name/value pair separator char ',' or end of object char '}'
            if ((jsonString.charAt(pos) != ',') && (jsonString.charAt(pos) != '}')) {
                throw new IllegalArgumentException("Wrong json. Expected separator \',\' or \'}\'");
            }
          // Check for the object closing bracket char '}'
          // Increment position index to one char beyond the closing bracket
        } while (jsonString.charAt(pos++) != '}');

        return hashMap;

    } // End readObject

    // Determines the type of value to read and calls the proper method
    private Object readValue() {

        skipWhitespaces();

        char currentChar = jsonString.charAt(pos);
        switch (currentChar) {
            case 'n':
                return readNull();

            case 't':
                return readTrue();

            case 'f':
                return readFalse();

            case '\"':
                pos++;
                return readString();

            case '[':
                pos++;
                return readArray();

            case '{':
                pos++;
                return readObject();

            case '-':
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
            return readNumber();
        }
        throw new IllegalArgumentException("Wrong jason format. Can not read value");
    }

    // Skips all consecutive whitespaces starting at current position index pos;
    // Puts position index one character beyond the last whitespace
    //
    private void skipWhitespaces() {

        try {
            char currentChar = jsonString.charAt(pos);
            while (currentChar == ' ') {
                currentChar = jsonString.charAt(++pos);
            }
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Invalid json format. Unexpected end of string");
        }
    }

    // Skips all consecutive digits starting at current position index pos;
    // Puts position index one character beyond the last digit
    //
    private void skipDigits() {

        if (pos >= jsonString.length()) {
            return;
        }
        char currentChar = jsonString.charAt(pos);
        while (isAsciiDigit(currentChar)) {
            pos++;
            if (pos >= jsonString.length()) {
                break;
            }
            currentChar = jsonString.charAt(pos);
        }
    }

    // Returns true if the char c is an ascii digit 0-9
    private boolean isAsciiDigit(char c) {
        return (c >= '0') && (c <= '9');
    }

}
