package ink.allx;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * @Author Allx
 * @Date 2021/9/5 10:43
 */
public class Client {
    public static void main(String[] args) throws IOException {
        System.out.println("==客户端的启动==");

        // （1）创建一个Socket的通信管道，请求与服务端的端口连接。
        Socket socket = new Socket("127.0.0.1", 9999);

        // （2）从Socket通信管道中得到一个字节输出流。
        OutputStream os = socket.getOutputStream();

        // （3）把字节流改装成自己需要的流进行数据的发送
        PrintStream ps = new PrintStream(os);

        new Thread(new ClientReaderThread(socket)).start();

        // （4）开始发送消息
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("请输入要发送的消息：---->");
            String msg = sc.nextLine();
            ps.println(msg);
            ps.flush();
        }



        //TODO 同时还要接受来自其他客户端的消息，这个是通过服务器端进行转发的
    }
}
