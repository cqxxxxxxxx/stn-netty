package protocol.codec;

import io.netty.buffer.ByteBuf;
import org.jboss.marshalling.Marshaller;

import java.io.IOException;


/**
 * 消息编码工具类
 * Created by cqx on 2017/8/9.
 */
public class MarshallingEncoder {

    private static final byte[] LENGTH_PLACEHOLDER = new byte[4];
    Marshaller marshaller;  //jboss提供的编解码

    public MarshallingEncoder() throws IOException {
        marshaller = MarshallingCodecFactory.buildMarshalling();
    }

    protected void encode(Object msg, ByteBuf out) throws IOException {
        try {
            int lengthPos = out.writerIndex(); //Returns the {@code writerIndex} of this buffer.
            out.writeBytes(LENGTH_PLACEHOLDER); //
            ChannelBufferByteOutput output = new ChannelBufferByteOutput(out);
            marshaller.start(output);
            marshaller.writeObject(msg);
            marshaller.finish();
            out.setInt(lengthPos, out.writerIndex() - lengthPos - 4);
        } finally {
            marshaller.close();
        }
    }

}
