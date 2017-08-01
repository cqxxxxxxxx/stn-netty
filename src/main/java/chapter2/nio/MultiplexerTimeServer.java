package chapter2.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
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
//参数作用 ->
// If positive, block for up to <tt>timeout</tt> milliseconds,
// more or less, while waiting for a channel to become ready; if zero, block indefinitely;
                selector.select(1000);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();  //获取准备就绪的Key
                Iterator<SelectionKey> it = selectionKeys.iterator();
                SelectionKey key = null;

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
