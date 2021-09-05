package ink.allx.one;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @PackageName: com.netty.bio.demo1
 * @author: youjp
 * @create: 2020-12-18 10:59
 * @description: 服务端案例
 * @Version: 1.0
 */
public class ServerDemo {

    public static void main(String[] args) throws IOException {
        System.out.println("==服务器的启动==");
        // （1）注册端口，就是监听8888端口
        ServerSocket serverSocket = new ServerSocket(8888);

        //（2）开始在这里暂停等待接收客户端的连接,得到一个端到端的Socket管道
        Socket socket = serverSocket.accept();

        //（3）从Socket管道中得到一个字节输入流。
        InputStream is = socket.getInputStream();

        //（4）把字节输入流包装成自己需要的流进行数据的读取。
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        //（5）读取数据
        String line;
        if ((line = br.readLine()) != null) {
            System.out.println("服务端收到----->" + line);
        }
    }
}