package ink.allx;

import java.io.*;
import java.net.Socket;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @Author Allx
 * @Date 2021/9/5 10:12
 */
@SuppressWarnings("all")
public class ServerReaderThread implements Runnable {
    private Socket socket;
    DateFormat df2 = DateFormat.getDateInstance(DateFormat.FULL, Locale.CHINA);
    DateFormat df6 = DateFormat.getTimeInstance(DateFormat.FULL, Locale.CHINA);

    public ServerReaderThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            //得到一个数据输入流读取客户端发送过来的数据
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String msg;
            while ((msg = br.readLine()) != null) {
                //服务器端接收到客户端的消息之后，是要推送给当前所有的在线socket进行接受
                sendMsgToAllClient(msg);
                //同时在服务器端也要展现
                System.out.println(df2.format(new Date()) + df6.format(new Date()) + "来自" + socket.getLocalAddress()
                        + ":" + socket.getPort() + "发送的消息是----->" + msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
            //当前有人下线了，更新在线socket集合
            System.out.println("有人下线了");
            Server.allSocketOnline.remove(socket);
        }
    }

    private void sendMsgToAllClient(String msg) throws IOException {
        for (Socket sk : Server.allSocketOnline) {
            if (sk != socket) {
                PrintStream ps = new PrintStream(sk.getOutputStream());
                ps.println(df2.format(new Date()) + df6.format(new Date()) + "来自" + socket.getLocalAddress() + ":" + socket.getInetAddress()
                        + "发送的消息是----->" + msg);
                ps.flush();
            }
        }
    }
}