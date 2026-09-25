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
import org.kairev0.Services.GameServiceOnline;
import org.kairev0.Utils.Utils;

import java.util.*;

/**
 * Handles a server-side channel.
 */
@Sharable
public class MyServerHandler extends SimpleChannelInboundHandler<String> {
    private List<String> players;
    private final List<Player> onlinePlayers;
    private final ClientManager clientManager;

    public MyServerHandler(ClientManager clientManager) {
        this.players = new ArrayList<>();
        this.onlinePlayers = new ArrayList<>();
        this.onlinePlayers.add(new Player("TestPlayer", "Password", 0));
        this.clientManager = clientManager;
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
        //this.players = DataService.getAllPlayers();

        String id = UUID.randomUUID().toString();
        players.add(id);
        clientManager.register(id, ctx.channel());
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, String request) throws Exception {
        // Generate and write a response.
        String response = "";
        boolean close = false;

        clientManager.connectClients(players.get(0), players.get(1));

        Player player = ctx.channel().attr(MyServer.PLAYER).get();
        int[][] field = ctx.channel().attr(MyServer.FIELD).get();
        System.out.println(Arrays.deepToString(field));
        if (player == null) {
            player = new Player("TestPlayer", "Password", 0);
            player.setTeam(1);
            ctx.channel().attr(MyServer.PLAYER).set(player);
        }

        if (field == null) {
            field = new int[3][3];
            ctx.channel().attr(MyServer.FIELD).set(field);
        }

        String[] coords = request.split(" ");
        int x = Integer.parseInt(coords[0]);
        int y = Integer.parseInt(coords[1]);
        if (x < 0 || x > 2 || y < 0 || y > 2 || field[x][y] == 1 || field[x][y] == 2) {
            response += "Invalid coordinates. Try again.\n";
        } else {
            field[x][y] = player.getTeam();
        }
        response += Arrays.deepToString(field) + "\n";
        response += GameServiceOnline.isWin(field, player.getTeam()) + "\n";
        response += "\r\n";

        /*Player player = ctx.channel().attr(MyServer.PLAYER).get();
        if (request.isEmpty()) {
            response = "Please type something.\r\n";
        } else if ("exit".equalsIgnoreCase(request)) {
            response = "Have a good day!\r\n";
            close = true;
        } else if (player == null && request.contains("&")) {
            String[] accountData = request.split("&");
            String login = accountData[0];
            String password = accountData[1];
            player = DataService.authorization(login, password);
            if (player == null) {
                response = "Invalid login or password!\r\n";
            } else {
                ctx.channel().attr(MyServer.PLAYER).set(player);
                response = "Success!\n";
                response += "\nProfile\n" +
                        "Login: " + player.getName() + "\n" +
                        "Score: " + player.getScore() + "\n" +
                        "Count of wins: " + player.getCountOfWins() + "\n" +
                        "Count of losses: " + player.getCountOfFails() + "\n" +
                        "KD: " + (player.getCountOfWins() * 1.0 / (player.getCountOfFails() + player.getCountOfWins())) + "\n" +
                        "Choice option:" + "\n" +
                        "\"me\"\t" + "\n" +
                        "\"start\"\t" + "\n" +
                        "\"exit\"\t\n\r\n";
                ctx.channel().attr(MyServer.PLAYER).set(player);
            }
            this.players = DataService.getAllPlayers();
        } else if (player != null && "me".equalsIgnoreCase(request)) {
            response = "\nProfile\n" +
                    "Login: " + player.getName() + "\n" +
                    "Score: " + player.getScore() + "\n" +
                    "Count of wins: " + player.getCountOfWins() + "\n" +
                    "Count of losses: " + player.getCountOfFails() + "\n" +
                    "KD: " + (player.getCountOfWins() * 1.0 / (player.getCountOfFails() + player.getCountOfWins())) + "\n" +
                    "Choice option:" + "\n" +
                    "\"start\"\t" + "\n" +
                    "\"exit\"\t\n\r\n";
        } else if (player != null && "start".equalsIgnoreCase(request)) {
            response = "Start game.\n" +
                    "Waiting for player to start game.\n";
            GameService service = openGameSession(player);
            if (service == null) {
                response = "Tries are exhausted. Nobody online.";
            } else {
                response += "game\n";
                ctx.channel().attr(MyServer.GAME).set(service);
            }
            response += "\r\n";
        } else {
            response = "Unknown command\r\n";
        }

        if (player != null && ctx.channel().attr(MyServer.GAME).get() != null) {
            System.out.println("Activate game");
            GameService service = ctx.channel().attr(MyServer.GAME).get();
            service.startGame(ctx, request);
        }*/

        // We do not need to write a ChannelBuffer here.
        // We know the encoder inserted at TelnetPipelineFactory will do the conversion.
        ChannelFuture future = ctx.write(response);

        // Close the connection after sending 'Have a good day!'
        // if the client has sent 'bye'.
        if (close) {
            future.addListener(ChannelFutureListener.CLOSE);
        }
    }

    private void closeGameSession(Player player) {
        onlinePlayers.remove(player);
    }

    private GameServiceOnline openGameSession(Player player) {
        onlinePlayers.add(player);
        Player opponent = opponentRandomizer(onlinePlayers, player);
        if (opponent == null) {
            return null;
        }
        return new GameServiceOnline(player, opponent);
    }

    private Player opponentRandomizer(List<Player> onlinePlayers, Player player) {
        Player opponent = null;
        if (onlinePlayers != null && !onlinePlayers.isEmpty()) {
            int tries = 0;
            while ((opponent == null || opponent.equals(player)) && tries++ < 10) {
                opponent = onlinePlayers.get(Utils.random.nextInt(onlinePlayers.size()));
            }
            if (Objects.equals(opponent, player)) {
                return null;
            }
        } else {
            return null;
        }
        return opponent;
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