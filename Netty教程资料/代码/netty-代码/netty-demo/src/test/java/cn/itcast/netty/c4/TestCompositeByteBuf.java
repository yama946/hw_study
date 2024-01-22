package cn.itcast.netty.c4;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;

import static cn.itcast.netty.c4.TestByteBuf.log;
/**
 * 本节api讲解
 *  将多个小buf组合成一个大的buf，与切片相反的操作，同样也不会发送数据的赋值操作
 */
public class TestCompositeByteBuf {
    public static void main(String[] args) {
        ByteBuf buf1 = ByteBufAllocator.DEFAULT.buffer();
        buf1.writeBytes(new byte[]{1, 2, 3, 4, 5});

        ByteBuf buf2 = ByteBufAllocator.DEFAULT.buffer();
        buf2.writeBytes(new byte[]{6, 7, 8, 9, 10});

        //方式1：链式调用，会产生复制
        /*ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
        buffer.writeBytes(buf1).writeBytes(buf2);
        log(buffer);*/

        //方式2：将多个buf逻辑上组合成一个，不会产生复制操作
        CompositeByteBuf buffer = ByteBufAllocator.DEFAULT.compositeBuffer();
        buffer.addComponents(true, buf1, buf2);//需要添加true，否则不会自动更改指针
        log(buffer);
    }
}
