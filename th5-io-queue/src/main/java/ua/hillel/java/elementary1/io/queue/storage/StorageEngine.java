package ua.hillel.java.elementary1.io.queue.storage;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StorageEngine {
    public static final String FORMAT = "__%s.queue";
    public static final String SHIFTS = "__%s.shft";
    private static final long MAX_FILE_SIZE = 10_000_000;

    private Map<String, Long> shifts;
    private Map<String, ByteChannel> ostreams;
    private Map<String, ByteChannel> istreams;
    private Map<String, ByteChannel> sfts;

    public StorageEngine() {
        this.shifts = new HashMap<>();
        this.ostreams = new HashMap<>();
        this.istreams = new HashMap<>();
        this.sfts = new HashMap<>();
    }

    public void offer(String queue, String message) throws IOException {
        ByteChannel os = ostreams
                .computeIfAbsent(queue, k -> {
                    try {
                        Path file = Paths.get(String.format(FORMAT, queue));
                        return Files.newByteChannel(file,
                                StandardOpenOption.CREATE,
                                StandardOpenOption.APPEND);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
        ByteUtils.writeMessage(message.getBytes(), os);
    }

    public List<String> getMessages(String queue) throws IOException {
        Path path = Paths.get(String.format(FORMAT, queue));
        if (!Files.exists(path)) {
            return Collections.emptyList();
        }
        ByteChannel is = istreams.computeIfAbsent(queue, k -> {
            try {
                SeekableByteChannel st = Files.newByteChannel(path, StandardOpenOption.READ);
                long shift = getShift(queue);
                shifts.put(queue, shift);
                st.position(shift);
                return st;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        List<ByteBuffer> buffers = ByteUtils.readLengthMessages(is);
        if (buffers == null) {
            return Collections.emptyList();
        }
        List<String> messages = new ArrayList<>();
        long shift = shifts.getOrDefault(queue, 0L);
        for (ByteBuffer b : buffers) {
            b.flip();
            messages.add(new String(b.array()));
            shift += 4 + b.capacity();
        }
        storeShift(queue, shift);
        shifts.put(queue, shift);
        cleanUp(queue, path);
        return messages;
    }

    private void storeShift(String queue, long shift) throws IOException {
        ByteChannel os = sfts
                .compute(queue, (k, v) -> {
                    try {
                        Path path = Paths.get(String.format(SHIFTS, queue));
                        return Files.newByteChannel(path,
                                StandardOpenOption.WRITE,
                                StandardOpenOption.CREATE);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        byteBuffer.putLong(shift);
        byteBuffer.flip();
        os.write(byteBuffer);
        os.close();
    }

    private long getShift(String queue) throws IOException {
        Path path = Paths.get(String.format(SHIFTS, queue));
        if (!Files.exists(path)) {
            return 0;
        }
        try (ByteChannel stream = Files.newByteChannel(path)) {
            ByteBuffer buffer = ByteBuffer.allocate(8);
            if (stream.read(buffer) < buffer.capacity()) {
                return 0;
            }
            buffer.flip();
            return buffer.getLong();
        }
    }

    private void cleanUp(String queue, Path path) throws IOException {
        if (Files.size(path) < MAX_FILE_SIZE) {
            return;
        }
        Files.delete(path);
        shifts.remove(queue);
        ostreams.remove(queue);
        istreams.remove(queue);
    }
}
