package org.kairev0.Server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.MultiThreadIoEventLoopGroup;
import io.netty.channel.nio.NioIoHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
// import io.netty.example.util.ServerUtil;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.util.AttributeKey;
import org.kairev0.Models.Player;
import org.kairev0.Services.GameServiceOnline;

/**
 * Simplistic telnet server.
 */
public final class MyServer {

    static final boolean SSL = System.getProperty("ssl") != null;
    static final int PORT = Integer.parseInt(System.getProperty("port", SSL? "8992" : "8023"));
    static final AttributeKey<Player> PLAYER = AttributeKey.valueOf("player");
    static final AttributeKey<int[][]> FIELD = AttributeKey.valueOf("field");
    static final AttributeKey<GameServiceOnline> GAME = AttributeKey.valueOf("game");
    static final AttributeKey<String> CLIENT_ID = AttributeKey.valueOf("clientId");

    public static void main(String[] args) throws Exception {
        // Configure SSL.
        final SslContext sslCtx = null; // ServerUtil.buildSslContext();

        EventLoopGroup group = new MultiThreadIoEventLoopGroup(NioIoHandler.newFactory());
        try {
            ClientManager clientManager = new ClientManager();
            ServerBootstrap b = new ServerBootstrap();
            b.group(group)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new MyServerInitializer(sslCtx))
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel channel) {
                            channel.pipeline().addLast(
                                    new MyServerHandler(clientManager)
                            );
                        }
                    });;

            b.bind(PORT).sync().channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }
}