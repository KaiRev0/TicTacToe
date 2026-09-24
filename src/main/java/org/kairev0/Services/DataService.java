package org.kairev0.Services;

import org.kairev0.Models.Player;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class DataService {
    // 0.1. Ввод логина
    // 0.2. Ввод пароля
    // 1. Получить базу данных пользователей
    // 2. Найти из полученных данных пользователя по логину
    // 3. Сравнить, совпадает ли введённый пароль
    // 4. Впустить пользователя в его личный кабинет

    public static void initialization() {
        if (!Files.exists(Paths.get("accounts.txt"))) {
            try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(Paths.get("accounts.txt")))) {
                Map<String, Player> players = new HashMap<>();
                oos.writeObject(players);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public static Player authorization(String login, String password) throws FileNotFoundException {
        Map<String, Player> players = getAllPlayers();
        Player player = players.get(login);
        if (player != null && password.equals(player.getPassword())) return player;
        if (player == null) {
            player = new Player(login, password, 0);
            players.put(login, player);
            save(player);
            return player;
        }
        return null;
    }

    public static Map<String, Player> getAllPlayers() throws FileNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(Paths.get("accounts.txt")))) {
            Map<String, Player> players = (Map<String, Player>) ois.readObject();
            return players;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void save(Player player) throws FileNotFoundException {
        Map<String, Player> players = getAllPlayers();
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(Paths.get("accounts.txt")))) {
            players.put(player.getName(), player);
            oos.writeObject(players);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}