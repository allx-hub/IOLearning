package ink.allx.nio;

import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author Allx
 * @Date 2021/9/7 15:55
 */
public class ChannelTest {
    //测试利用通道写数据到文件中
    @Test
    public void write() {
        try {
            // 1、字节输出流通向目标文件
            FileOutputStream fos = new FileOutputStream("data01.txt");
            // 2、得到字节输出流对应的通道Channel
            FileChannel channel = fos.getChannel();
            // 3、分配缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.put("hello,黑马Java程序员！".getBytes());
            // 4、把缓冲区切换成写出模式
            buffer.flip();
            channel.write(buffer);
            channel.close();
            System.out.println("写数据到文件中！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //读取文件数据
    @Test
    public void read() throws Exception {
        // 1、定义一个文件字节输入流与源文件接通
        FileInputStream is = new FileInputStream("data01.txt");
        // 2、需要得到文件字节输入流的文件通道
        FileChannel channel = is.getChannel();
        // 3、定义一个缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        // 4、读取数据到缓冲区
        channel.read(buffer);
        buffer.flip();
        // 5、读取出缓冲区中的数据并输出即可，剩多少就输出多少
        String rs = new String(buffer.array(), 0, buffer.remaining());
        System.out.println(rs);
    }

    @Test
    public void copy() throws Exception {
        // 源文件
        File srcFile = new File("D:\\GitHub\\IOLearning\\NIO\\old.png");
        File destFile = new File("D:\\GitHub\\IOLearning\\NIO\\new.png");
        // 得到一个字节字节输入流
        FileInputStream fis = new FileInputStream(srcFile);
        // 得到一个字节输出流
        FileOutputStream fos = new FileOutputStream(destFile);
        // 得到的是文件通道
        FileChannel isChannel = fis.getChannel();
        FileChannel osChannel = fos.getChannel();
        // 分配缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while (true) {
            // 必须先清空缓冲然后再写入数据到缓冲区
            buffer.clear();
            // 开始读取一次数据，从通道读取数据到缓冲区
            int flag = isChannel.read(buffer);
            if (flag == -1) {
                break;
            }
            // 已经读取了数据 ，把缓冲区的模式切换成可读模式
            buffer.flip();
            // 把缓冲区的数据写出到通道
            osChannel.write(buffer);
        }
        isChannel.close();
        osChannel.close();
        System.out.println("复制完成！");
    }

    //分散和聚集
    @Test
    public void test() throws IOException {
        RandomAccessFile raf1 = new RandomAccessFile("data01.txt", "rw");
        //1. 获取通道
        FileChannel channel1 = raf1.getChannel();

        //2. 分配指定大小的缓冲区
        ByteBuffer buf1 = ByteBuffer.allocate(4);
        ByteBuffer buf2 = ByteBuffer.allocate(1024);

        //3. 从通道中分散读取到不同的缓冲区
        ByteBuffer[] bufs = {buf1, buf2};
        channel1.read(bufs);

        for (ByteBuffer byteBuffer : bufs) {
            byteBuffer.flip();
        }

        System.out.println(new String(bufs[0].array(), 0, bufs[0].limit()));
        System.out.println("-----------------");
        System.out.println(new String(bufs[1].array(), 0, bufs[1].limit()));

        //4. 聚集写入
        RandomAccessFile raf2 = new RandomAccessFile("data02.txt", "rw");
        FileChannel channel2 = raf2.getChannel();

        channel2.write(bufs);
    }

    @Test
    public void test02() throws Exception {
        // 1、字节输入管道
        FileInputStream is = new FileInputStream("data01.txt");
        FileChannel isChannel = is.getChannel();
        // 2、字节输出流管道
        FileOutputStream fos = new FileOutputStream("data03.txt");
        FileChannel osChannel = fos.getChannel();
        // 3、复制
        osChannel.transferFrom(isChannel,isChannel.position(),isChannel.size());
        isChannel.close();
        osChannel.close();
    }

    @Test
    public void test03() throws Exception {
        // 1、字节输入管道
        FileInputStream is = new FileInputStream("data01.txt");
        FileChannel isChannel = is.getChannel();
        // 2、字节输出流管道
        FileOutputStream fos = new FileOutputStream("data04.txt");
        FileChannel osChannel = fos.getChannel();
        // 3、复制
        //主要区别在下面这行代码
        isChannel.transferTo(isChannel.position() , isChannel.size() , osChannel);
        isChannel.close();
        osChannel.close();
    }
}