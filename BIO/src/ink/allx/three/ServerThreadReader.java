package ink.allx.three;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * @Author Allx
 * @Date 2021/9/4 23:04
 */
@SuppressWarnings("all")
public class ServerThreadReader extends Thread {
    private Socket socket;

    public ServerThreadReader(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        //（3）从Socket管道中得到一个字节输入流。
        InputStream is;
        try {
            is = socket.getInputStream();
            //（4）把字节输入流包装成自己需要的流进行数据的读取。
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            //（5）读取数据
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println("服务端收到的消息来自----->" +socket.getLocalAddress()+":"+socket.getPort()+ line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}