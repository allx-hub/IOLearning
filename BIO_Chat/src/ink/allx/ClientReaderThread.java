package ink.allx;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * @Author Allx
 * @Date 2021/9/5 11:24
 */
public class ClientReaderThread implements Runnable{
    private Socket socket;

    public ClientReaderThread(Socket socket) {
        this.socket = socket;
    }
    @Override
    public void run() {
        try {
            //得到一个数据输入流读取服务器端转发过来的数据
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String msg;
            while ((msg = br.readLine()) != null) {
                //服务器端接收到客户端的消息之后，是要推送给当前所有的在线socket进行接受
                System.out.println("其他人发送的消息是----->" + msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
