package com.burton.netty.server.tcpsocket;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * ServerChannel
 *
 * @author Tainy
 * @date   2019-12-04 18:47
 */
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {
    
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

// 针对socket的解码器(WebSocket基于http，TcpSocket基于tcp, 底层都是基于tcp，以下为通用解码配置)
        ByteBuf delimiter = Unpooled.copiedBuffer(ServerFrameHandler.DELIMITER.getBytes());
        pipeline.addLast("delimiter", new DelimiterBasedFrameDecoder(4096, delimiter));
        pipeline.addLast("stringDecoder", new StringDecoder());
        pipeline.addLast("stringEncoder", new StringEncoder());

        pipeline.addLast(new ServerFrameHandler());
        
    }
}
