package ink.allx.three;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 目标：实现服务器端可以同时接受多个客户端的Socket通信请求
 * 思路：服务器端酶接收到一个客户端socket请求后都交给一个独立的线程来处理
 *
 * @Author Allx
 * @Date 2021/9/4 22:51
 */
@SuppressWarnings("all")
public class Server {
    public static void main(String[] args) {
        System.out.println("==服务器的启动==");
        // （1）注册端口
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(8888);
            //（2）开始在这里暂停等待接收客户端的连接，得到一个端到端的Socket管道，因而在这里死循环，不断监听请求
            while (true){
                Socket socket = serverSocket.accept();
                //创建线程进行处理
                new ServerThreadReader(socket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}