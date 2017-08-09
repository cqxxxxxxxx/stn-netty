package protocol.pojo;

/**
 * netty协议栈使用的数据结构
 * Created by cqx on 2017/8/9.
 */
public class NettyMessage {

    private Header header; //消息头
    private Object body; //消息体

    @Override
    public String toString() {
        return "NettyMessage{" +
                "header=" + header +
                ", body=" + body +
                '}';
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}
