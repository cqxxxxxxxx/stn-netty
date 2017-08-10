package protocol.pojo;

import java.util.HashMap;
import java.util.Map;

/**
 * final 方法 不可重写，
 * 1.当你明确方法不需要进行扩展的时候可以用。
 * 2.
 * Created by cqx on 2017/8/9.
 */
public class Header {

    private int crcCode = 0xabef0101;
    private int length;     //消息长度
    private long sessionId; //会话ID
    private byte type;  //消息类型
    private byte priority;  //消息优先级
    private Map<String, Object> attachment = new HashMap<>();   //附件

    @Override
    public String toString() {
        return "Header{" +
                "crcCode=" + crcCode +
                ", length=" + length +
                ", sessionId=" + sessionId +
                ", type=" + type +
                ", priority=" + priority +
                ", attachment=" + attachment +
                '}';
    }

    public final int getCrcCode() {
        return crcCode;
    }

    public final void setCrcCode(int crcCode) {
        this.crcCode = crcCode;
    }

    public final int getLength() {
        return length;
    }

    public final void setLength(int length) {
        this.length = length;
    }

    public final long getSessionId() {
        return sessionId;
    }

    public final void setSessionId(long sessionId) {
        this.sessionId = sessionId;
    }

    public final byte getType() {
        return type;
    }

    public final void setType(byte type) {
        this.type = type;
    }

    public final byte getPriority() {
        return priority;
    }

    public final void setPriority(byte priority) {
        this.priority = priority;
    }

    public final Map<String, Object> getAttachment() {
        return attachment;
    }

    public final void setAttachment(Map<String, Object> attachment) {
        this.attachment = attachment;
    }
}
