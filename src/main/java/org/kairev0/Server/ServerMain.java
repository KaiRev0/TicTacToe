package org.kairev0.Server;

import org.kairev0.Models.Player;
import org.kairev0.Services.DataService;
import org.kairev0.Services.GameService;

import java.io.FileNotFoundException;
import java.util.*;

public class ServerMain {
    private Map<String, Player> players = new HashMap<>();

    public void run() throws FileNotFoundException {
        // Инициализация базы данных
        DataService.initialization();
        // Получение всех пользователей
        this.players = DataService.getAllPlayers();
    }

    public void createGameSession(Player player) {
        Player opponent = opponentRandomizer(players, player);
        GameService gameService = new GameService(player, opponent);
    }

    private Player opponentRandomizer(Map<String, Player> accounts, Player player) {
        Player opponent = null;
        if (accounts != null && !accounts.isEmpty()) {
            List<Player> players = new ArrayList<>(accounts.values());
            Collections.shuffle(players);
            opponent = players.get(0);
            if (!accounts.isEmpty()) {
                int tries = 0;
                while (opponent.equals(player) && tries++ < 10) {
                    Collections.shuffle(players);
                    opponent = players.get(0);
                }
                if (opponent.equals(player)) {
                    System.out.println("Tries are exhausted");
                    System.exit(0);
                }
            }
        } else {
            System.out.println("Account list is empty");
            System.exit(0);
        }
        return opponent;
    }
}
