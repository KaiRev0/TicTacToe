package org.kairev0;

import java.io.*;
import java.util.*;

public class App {
    public static void main( String[] args ) throws IOException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        Map<String, Player> accounts = null;
        Player player = null;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("accounts.txt"))) {
            accounts = (Map<String, Player>) ois.readObject();
            System.out.println("Welcome to Game!");
            System.out.print("Please enter login: ");
            String login = scanner.nextLine();
            System.out.print("Please enter password: ");
            String password = scanner.nextLine();
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
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("accounts.txt"))) {
                accounts = new HashMap<>();
                oos.writeObject(accounts);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Player opponent = null;
        if (accounts != null) {
            List<Player> players = new ArrayList<>(accounts.values());
            Collections.shuffle(players);
            opponent = players.get(0);
            if (accounts != null && !accounts.isEmpty()) {
                int tries = 0;
                while (opponent.equals(player) && tries++ < 10) {
                    Collections.shuffle(players);
                    opponent = players.get(0);
                }
                if (opponent.equals(player)) {
                    System.out.println("Tries are exhausted");
                    System.exit(0);
                }
            } else {
                System.exit(0);
            }
        }
        System.out.println("Your opponent is " + opponent.getName());

        Game game = new Game(new Id(1), player, opponent);
        String input;
        int[][] field = null;
        int gameCycle = 0;
        Player currentPlayer = player;
        isWinTest();
        while (!isWin(field)) {
            if (gameCycle%2 == 0) {
                currentPlayer = player;
            } else {
                currentPlayer = opponent;
            }
            System.out.println("Current player is " + currentPlayer.getName());
            System.out.println("Your opponent is " + opponent.getName());
            System.out.println("Field:");
            field = game.getField();
            printField(field);
            System.out.print("Please, enter coordinates: ");
            input = scanner.nextLine();
            String[] coords = input.split(" ");
            int x = Integer.parseInt(coords[0]);
            int y = Integer.parseInt(coords[1]);
            if (currentPlayer.getName().equals(player.getName())) {
                if (x >= 0 && x <= 2 && y >= 0 && y <= 2 && !(field[x][y]==1 || field[x][y]==2)) {
                    field[x][y] = 1;
                } else {
                    System.out.println("Invalid coordinates. Try again.");
                    continue;
                }
            } else {
                currentPlayer = opponent;
                if (x >= 0 && x <= 2 && y >= 0 && y <= 2 && !(field[x][y]==1 || field[x][y]==2)) {
                    field[x][y] = 2;
                } else {
                    System.out.println("Invalid coordinates. Try again.");
                    continue;
                }
                field[x][y] = 2;
            }
            gameCycle++;
        }
        printField(field);
        System.out.printf("%s wins!", currentPlayer.getName());
        scanner.close();
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
    }

    static void check(Object expected, Object actual) {
        if (Objects.deepEquals(expected, actual)) {
            System.out.println("[ACCEPT]");
        } else {
            System.out.println("[REJECT]");
            System.out.println(expected + " != " + actual);
        }
    }
}
