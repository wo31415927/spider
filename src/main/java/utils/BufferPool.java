/**
 *
 */
package utils;

import java.nio.ByteBuffer;
import java.util.concurrent.LinkedTransferQueue;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author zeyu
 */
@RequiredArgsConstructor
@Getter
public class BufferPool {
    private final int directBufferSize;
    public final LinkedTransferQueue<ByteBuffer> bufferQueue = new LinkedTransferQueue<>();

    public ByteBuffer getBuffer() {
        ByteBuffer buffer = null;
        if (null == (buffer = bufferQueue.poll())) {
            buffer = ByteBuffer.allocateDirect(directBufferSize);
        }
        return buffer;
    }

    public void returnBuffer(ByteBuffer buffer) {
        bufferQueue.put(buffer);
    }
}
