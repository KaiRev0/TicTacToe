package org.kairev0.Server;

import org.kairev0.Models.Player;
import org.kairev0.Services.DataService;
import org.kairev0.Services.GameServiceOffline;
import org.kairev0.Utils.Utils;

import java.io.FileNotFoundException;
import java.util.*;

public class ServerMain {
    private Map<String, Player> players;
    private final List<Player> onlinePlayers;

    public ServerMain() {
        this.players = new HashMap<>();
        this.onlinePlayers = new ArrayList<>();
        this.onlinePlayers.add(new Player("TestPlayer", "Password", 0));
    }

    public void run() throws FileNotFoundException {
        // Инициализация базы данных
        DataService.initialization();
        // Получение всех пользователей
        this.players = DataService.getAllPlayers();
    }

    public void update() throws FileNotFoundException {
        this.players = DataService.getAllPlayers();
    }

    public GameServiceOffline openGameSession(Player player) {
        onlinePlayers.add(player);
        Player opponent = opponentRandomizer(onlinePlayers, player);
        return new GameServiceOffline(player, opponent);
    }

    public void closeGameSession(Player player) {
        onlinePlayers.remove(player);
    }

    private Player opponentRandomizer(List<Player> onlinePlayers, Player player) {
        Player opponent = null;
        if (onlinePlayers != null && !onlinePlayers.isEmpty()) {
            int tries = 0;
            while ((opponent == null || opponent.equals(player)) && tries++ < 10) {
                opponent = onlinePlayers.get(Utils.random.nextInt(onlinePlayers.size()));
            }
            if (Objects.equals(opponent, player)) {
                System.out.println("Tries are exhausted");
                System.exit(0);
           }
        } else {
            System.out.println("Nobody is online");
            System.exit(0);
        }
        return opponent;
    }
}
