package ua.hillel.java.elementary1.stream.implementations.kosenkov;

import ua.hillel.java.elementary1.stream.WordsSearcher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class WordSearcherWithStreams implements WordsSearcher {

    // The list of words of the given text string to search in.
    private List<String> wordList = new ArrayList<>();

    // Initialize the operations with words searcher
    // by splitting the text string into words.
    // Should be invoked before any other operation.
    @Override
    public void init(String text) {
        wordList.clear();
        wordList.addAll(splitToList(text));
    }

    @Override
    public void initFromFile(String filename) {
        File file = new File(filename);
        try (FileReader fileReader = new FileReader(file)) {
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            wordList.clear();
            while (line != null) {
                wordList.addAll(splitToList(line));
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Returns true if the initial text contains the word.
    @Override
    public boolean isWordExists(String word) {
        return wordList.stream().anyMatch(s -> s.equals(word));
    }

    // .
    @Override
    public Collection<String> search(Predicate<String> rule) {
        return wordList.stream()
                .filter(s -> rule.test(s))
                .collect(Collectors.toList());
    }

    // Returns the number of occurrences of the given word in the initial text.
    @Override
    public int countOccurrence(String word) {
        return wordList.stream()
                .filter(s -> s.equals(word))
                .mapToInt(e -> 1)
                .sum();
    }

    @Override
    public Map<String, Long> allWithOccurrences() {
        return wordList.stream()
                .collect(Collectors.groupingBy(
                        Function.identity(), Collectors.counting()));
    }

    /**
     * Splits the given string into the list of words
     * by any non-alpha-numeric character.
     *
     * @param txt input string
     *
     * @return the list of words
     */
    private List<String> splitToList(String txt) {
        // Split the string into array of words
        // by any non-alpha-numeric character.
        // Return the list, backed by obtained array.
        String[] words = txt.split("\\W+");
        return Arrays.asList(words);
    }
}
