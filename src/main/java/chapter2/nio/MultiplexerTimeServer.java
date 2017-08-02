package chapter2.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by cqx on 2017/8/1.
 */
public class MultiplexerTimeServer implements Runnable {

    private Selector selector;

    private ServerSocketChannel serverSocketChannel;

    private volatile boolean stop;

    public MultiplexerTimeServer(int port) {
        try {

            selector = Selector.open();     //开启多路复用器
            serverSocketChannel = ServerSocketChannel.open();   //所有客户端的连接通道，用于监听客户端连接
            serverSocketChannel.configureBlocking(false);   //不阻塞模式
            serverSocketChannel.socket().bind(new InetSocketAddress(port), 1024);   //设置监听的端口，最大连接数
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT); //注册到Selector上， 监听ACCEPT事件,
            System.out.println("The time server is start in port : " + port);

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1); //如果出现异常 退出系统
        }
    }

    public void stop() {
        this.stop = true;
    }

    @Override
    public void run() {

        while (!stop) {
            try {
// timeout参数作用 ->
// If positive, block for up to <tt>timeout</tt> milliseconds,
// more or less, while waiting for a channel to become ready; if zero, block indefinitely;
                selector.select(1000);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();  //获取准备就绪的Key
                Iterator<SelectionKey> it = selectionKeys.iterator();
                SelectionKey key = null;

                while (it.hasNext()) {
                    key = it.next();
                    it.remove();
                    try {
                        handleInput(key);
                    } catch (Exception e) {
                        if (key != null) {
                            key.cancel();   //从selector中移除应该
                            if (key.channel() != null) {
                                key.channel().close();  //关闭channel
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (selector != null) {
            try {
                selector.close();   //多路复用器关闭，注册在上面的channel 等资源都会自动关闭
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void handleInput(SelectionKey key) throws IOException {

        // A key is valid upon creation and remains so until it is cancelled, its channel is closed, or its selector is closed.
        if (key.isValid()) {
            //Tests whether this key's channel is ready to accept a new socket connection.
            if (key.isAcceptable()) {
                ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                SocketChannel sc = ssc.accept();
                sc.configureBlocking(false);    //非阻塞魔兽

                sc.register(selector, SelectionKey.OP_READ);    //监听读操作
            }

            if (key.isReadable()) {
                SocketChannel sc = (SocketChannel) key.channel();
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);  //初始分配数组大小 1024
                int readBytes = sc.read(readBuffer);    //channel中读取到ByteBuffer中 返回读取的字节数
                if (readBytes > 0) {
                    readBuffer.flip();  //这个干嘛用 忘了
                    byte[] bytes = new byte[readBuffer.remaining()];    //ByteBuffer里剩余的大小
                    readBuffer.get(bytes);
                    String body = new String(bytes, "UTF-8");
                    System.out.println("the time server receive order :" + body);
                    String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new Date().toString() : "BAD ORDER YOU FUCKING NOOB";

                    doWrite(sc, currentTime);   //通过channel 发送给客户端
                }

            }
        }
    }

    private void doWrite(SocketChannel socketChannel, String response) throws IOException {
        if (response != null && response.trim().length() > 0) {
            byte[] bytes = response.getBytes();
            ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
            writeBuffer.put(bytes);
            writeBuffer.flip();
            socketChannel.write(writeBuffer);
        }
    }
}
