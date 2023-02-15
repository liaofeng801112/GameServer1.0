package org.come.serverInitializer;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import org.come.handler.MainServerHandler;

public class MainServerInitializer extends ChannelInitializer<SocketChannel> {
	
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("logging", new LoggingHandler(LogLevel.INFO));
        // 以("\n")为结尾分割的 解码器
        pipeline.addLast("framer", new DelimiterBasedFrameDecoder(Integer.MAX_VALUE, Delimiters.lineDelimiter()));
        pipeline.addLast(new IdleStateHandler(180, 0, 0, TimeUnit.SECONDS));
        // 字符串解码 和 编码
        pipeline.addLast("decoder", new StringDecoder(Charset.forName("utf-8")));
        pipeline.addLast("encoder", new StringEncoder(Charset.forName("utf-8")));
        // 自己的逻辑Handler
        pipeline.addLast("handler", new MainServerHandler());
        
    }
}