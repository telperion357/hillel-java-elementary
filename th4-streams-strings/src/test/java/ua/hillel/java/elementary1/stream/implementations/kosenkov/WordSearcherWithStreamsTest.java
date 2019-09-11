package ua.hillel.java.elementary1.stream.implementations.kosenkov;

import org.junit.Before;
import org.junit.Test;
import ua.hillel.java.elementary1.stream.WordsSearcher;

import java.io.File;
import java.util.Collection;
import java.util.Map;

import static org.junit.Assert.*;

public class WordSearcherWithStreamsTest {

    static final String TEST_TEXT = "three, one: two; three' -two, three.";
    static final String FILE_NAME = "src/main/resources/test.txt";
    WordsSearcher wordsSearcher;

    @Before
    public void setup() {
        wordsSearcher = new WordSearcherWithStreams();
        wordsSearcher.init(TEST_TEXT);
    }

    @Test
    public void isWordExists() {
        assertEquals(true, wordsSearcher.isWordExists("one"));
        assertEquals(true, wordsSearcher.isWordExists("two"));
        assertEquals(true, wordsSearcher.isWordExists("three"));
        assertEquals(false, wordsSearcher.isWordExists("five"));
    }

    @Test
    public void search() {
        // Search all words containing the letter "o".
        Collection<String> collectionO = wordsSearcher.search(s -> s.contains("o"));
        // Should be three words: {two, one, two}.
        assertEquals(3, collectionO.size());
        assertEquals(true, collectionO.contains("one"));
        assertEquals(true, collectionO.contains("two"));
        assertEquals(false, collectionO.contains("three"));
        // Search all words, starting with letters "th"
        Collection<String> collectionTh = wordsSearcher.search(s -> s.startsWith("th"));
        // Should be three words: {three, three, three}.
        assertEquals(3, collectionTh.size());
        assertEquals(true, collectionTh.contains("three"));
        assertEquals(false, collectionTh.contains("one"));
        assertEquals(false, collectionTh.contains("two"));
    }

    @Test
    public void countOccurrence() {
        assertEquals(1, wordsSearcher.countOccurrence("one"));
        assertEquals(2, wordsSearcher.countOccurrence("two"));
        assertEquals(3, wordsSearcher.countOccurrence("three"));
    }

    @Test
    public void searchByRegexp() {
        // Search all words ending with letters "ne".
        Collection<String> collectionNe = wordsSearcher.searchByRegexp("(?:ne)");
        // Should be one word: {one}.
        assertEquals(1, collectionNe.size());
        assertEquals(true, collectionNe.contains("one"));
        assertEquals(false, collectionNe.contains("two"));
        assertEquals(false, collectionNe.contains("three"));
    }

    @Test
    public void initFromFile() {
        // Check that word searcher was initialized by setup.
        // Get the collection of all words and compare its size
        // with the number of words in the setup string.
        Collection<String> collection = wordsSearcher.search(s -> true);
        assertEquals(6, collection.size());
        // Get the repo working directory path
        System.out.println("///-----------------///");
        File file = new File("working_dir_is_here");
        System.out.println(file.getAbsolutePath());
        System.out.println("///-----------------///");
        ///-----------------///
        //  /builds/djandrewd/hillel-elementary-java01/th4-streams-strings/working_dir_is_here
        ///-----------------///
        // Reinitialize word searcher with file test.txt
        wordsSearcher.initFromFile(FILE_NAME);
        // Check that word searcher was reinitialized from the given file.
        // Get the collection of all words and compare its size
        // with the number of words in the given file.
        collection = wordsSearcher.search(s -> true);
        assertEquals(10, collection.size());
        // System.out.println(collection);
    }

    @Test
    public void allWithOccurrences() {
        wordsSearcher.initFromFile(FILE_NAME);
        Map<String,Long> map = wordsSearcher.allWithOccurrences();
        // Check that map size equals to the number of words in the test file
        assertEquals(10, map.size());
        // Check that map contains key-value pairs for some words from the file,
        // and that word count for each word is 1, as in the file.
        assertEquals((long) 1, (long) map.get("one"));
        assertEquals((long) 1, (long) map.get("five"));
        assertEquals((long) 1, (long) map.get("ten"));
    }
}