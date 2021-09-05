package ink.allx.server;

import ink.allx.util.Constants;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
@SuppressWarnings("all")
public class ServerChat {
    //定义一个集合存放所有在线的socket
    //在线集合只需要一个：存储客户端socket的同时还需要直到这个socket客户端的名称
    public static Map<Socket, String> onLineSockets = new HashMap<>();

    public static void main(String[] args) {
        try {
            //1.注册端口
            ServerSocket serverSocket = new ServerSocket(Constants.PORT);

            //2.循环一直等待所有可能的客户端连接
            while (true) {
                Socket socket = serverSocket.accept();
                //把客户端的socket管道单独配置一个线程来处理
                new ServerReader(socket).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}