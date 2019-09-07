package io.netty.example.lwl.heartbeat;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.concurrent.EventExecutorGroup;

/**
 * author liuweilong
 * date 2019/9/5 11:45
 * desc
 */
public class HeartBeatHandler extends ChannelInboundHandlerAdapter {
    /**
     * 用户事件触发的处理器
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            IdleState state = event.state();
            if (IdleState.READER_IDLE.equals(state)){
                System.out.println("进入读空闲状态，不做处理，连接未断开");
                return;
            }
            if (IdleState.WRITER_IDLE.equals(state)){
                System.out.println("进入写空闲状态，不做处理，连接未断开");
                return;
            }
            if (IdleState.ALL_IDLE.equals(state)){
                System.out.println("既没有读，也没有写，就把连接断开");
                ctx.pipeline().channel().close();
            }
        }
        super.userEventTriggered(ctx, evt);
    }
}
