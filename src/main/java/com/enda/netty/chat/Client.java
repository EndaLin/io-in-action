package com.enda.netty.chat;

import io.netty.bootstrap.Bootstrap;

import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.InputStreamReader;


/**
 * @author wt
 * @create 2019-03-21 上午9:50
 */
public class Client {
    public static void main(String[] args) {
        EventLoopGroup eventExecutors = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventExecutors).channel(NioSocketChannel.class)
                    .handler(new ClientInitializer());

            Channel channel = bootstrap.connect("localhost", 8899).sync().channel();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
                channel.writeAndFlush(bufferedReader.readLine() + "\r\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            eventExecutors.shutdownGracefully();
        }
    }
}
