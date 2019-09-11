package ua.hillel.java.elementary1.stream;

import java.util.Collection;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * Words searcher.
 */
public interface WordsSearcher {
    /**
     * Init searcher with text.
     *
     * @param text the text
     */
    void init(String text);

    /**
     * Init searcher with file.
     *
     * @param filename the file.
     */
    void initFromFile(String filename);

    /**
     * Is word exists.
     *
     * @param word the word
     *
     * @return the true in case of existence
     */
    boolean isWordExists(String word);

    /**
     * Search collection of words with given predicate.
     *
     * @param rule the rule of the search.
     *
     * @return the collection of words matching the rule.
     */
    Collection<String> search(Predicate<String> rule);

    /**
     * Search by regexp collection.
     *
     * @param pattern the pattern
     *
     * @return the collection
     */
    default Collection<String> searchByRegexp(String pattern) {
        Pattern p = Pattern.compile(pattern);
        return search(p.asPredicate());
    }

    /**
     * Count number of occurrence.
     *
     * @param word the word
     *
     * @return the number of occurrence.
     */
    int countOccurrence(String word);

    /**
     * All words with their occurrences.
     *
     * @return the map of words.
     */
    Map<String, Long> allWithOccurrences();
}
