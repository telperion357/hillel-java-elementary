package ua.hillel.java.elementary1.io.queue.storage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ByteUtils {
    public static final ByteBuffer EMPTY = ByteBuffer.allocate(0);
    public static final byte[] EMPTY_B = {};

    private static final int BUFFER_SIZE = 1024;

    public static long longFrom(byte[] v) {
        return ByteBuffer.wrap(v).getLong();
    }

    public static byte[] read(ByteChannel channel) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
        int n = channel.read(buffer);
        if (n < 0) {
            return null;
        }
        byte[] result = {};
        while (n > 0) {
            byte[] t = new byte[n];
            buffer.flip();
            buffer.get(t);
            int pos = result.length;
            result = Arrays.copyOf(result, pos + n);
            System.arraycopy(t, 0, result, pos, n);
            //
            buffer.flip();
            n = channel.read(buffer);
        }
        return result;
    }

    public static List<ByteBuffer> readLengthMessages(ByteChannel channel) throws IOException {
        List<ByteBuffer> messages = new ArrayList<>();
        ByteBuffer current = readLengthMessage(channel);
        if (current == null) {
            return null;
        }
        if (current == EMPTY) {
            return Collections.emptyList();
        }
        messages.add(current);
        while ((current = readLengthMessage(channel)) != null) {
            if (current == EMPTY) {
                break;
            }
            messages.add(current);
        }
        return messages;

    }

    public static ByteBuffer readLengthMessage(ByteChannel channel) throws IOException {
        ByteBuffer sized = ByteBuffer.allocate(4);
        int read;
        if ((read = channel.read(sized)) < 0) {
            return null;
        }
        if (read == 0) {
            return EMPTY;
        }
        sized.flip();
        int length = sized.getInt();
        sized.flip();
        ByteBuffer msg = ByteBuffer.allocate(length);
        if (channel.read(msg) != length) {
            return null;
        }
        return msg;
    }

    public static List<byte[]> readLengthMessages(InputStream is) throws IOException {
        List<byte[]> messages = new ArrayList<>();
        byte[] current = readLengthMessage(is);
        if (current == null) {
            return null;
        }
        if (current == EMPTY_B) {
            return Collections.emptyList();
        }
        messages.add(current);
        while ((current = readLengthMessage(is)) != null) {
            if (current == EMPTY_B) {
                break;
            }
            messages.add(current);
        }
        return messages;

    }

    public static byte[] readLengthMessage(InputStream is) throws IOException {
        byte[] sized = new byte[4];
        int read;
        if ((read = is.read(sized)) < 0) {
            return null;
        }
        if (read == 0) {
            return EMPTY_B;
        }
        int length = ByteBuffer.wrap(sized).getInt();
        byte[] msg = new byte[length];
        if (is.read(msg) != length) {
            return null;
        }
        return msg;
    }

    public static void writeMessage(byte[] message, ByteChannel channel) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(message.length + 4);
        buffer.putInt(message.length);
        buffer.put(message);
        buffer.flip();
        channel.write(buffer);
    }

    public static void writeMessage(byte[] message, OutputStream stream) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(message.length + 4);
        buffer.putInt(message.length);
        buffer.put(message);
        buffer.flip();
        stream.write(buffer.array());
        stream.flush();
    }
}
