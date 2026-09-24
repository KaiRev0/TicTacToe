package org.kairev0.Client;

import org.kairev0.Models.Player;
import org.kairev0.Server.ServerMain;
import org.kairev0.Services.DataService;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class ClientMain {
    private static final Scanner scanner = new Scanner(System.in);
    private ServerMain serverMain;

    public ClientMain(ServerMain serverMain) {
        this.serverMain = serverMain;
    }

    public void run() throws FileNotFoundException {
        /* --- authorization --- */
        // Приветствуем пользователя
        System.out.println("Welcome to Game!");
        String login = "";
        Player player = null;
        boolean isExit = false;
        while (player == null) {
            // 0.1. Ввод логина
            System.out.print("Please enter login (or \"exit\" for exit from game): ");
            login = scanner.nextLine();
            if (login.equals("exit")) {
                isExit = true;
                break;
            }
            // 0.2. Ввод пароля
            System.out.print("Please enter password: ");
            String password = scanner.nextLine();
            // Авторизация
            player = DataService.authorization(login, password);
            if (player == null) {
                System.out.println("Invalid login or password!");
            }
        }
        /* --- authorization --- */

        /* --- profile --- */
        while (!isExit) {
            System.out.println();
            System.out.println("Profile");
            System.out.println("Login: " + player.getName());
            System.out.println("Score: " + player.getScore());
            System.out.println("Count of wins: " + player.getCountOfWins());
            System.out.println("Count of losses: " + player.getCountOfFails());
            System.out.println("KD: " + (player.getCountOfWins() * 1.0 / (player.getCountOfFails() + player.getCountOfWins())));
            System.out.println("Choice option:");
            System.out.print("(1) start game\t");
            System.out.print("(2) exit\t");
            System.out.println();
            String option = scanner.nextLine();
            if (option.equals("1")) {
                System.out.println("Start game");
                serverMain.createGameSession(player);
            } else if (option.equals("2")) {
                isExit = true;
            } else {
                System.out.println("Invalid input!");
            }
        }
        /* --- profile --- */

        scanner.close();
    }
}
