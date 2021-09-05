package ink.allx.file;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * 接受任意类型文件，并保存在磁盘
 *
 * @Author Allx
 * @Date 2021/9/5 9:58
 */
@SuppressWarnings("all")
public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket ss=new ServerSocket(8888);

            while (true){
                Socket socket = ss.accept();
                //交给一个独立的线程来处理与客户端的文件通信需求
                new ServerReaderThread(socket).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }
}
