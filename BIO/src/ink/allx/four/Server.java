package ink.allx.four;

import ink.allx.three.ServerThreadReader;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 伪异步通信架构
 *
 * @Author Allx
 * @Date 2021/9/4 23:23
 */
@SuppressWarnings("all")
public class Server {
    public static void main(String[] args) {
        System.out.println("==服务器的启动==");
        // （1）注册端口
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(8888);

            //初始化线程池对象
            HandlerSocketServerPool serverPool = new HandlerSocketServerPool(6,10);
            //（2）开始在这里暂停等待接收客户端的连接，得到一个端到端的Socket管道，因而在这里死循环，不断监听请求
            while (true) {
                Socket socket = serverSocket.accept();
                //把socket对象交给一个线程池进行处理
                //把socket封装成一个任务对象交给线程池来执行
                ServerRunnableTarget target = new ServerRunnableTarget(socket);
                serverPool.execute(target);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
