package org.kairev0;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class App {
    public static void main( String[] args ) {
        Scanner scanner = new Scanner(System.in);
        Map<String, String> accounts = new HashMap<>();

        System.out.println("Welcome to Game!");
        System.out.println("Please enter login: ");
        String name = scanner.nextLine();
        System.out.println("Please enter password: ");
        String password = scanner.nextLine();
        if (accounts.containsKey(name)) {
            System.out.println("Auth");
            System.out.println("Account:");
            System.out.println("Login: " + name);
            System.out.println("Score" + );
        } else {
            System.out.println("Registration");
            Player player = new Player(name, 0);
            accounts.put(name, password);
        }
        System.exit(0);

        Player player1 = new Player(new Id(1), "Player 1", 100);
        Player player2 = new Player(new Id(2), "Player 2", 100);
        Game game = new Game(new Id(1), player1, player2);
        String input;
        int[][] field = null;
        int gameCycle = 0;
        Player currentPlayer = player1;
        isWinTest();
        while (!isWin(field)) {
            if (gameCycle%2 == 0) {
                currentPlayer = player1;
            } else {
                currentPlayer = player2;
            }
            System.out.println("Current player is " + currentPlayer.getName());
            System.out.println("Field:");
            field = game.getField();
            printField(field);
            System.out.print("Please, enter coordinates: ");
            input = scanner.nextLine();
            String[] coords = input.split(" ");
            int x = Integer.parseInt(coords[0]);
            int y = Integer.parseInt(coords[1]);
            if (currentPlayer.getName().equals(player1.getName())) {
                if (x >= 0 && x <= 2 && y >= 0 && y <= 2 && !(field[x][y]==1 || field[x][y]==2)) {
                    field[x][y] = 1;
                } else {
                    System.out.println("Invalid coordinates. Try again.");
                    continue;
                }
            } else {
                currentPlayer = player2;
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
