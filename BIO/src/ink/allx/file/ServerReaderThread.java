package ink.allx.file;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.UUID;

/**
 * @Author Allx
 * @Date 2021/9/5 10:12
 */
@SuppressWarnings("all")
public class ServerReaderThread extends Thread {
    private Socket socket;
    private static final String DESTINATION = "D:\\GitHub\\IOLearning\\BIO\\src\\ink\\allx\\file";

    public ServerReaderThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        DataInputStream dis = null;
        try {
            //得到一个数据输入流读取客户端发送过来的数据
            dis = new DataInputStream(socket.getInputStream());
            //读取文件类型
            String suffix = dis.readUTF();
            System.out.println("服务器端接收到的文件类型为：" + suffix);
            //定义一个字节输出管道负责把客户端发来的文件数据写出去
            OutputStream os = new FileOutputStream(DESTINATION + UUID.randomUUID() + suffix);
            //从数据输入流中读取文件数据，写出到字节输出流中去
            byte[] buffer = new byte[1024];
            int len;
            while ((len = dis.read(buffer)) > 0) {
                os.write(buffer, 0, len);
            }
            System.out.println("服务器端文件保存成功");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (dis != null) {
                try {
                    dis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}