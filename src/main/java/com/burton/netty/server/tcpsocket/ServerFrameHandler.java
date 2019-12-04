package com.burton.netty.server.tcpsocket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.time.LocalDateTime;


/**
 * 处理文本协议数据
 *
 * @author Tainy
 * @date   2019-12-04 18:47
 */
public class ServerFrameHandler extends SimpleChannelInboundHandler<String> {

    public static String DELIMITER = "_#_";
    
    //读到客户端的内容并且向客户端去写内容
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
    
        Channel channel = ctx.channel();
    
        System.out.println("收到客户端消息: " + msg);

        channel.writeAndFlush(new TextWebSocketFrame("服务时间："+ LocalDateTime.now()));
    }
    
    //每个channel都有一个唯一的id值
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        //打印出channel唯一值，asLongText方法是channel的id的全名
        System.out.println("handlerAdded："+ctx.channel());
    }
    
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerRemoved：" + ctx.channel());
    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("异常发生");
        ctx.close();
    }
    
}
