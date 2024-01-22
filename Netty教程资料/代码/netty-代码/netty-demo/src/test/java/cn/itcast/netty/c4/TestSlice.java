package cn.itcast.netty.c4;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import static cn.itcast.netty.c4.TestByteBuf.log;
/**
 * 注意：
 *      1、切片后的buf，会限制最大长度，不能向buf中写入数据
 *      2、如果对原始buf执行release操作，切片也会失效。
 */
public class TestSlice {
    public static void main(String[] args) {
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer(10);
        buf.writeBytes(new byte[]{'a','b','c','d','e','f','g','h','i','j'});
        log(buf);

        // 在切片过程中，没有发生数据复制
        ByteBuf f1 = buf.slice(0, 5);//切片1
        f1.retain();
        // 'a','b','c','d','e', 'x'
        ByteBuf f2 = buf.slice(5, 5);//切片2
        f2.retain();
        log(f1);
        log(f2);

        System.out.println("释放原有 byteBuf 内存");
        buf.release();
        log(f1);


        f1.release();
        f2.release();
        /*System.out.println("========================");
        f1.setByte(0, 'b');
        log(f1);
        log(buf);*/
    }
}
