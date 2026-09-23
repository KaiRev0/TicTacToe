package org.kairev0;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/*
ERRORS:
1. Нарушен игровой цикл (нет трёх раундов, вероятно, проблема в isWin)
2. Неправильно определяется игрок и его оппонент (currentPlayer и currentOpponent)
3. Нарушена авторизация, нужно поправить сравнение по паролю
4. Нужно проверить систему начисления очков и игровые циклы
 */

/*
1. Сыграть одну успешную одноразовую партию
2. Разделить аккаунт и игровой цикл
3. Сделать программу постоянной, а не одноразовой
4. Настроить обмен между сокетами
 */

public class App {
    static Scanner scanner = new Scanner(System.in);
    static Random rand = new Random();

    public static void main( String[] args ) throws IOException, ClassNotFoundException {
        /* --- Tests --- */
        isWinTest();
        /* --- Tests --- */

        /* --- authorization window --- */
        System.out.println("Welcome to Game!");
        System.out.print("Please enter login: ");
        String login = scanner.nextLine();
        System.out.print("Please enter password: ");
        String password = scanner.nextLine();
        Map<String, Player> accounts = authorization(login, password);
        Player player = accounts.get(login);
        /* --- authorization window --- */

        /* --- opponent selection --- */
        Player opponent = opponentRandomizer(accounts, player);
        /* --- opponent selection --- */

        /* --- Game creator --- */
        Game game = new Game(new Id(Math.abs(rand.nextInt())), player, opponent);
        String input;
        int[][] field = null;
        int gameCycle = 0;
        Player currentPlayer = player;
        /* --- Game creator --- */

        /* --- game cycle --- */
        System.out.println("Current player is " + currentPlayer.getName());
        System.out.println("Your opponent is " + opponent.getName());
        int playerRoundWins = 0, opponentRoundWins = 0;
        while (playerRoundWins < 2 && opponentRoundWins < 2) {
            if (isWin(field)) {
                game.reloadField();
                System.out.println("Winner of round is " + currentPlayer.getName());
                if (currentPlayer.equals(player)) {
                    playerRoundWins++;
                    currentPlayer = opponent;
                    continue;
                } else {
                    opponentRoundWins++;
                    currentPlayer = player;
                    continue;
                }
            }
            if (gameCycle%2 == 0) {
                currentPlayer = player;
            } else {
                currentPlayer = opponent;
            }
            System.out.println("Field:");
            field = game.getField();
            printField(field);
            System.out.print("Please, enter coordinates: ");
            input = scanner.nextLine();
            String[] coords = input.split(" ");
            int x = Integer.parseInt(coords[0]);
            int y = Integer.parseInt(coords[1]);
            System.out.println("Current player is " + currentPlayer.getName());
            System.out.println("Your opponent is " + opponent.getName());
            if (currentPlayer.getName().equals(player.getName())) {
                if (x >= 0 && x <= 2 && y >= 0 && y <= 2 && !(field[x][y]==1 || field[x][y]==2)) {
                    field[x][y] = 1;
                } else {
                    System.out.println("Invalid coordinates. Try again.");
                    continue;
                }
            } else {
                currentPlayer = opponent;
                if (x >= 0 && x <= 2 && y >= 0 && y <= 2 && !(field[x][y] == 1 || field[x][y] == 2)) {
                    field[x][y] = 2;
                } else {
                    System.out.println("Invalid coordinates. Try again.");
                    continue;
                }
            }
            gameCycle++;
        }
        /* --- game cycle --- */

        /* --- result --- */
        printField(field);
        if (currentPlayer.equals(player)) {
            player.win();
            opponent.fail();
        } else {
            player.fail();
            opponent.win();
        }
        System.out.println(player.getName() + ": " + player.getScore());
        System.out.println(opponent.getName() + ": " + opponent.getScore());
        if (playerRoundWins >= 2) {
            System.out.printf("%s wins!\n", player.getName());
        } else {
            System.out.printf("%s wins!\n", opponent.getName());
        }
        System.out.println(playerRoundWins + "/" + opponentRoundWins);
        scanner.close();
        /* --- result --- */
    }

