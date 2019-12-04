package com.burton.netty.server.tcpsocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.net.InetSocketAddress;

/**
 * websocket长连接示例
 *
 * @author Tainy
 * @date   2019-12-04 18:47
 */
public class Server {
    public static void main(String[] args) throws Exception{
        
        // 主线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        // 从线程组
        EventLoopGroup wokerGroup = new NioEventLoopGroup();
        
        try{
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup,wokerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ServerChannelInitializer());
            
            ChannelFuture channelFuture = serverBootstrap.bind(new InetSocketAddress(8707)).sync();

            System.out.println("netty starting success");

            channelFuture.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            wokerGroup.shutdownGracefully();
        }
        
    }
}