/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package org.kairev0.Server;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.kairev0.Models.Player;
import org.kairev0.Services.DataService;

import javax.xml.crypto.Data;
import java.net.InetAddress;
import java.util.*;

/**
 * Handles a server-side channel.
 */
@Sharable
public class MyServerHandler extends SimpleChannelInboundHandler<String> {
    private Map<String, Player> players;
    private final List<Player> onlinePlayers;

    public MyServerHandler() {
        this.players = new HashMap<>();
        this.onlinePlayers = new ArrayList<>();
        this.onlinePlayers.add(new Player("TestPlayer", "Password", 0));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // Send greeting for a new connection.
        ctx.write("Welcome to Game!\r\n");
        ctx.write("Please enter login and password [login&password] (or \"exit\" for exit from game): \n");
        ctx.flush();
        // Инициализация базы данных
        DataService.initialization();
        // Получение всех пользователей
        this.players = DataService.getAllPlayers();
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, String request) throws Exception {
        // Generate and write a response.
        String response = "";
        boolean close = false;
        if (request.isEmpty()) {
            response = "Please type something.\r\n";
        } else if ("exit".equals(request.toLowerCase())) {
            response = "Have a good day!\r\n";
            close = true;
        } else if (request.contains("&")) {
            String[] input = request.split("&");
            String login = input[0];
            String password = input[1];
            Player player = DataService.authorization(login, password);

            if (player == null) {
                response = "Invalid login or password!\r\n";
            } else {
                response = "Success!\r\n";
            }
            this.players = DataService.getAllPlayers();
        }

        // We do not need to write a ChannelBuffer here.
        // We know the encoder inserted at TelnetPipelineFactory will do the conversion.
        ChannelFuture future = ctx.write(response);

        // Close the connection after sending 'Have a good day!'
        // if the client has sent 'bye'.
        if (close) {
            future.addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}