    static Player opponentRandomizer(Map<String, Player> accounts, Player player) {
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

    static Map<String, Player> authorization(String login, String password) throws IOException, ClassNotFoundException {
        Player player;
        Map<String, Player> accounts = new HashMap<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("accounts.txt"))) {
            accounts = (Map<String, Player>) ois.readObject();
            if (accounts.containsKey(login) && accounts.get(login).getPassword().equals(password)) {
                player = accounts.get(login);
                System.out.println("Auth");
                System.out.println("Account:");
                System.out.println("Login: " + player.getName());
                System.out.println("Score: " + player.getScore());
            } else {
                System.out.println("Registration");
                player = new Player(login, password, 0);
                accounts.put(login, player);
                try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("accounts.txt"))) {
                    oos.writeObject(accounts);
                }
            }
        } catch (FileNotFoundException e) {
            try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(Paths.get("accounts.txt")))) {
                oos.writeObject(accounts);
                System.exit(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    static Player readData(Map<String, Player> accounts) throws IOException, ClassNotFoundException {

        return null;
    }

    static void printField(int[][] field) {
        if (field != null) {
            for (int[] ints : field) {
                for (int anInt : ints) {
                    System.out.print(anInt + " ");
                }
                System.out.println();
            }
        }
    }

    static boolean isWin(int[][] field) {
        if (field != null) {
            boolean flag = true;
            for (int[] ints : field) {
                for (int anInt : ints) {
                    if (anInt == 0) {
                        flag = false;
                    }
                }
            }
            if (flag) {
                System.out.println("There is no winner!");
                return true;
            }
            return /* Player 1 */
                    field[0][0] == 1 && field[0][0] == field[1][0] && field[1][0] == field[2][0] ||
                    field[0][1] == 1 && field[0][1] == field[1][1] && field[1][1] == field[2][1] ||
                    field[0][2] == 1 && field[0][2] == field[1][2] && field[1][2] == field[2][2] ||
                    field[0][0] == 1 && field[0][0] == field[0][1] && field[0][1] == field[0][2] ||
                    field[1][0] == 1 && field[1][0] == field[1][1] && field[1][1] == field[1][2] ||
                    field[2][0] == 1 && field[2][0] == field[2][1] && field[2][1] == field[2][2] ||
                    field[0][0] == 1 && field[0][0] == field[1][1] && field[1][1] == field[2][2] ||
                    field[2][0] == 1 && field[2][0] == field[1][1] && field[1][1] == field[0][2] ||
                    /* Player 2 */
                    field[0][0] == 2 && field[0][0] == field[1][0] && field[1][0] == field[2][0] ||
                    field[0][1] == 2 && field[0][1] == field[1][1] && field[1][1] == field[2][1] ||
                    field[0][2] == 2 && field[0][2] == field[1][2] && field[1][2] == field[2][2] ||
                    field[0][0] == 2 && field[0][0] == field[0][1] && field[0][1] == field[0][2] ||
                    field[1][0] == 2 && field[1][0] == field[1][1] && field[1][1] == field[1][2] ||
                    field[2][0] == 2 && field[2][0] == field[2][1] && field[2][1] == field[2][2] ||
                    field[0][0] == 2 && field[0][0] == field[1][1] && field[1][1] == field[2][2] ||
                    field[2][0] == 2 && field[2][0] == field[1][1] && field[1][1] == field[0][2];
        }
        return false;
    }

    static void isWinTest() {
        int[][] field = new int[3][3];
        check(false, isWin(field));
        field = new int[][]{
                new int[]{1, 0, 0},
                new int[]{1, 0, 0},
                new int[]{1, 0, 0}
        };
        check(true, isWin(field));
        field = new int[][]{
                new int[]{0, 1, 0},
                new int[]{0, 1, 0},
                new int[]{0, 1, 0}
        };
        check(true, isWin(field));
        field = new int[][]{
                new int[]{0, 0, 1},
                new int[]{0, 0, 1},
                new int[]{0, 0, 1}
        };
        check(true, isWin(field));
        field = new int[][]{
                new int[]{1, 1, 1},
                new int[]{0, 0, 0},
                new int[]{0, 0, 0}
        };
        check(true, isWin(field));
        field = new int[][]{
                new int[]{0, 0, 0},
                new int[]{1, 1, 1},
                new int[]{0, 0, 0}
        };
        check(true, isWin(field));
        field = new int[][]{
                new int[]{0, 0, 0},
                new int[]{0, 0, 0},
                new int[]{1, 1, 1}
        };
        check(true, isWin(field));
        field = new int[][]{
                new int[]{0, 0, 1},
                new int[]{0, 1, 0},
                new int[]{1, 0, 0}
        };
        check(true, isWin(field));
        field = new int[][]{
                new int[]{1, 0, 0},
                new int[]{0, 1, 0},
                new int[]{0, 0, 1}
        };
        check(true, isWin(field));
        field = new int[][]{
                new int[]{1, 0, 1},
                new int[]{0, 1, 0},
                new int[]{0, 0, 0}
        };
        check(false, isWin(field));
        field = new int[][]{
                new int[]{1, 0, 0},
                new int[]{0, 1, 0},
                new int[]{1, 0, 0}
        };
        check(false, isWin(field));
        System.out.println("All tests passed");
    }

    static void check(Object expected, Object actual) {
        if (!Objects.deepEquals(expected, actual)) {
            System.out.println("[FAIL]");
            throw new AssertionError("Expected " + expected + ", but found " + actual);
        }
    }
}
