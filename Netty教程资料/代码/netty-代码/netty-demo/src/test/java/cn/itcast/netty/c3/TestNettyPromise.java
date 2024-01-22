package cn.itcast.netty.c3;

import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultPromise;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
/**
 * 前面两个的future的对象，都不是我们自己创建的，都是提交任务后返回的，有没有更灵活的方式获取结果呢？promise
 */
@Slf4j
public class TestNettyPromise {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 1. 准备 EventLoop 对象
        EventLoop eventLoop = new NioEventLoopGroup().next();
        // 2. 可以主动创建 promise, 结果容器,任何线程都可以向其中设置结果
        DefaultPromise<Integer> promise = new DefaultPromise<>(eventLoop);
        new Thread(() -> {
            // 3. 任意一个线程执行计算，计算完毕后向 promise 填充结果
            log.debug("开始计算...");
            try {
                int i = 1 / 0;
                Thread.sleep(1000);
                promise.setSuccess(80);
            } catch (Exception e) {
                e.printStackTrace();
                promise.setFailure(e);
            }

        }).start();
        // 4. 接收结果的线程
        log.debug("等待结果...");
        log.debug("结果是: {}", promise.get());
    }

}
