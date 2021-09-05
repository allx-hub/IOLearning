package ink.allx;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * 目标：BIO模式的端口转发思想-服务器端实现
 * 服务器端实现的需求：
 * 1.注册端口
 * 2.接收客户端的socket连接，交给一个独立的线程处理
 * 3.把当前连接的客户端socket存入到一个所谓的在线socket集合进行集中保存
 * 4.接收到客户端的消息后，然后推送给当前所有的在线socket接收
 *
 * @Author Allx
 * @Date 2021/9/5 10:43
 */
@SuppressWarnings("all")
public class Server {
    public static List<Socket> allSocketOnline = new ArrayList<>();

    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(9999);
            while (true) {
                Socket socket = ss.accept();
                //把接收到的客户端socket存入到一个在线集合中去
                allSocketOnline.add(socket);
                //为当前登录成功的socket分配一个独立的线程类处理与之通信
                new Thread(new ServerReaderThread(socket)).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }
}
