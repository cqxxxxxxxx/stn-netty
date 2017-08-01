package chapter2.block;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 同步阻塞式IO
 * 一个连接对应一个线程
 * 缺点：无法满足高性能，高并发的接入场景
 * Created by cqxxxxxxxx on 17-7-31.
 */
public class TimeServer {


    public static void main(String[] args) throws IOException {
        int port = 8080;

        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                //采用默认值
            }
        }

        ServerSocket server = null;

        try {
            server = new ServerSocket(port);
            System.out.println("The time server is start in port: " + port);
            Socket socket = null;
            while (true) {
                socket = server.accept();	//主线程在这阻塞　如果没有客户端介入
                new Thread(new TimeServerHandler(socket)).start();	//客户端请求接入,则创建一个新的线程处理连接
            }
        } finally {
            if (server != null) {
                System.out.println("The time server close");
                server.close();
                server = null;
            }
        }
    }

}
