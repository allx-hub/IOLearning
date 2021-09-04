package ink.allx.four;

import java.util.concurrent.*;

/**
 * @Author Allx
 * @Date 2021/9/4 23:26
 */
public class HandlerSocketServerPool {
    //创建一个线程池的成员变量用于存储一个线程池对象
    private ExecutorService executorService;

    //初始化线程池
    public HandlerSocketServerPool(int maxThreadNum, int queueSize) {
        executorService = new ThreadPoolExecutor(3, maxThreadNum, 120,
                TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(queueSize));
    }

    //提供方法来提交任务给线程池的任务队列来暂存，等着线程池来处理
    public void execute(Runnable target){
        executorService.execute(target);
    }
}
