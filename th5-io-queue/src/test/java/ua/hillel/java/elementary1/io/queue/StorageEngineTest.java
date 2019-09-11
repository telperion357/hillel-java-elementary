package ua.hillel.java.elementary1.io.queue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ua.hillel.java.elementary1.io.queue.storage.ByteUtils;
import ua.hillel.java.elementary1.io.queue.storage.StorageEngine;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static ua.hillel.java.elementary1.io.queue.storage.StorageEngine.FORMAT;
import static ua.hillel.java.elementary1.io.queue.storage.StorageEngine.SHIFTS;


public class StorageEngineTest {

    private static final String QUEUE_NAME = "test_q";

    private File sfile;
    private File qfile;

    @Before
    public void setUp() {
        qfile = new File(String.format(FORMAT, QUEUE_NAME));
        qfile.delete();
        sfile = new File(String.format(SHIFTS, QUEUE_NAME));
        sfile.delete();
    }

    @Test
    public void testSave() throws IOException {
        StorageEngine s = new StorageEngine();
        s.offer(QUEUE_NAME, "one");
        s.offer(QUEUE_NAME, "two");
        s.offer(QUEUE_NAME, "three");

        List<String> expected = Arrays.asList("one", "two", "three");
        assertEquals(expected, s.getMessages(QUEUE_NAME));
        long length = String.join("", expected).length() + 4 * expected.size();
        assertEquals(length, ByteUtils.longFrom(Files.readAllBytes(sfile.toPath())));
    }

    @Test
    public void testRestore() throws IOException {
        StorageEngine s1 = new StorageEngine();
        s1.offer(QUEUE_NAME, "one");
        s1.offer(QUEUE_NAME, "two");
        s1.offer(QUEUE_NAME, "three");
        assertFalse(sfile.exists());

        StorageEngine s2 = new StorageEngine();
        List<String> expected = Arrays.asList("one", "two", "three");
        assertEquals(expected, s2.getMessages(QUEUE_NAME));
        long length = String.join("", expected).length() + 4 * expected.size();
        assertEquals(length, ByteUtils.longFrom(Files.readAllBytes(sfile.toPath())));

        StorageEngine s3 = new StorageEngine();
        s3.offer(QUEUE_NAME, "four");
        s3.offer(QUEUE_NAME, "five");
        assertEquals(length, ByteUtils.longFrom(Files.readAllBytes(sfile.toPath())));

        StorageEngine s4 = new StorageEngine();
        expected = Arrays.asList("four", "five");
        assertEquals(expected, s4.getMessages(QUEUE_NAME));
        length += String.join("", expected).length() + 4 * expected.size();
        assertEquals(length, ByteUtils.longFrom(Files.readAllBytes(sfile.toPath())));

        StorageEngine s5 = new StorageEngine();
        assertEquals(Collections.emptyList(), s5.getMessages(QUEUE_NAME));
        s5.offer(QUEUE_NAME, "six");
        assertEquals(length, ByteUtils.longFrom(Files.readAllBytes(sfile.toPath())));

        StorageEngine s6 = new StorageEngine();
        assertEquals(Collections.singletonList("six"), s6.getMessages(QUEUE_NAME));
        length += "six".length() + 4;
        assertEquals(length, ByteUtils.longFrom(Files.readAllBytes(sfile.toPath())));
    }

    @After
    public void tearDown() {
        qfile.delete();
        sfile.delete();
    }
}