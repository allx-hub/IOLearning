package ink.allx.file;

import java.io.*;
import java.net.Socket;

/**
 * 客户端上传任意类型的文件数据给服务器端保存起来
 * 重点是任意类型的文件
 *
 * @Author Allx
 * @Date 2021/9/5 9:58
 */
public class Client {
    public static void main(String[] args) {
        //String SOURCE = "D:\\Daily Life\\Wallpaper\\wallhaven-j3xoj5_2560x1600.png";
        String SOURCE = "D:\\Daily Life\\Download Music\\漂洋过海来看你-刘明湘.mp3";
        DataOutputStream dos = null;
        try {
            // （1）创建一个Socket的通信管道，请求与服务端的端口连接。
            Socket socket = new Socket("127.0.0.1", 8888);

            // （2）从Socket通信管道中得到一个字节输出流。
            OutputStream os = socket.getOutputStream();

            // （3）把字节流改装成自己需要的流进行数据的发送
            dos = new DataOutputStream(os);

            // （4）先发送上传文件的后缀给服务器端
            dos.writeUTF(".mp3");

            // （5）把文件数据发送给服务器端进行接受
            InputStream is = new FileInputStream(SOURCE);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = is.read(buffer)) > 0) {
                dos.write(buffer, 0, len);
            }
            dos.flush();

            socket.shutdownInput();//通知服务器端这边的数据已经发送完毕，可以关闭了
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (dos != null) {
                try {
                    dos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
