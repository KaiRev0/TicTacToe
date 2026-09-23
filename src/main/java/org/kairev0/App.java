package org.kairev0;

import org.kairev0.Authorization.AuthService;
import org.kairev0.Authorization.Player;

import java.io.*;
import java.util.*;

/*
ERRORS:
1. Нарушен игровой цикл (нет трёх раундов, вероятно, проблема в isWin) [РЕШЕНО]
2. Неправильно определяется игрок и его оппонент (currentPlayer и currentOpponent) [РЕШЕНО]
3. Нарушена авторизация, нужно поправить сравнение по паролю [РЕШЕНО]
4. Нужно проверить систему начисления очков и игровые циклы [РЕШЕНО]
 */

/*
1. Сыграть одну успешную одноразовую партию [РЕШЕНО]
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
        // Приветствуем пользователя
        System.out.println("Welcome to Game!");
        // 0.1. Ввод логина
        System.out.print("Please enter login: ");
        String login = scanner.nextLine();
        // 0.2. Ввод пароля
        System.out.print("Please enter password: ");
        String password = scanner.nextLine();
        // Авторизация
        Player player = AuthService.authorization(login, password);
        System.out.println(player);
        // Получение всех пользователей
        Map<String, Player> players = AuthService.getAllPlayers();
        System.out.println(players);
        // Сохранение пользователя
        if (player == null) {
            AuthService.save(new Player(login, password, 0));
        }
        /* --- authorization window --- */
        System.exit(0);

        /* --- opponent selection --- */
        Player opponent = opponentRandomizer(players, player);
        /* --- opponent selection --- */

        /* --- Game creator --- */
        System.out.println("Press enter for start game.");
        scanner.nextLine();
        Game game = new Game(new Id(Math.abs(rand.nextInt())), player, opponent);
        Player currentPlayer = player;
        /* --- Game creator --- */

        /* --- game cycle --- */
        System.out.println("You is " + currentPlayer.getName());
        System.out.println("Your opponent is " + opponent.getName());
        GameRoom(game, player, opponent);
        /* --- game cycle --- */

        /* --- result --- */
        /*printField(field);
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
        System.out.println(playerRoundWins + "/" + opponentRoundWins);*/
        scanner.close();
        /* --- result --- */
    }

    // 1. Соединить двух игроков вместе
    // 2. Случайным образом определить первого игрока
    // 3. Сыграть один раунд
    // 4. Определить победителя раунда
    // 5. Поменять первого игрока со вторым местами
    // 6. Провести второй раунд

    static void GameRoom(Game game, Player you, Player opponent) {
        List<Player> players = new ArrayList<>(Arrays.asList(you, opponent));
        Collections.shuffle(players);
        Player first = players.get(0);
        Player second = players.get(1);
        first.setTeam(1);
        second.setTeam(2);
        int firstPlayerWinsOfRounds = 0, secondPlayerWinsOfRounds = 0;
        int[][] field = game.getField();
        int winnerOfRound = gameCycle(field, first, second);
        if (winnerOfRound == 1) {
            System.out.printf("%s wins!\n", first.getName());
            firstPlayerWinsOfRounds++;
        } else {
            System.out.printf("%s wins!\n", second.getName());
            secondPlayerWinsOfRounds++;
        }
        System.out.println(firstPlayerWinsOfRounds + "/" + secondPlayerWinsOfRounds);
        game.reloadField();
        field = game.getField();
        winnerOfRound = gameCycle(field, second, first);
        if (winnerOfRound == 1) {
            System.out.printf("%s wins!\n", first.getName());
            firstPlayerWinsOfRounds++;
        } else {
            System.out.printf("%s wins!\n", second.getName());
            secondPlayerWinsOfRounds++;
        }
        System.out.println(firstPlayerWinsOfRounds + "/" + secondPlayerWinsOfRounds);
        if (firstPlayerWinsOfRounds == secondPlayerWinsOfRounds) {
            game.reloadField();
            field = game.getField();
            winnerOfRound = gameCycle(field, first, second);
            if (winnerOfRound == 1) {
                System.out.printf("%s wins!\n", first.getName());
                firstPlayerWinsOfRounds++;
            } else {
                System.out.printf("%s wins!\n", second.getName());
                secondPlayerWinsOfRounds++;
            }
            System.out.println(firstPlayerWinsOfRounds + "/" + secondPlayerWinsOfRounds);
        }
        if (firstPlayerWinsOfRounds > secondPlayerWinsOfRounds) {
            if (first.equals(you)) {
                System.out.println("Congratulations! You win!");
                you.win();
                opponent.fail();
            } else {
                System.out.println("Congratulations! You lose!");
                you.fail();
                opponent.win();
            }
        } else {
            if (second.equals(you)) {
                System.out.println("Congratulations! You win!");
                you.win();
                opponent.fail();
            } else {
                System.out.println("Congratulations! You lose!");
                you.fail();
                opponent.win();
            }
        }
    }

    static int gameCycle(int[][] field, Player first, Player second) {
        Player currentPlayer = first;
        boolean firstPlayerStatus= false;
        boolean secondPlayerStatus = false;
        while (true) {
            System.out.println("Current player is " + currentPlayer.getName());
            printField(field);
            String[] coords = scanner.nextLine().split(" ");
            int x = Integer.parseInt(coords[0]);
            int y = Integer.parseInt(coords[1]);
            if (x < 0 || x > 2 || y < 0 || y > 2 || field[x][y]==1 || field[x][y]==2) {
                System.out.println("Invalid coordinates. Try again.");
                continue;
            }
            if (currentPlayer.equals(first)) {
                field[x][y] = first.getTeam();
                currentPlayer = second;
                firstPlayerStatus = isWin(field, first.getTeam());
            } else {
                field[x][y] = second.getTeam();
                currentPlayer = first;
                secondPlayerStatus = isWin(field, second.getTeam());
            }
            if (firstPlayerStatus) {
                return first.getTeam();
            }
            if (secondPlayerStatus) {
                return second.getTeam();
            }
        }
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

    static boolean isWin(int[][] field, int team) {
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
            if (team == 1) return /* Player 1 */
                    field[0][0] == 1 && field[0][0] == field[1][0] && field[1][0] == field[2][0] ||
                    field[0][1] == 1 && field[0][1] == field[1][1] && field[1][1] == field[2][1] ||
                    field[0][2] == 1 && field[0][2] == field[1][2] && field[1][2] == field[2][2] ||
                    field[0][0] == 1 && field[0][0] == field[0][1] && field[0][1] == field[0][2] ||
                    field[1][0] == 1 && field[1][0] == field[1][1] && field[1][1] == field[1][2] ||
                    field[2][0] == 1 && field[2][0] == field[2][1] && field[2][1] == field[2][2] ||
                    field[0][0] == 1 && field[0][0] == field[1][1] && field[1][1] == field[2][2] ||
                    field[2][0] == 1 && field[2][0] == field[1][1] && field[1][1] == field[0][2];
                    /* Player 2 */
            if (team == 2) return
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
        /* --- EMPTY --- */
        int[][] field = new int[3][3];
        check(false, isWin(field, 1));

        /* --- WIN TEAM 1 --- */
        field = new int[][]{
                new int[]{1, 0, 0},
                new int[]{1, 0, 0},
                new int[]{1, 0, 0}
        };
        check(true, isWin(field, 1));
        field = new int[][]{
                new int[]{0, 1, 0},
                new int[]{0, 1, 0},
                new int[]{0, 1, 0}
        };
        check(true, isWin(field, 1));
        field = new int[][]{
                new int[]{0, 0, 1},
                new int[]{0, 0, 1},
                new int[]{0, 0, 1}
        };
        check(true, isWin(field, 1));
        field = new int[][]{
                new int[]{1, 1, 1},
                new int[]{0, 0, 0},
                new int[]{0, 0, 0}
        };
        check(true, isWin(field, 1));
        field = new int[][]{
                new int[]{0, 0, 0},
                new int[]{1, 1, 1},
                new int[]{0, 0, 0}
        };
        check(true, isWin(field, 1));
        field = new int[][]{
                new int[]{0, 0, 0},
                new int[]{0, 0, 0},
                new int[]{1, 1, 1}
        };
        check(true, isWin(field, 1));
        field = new int[][]{
                new int[]{0, 0, 1},
                new int[]{0, 1, 0},
                new int[]{1, 0, 0}
        };
        check(true, isWin(field, 1));
        field = new int[][]{
                new int[]{1, 0, 0},
                new int[]{0, 1, 0},
                new int[]{0, 0, 1}
        };
        check(true, isWin(field, 1));
        field = new int[][]{
                new int[]{1, 0, 1},
                new int[]{0, 1, 0},
                new int[]{2, 2, 2}
        };
        check(false, isWin(field, 1));
        field = new int[][]{
                new int[]{1, 0, 2},
                new int[]{0, 1, 2},
                new int[]{1, 0, 2}
        };
        check(false, isWin(field, 1));

        /* --- FAIL TEAM 1 --- */
        field = new int[][]{
                new int[]{2, 0, 0},
                new int[]{2, 0, 0},
                new int[]{2, 0, 0}
        };
        check(false, isWin(field, 1));
        field = new int[][]{
                new int[]{0, 2, 0},
                new int[]{0, 2, 0},
                new int[]{0, 2, 0}
        };
        check(false, isWin(field, 1));
        field = new int[][]{
                new int[]{0, 0, 2},
                new int[]{0, 0, 2},
                new int[]{0, 0, 2}
        };
        check(false, isWin(field, 1));
        field = new int[][]{
                new int[]{2, 2, 2},
                new int[]{0, 0, 0},
                new int[]{0, 0, 0}
        };
        check(false, isWin(field, 1));
        field = new int[][]{
                new int[]{0, 0, 0},
                new int[]{2, 2, 2},
                new int[]{0, 0, 0}
        };
        check(false, isWin(field, 1));
        field = new int[][]{
                new int[]{0, 0, 0},
                new int[]{0, 0, 0},
                new int[]{2, 2, 2}
        };
        check(false, isWin(field, 1));
        field = new int[][]{
                new int[]{0, 0, 2},
                new int[]{0, 2, 0},
                new int[]{2, 0, 0}
        };
        check(false, isWin(field, 1));
        field = new int[][]{
                new int[]{2, 0, 0},
                new int[]{0, 2, 0},
                new int[]{0, 0, 2}
        };
        check(false, isWin(field, 1));
        field = new int[][]{
                new int[]{2, 0, 2},
                new int[]{0, 2, 0},
                new int[]{1, 1, 1}
        };
        check(true, isWin(field, 1));
        field = new int[][]{
                new int[]{2, 0, 1},
                new int[]{0, 2, 1},
                new int[]{2, 0, 1}
        };
        check(true, isWin(field, 1));

        /* --- FAIL TEAM 2 --- */
        field = new int[][]{
                new int[]{1, 0, 0},
                new int[]{1, 0, 0},
                new int[]{1, 0, 0}
        };
        check(false, isWin(field, 2));
        field = new int[][]{
                new int[]{0, 1, 0},
                new int[]{0, 1, 0},
                new int[]{0, 1, 0}
        };
        check(false, isWin(field, 2));
        field = new int[][]{
                new int[]{0, 0, 1},
                new int[]{0, 0, 1},
                new int[]{0, 0, 1}
        };
        check(false, isWin(field, 2));
        field = new int[][]{
                new int[]{1, 1, 1},
                new int[]{0, 0, 0},
                new int[]{0, 0, 0}
        };
        check(false, isWin(field, 2));
        field = new int[][]{
                new int[]{0, 0, 0},
                new int[]{1, 1, 1},
                new int[]{0, 0, 0}
        };
        check(false, isWin(field, 2));
        field = new int[][]{
                new int[]{0, 0, 0},
                new int[]{0, 0, 0},
                new int[]{1, 1, 1}
        };
        check(false, isWin(field, 2));
        field = new int[][]{
                new int[]{0, 0, 1},
                new int[]{0, 1, 0},
                new int[]{1, 0, 0}
        };
        check(false, isWin(field, 2));
        field = new int[][]{
                new int[]{1, 0, 0},
                new int[]{0, 1, 0},
                new int[]{0, 0, 1}
        };
        check(false, isWin(field, 2));
        field = new int[][]{
                new int[]{1, 0, 1},
                new int[]{0, 1, 0},
                new int[]{2, 2, 2}
        };
        check(true, isWin(field, 2));
        field = new int[][]{
                new int[]{1, 0, 2},
                new int[]{0, 1, 2},
                new int[]{1, 0, 2}
        };
        check(true, isWin(field, 2));

        /* --- WIN TEAM 2 --- */
        field = new int[][]{
                new int[]{2, 0, 0},
                new int[]{2, 0, 0},
                new int[]{2, 0, 0}
        };
        check(true, isWin(field, 2));
        field = new int[][]{
                new int[]{0, 2, 0},
                new int[]{0, 2, 0},
                new int[]{0, 2, 0}
        };
        check(true, isWin(field, 2));
        field = new int[][]{
                new int[]{0, 0, 2},
                new int[]{0, 0, 2},
                new int[]{0, 0, 2}
        };
        check(true, isWin(field, 2));
        field = new int[][]{
                new int[]{2, 2, 2},
                new int[]{0, 0, 0},
                new int[]{0, 0, 0}
        };
        check(true, isWin(field, 2));
        field = new int[][]{
                new int[]{0, 0, 0},
                new int[]{2, 2, 2},
                new int[]{0, 0, 0}
        };
        check(true, isWin(field, 2));
        field = new int[][]{
                new int[]{0, 0, 0},
                new int[]{0, 0, 0},
                new int[]{2, 2, 2}
        };
        check(true, isWin(field, 2));
        field = new int[][]{
                new int[]{0, 0, 2},
                new int[]{0, 2, 0},
                new int[]{2, 0, 0}
        };
        check(true, isWin(field, 2));
        field = new int[][]{
                new int[]{2, 0, 0},
                new int[]{0, 2, 0},
                new int[]{0, 0, 2}
        };
        check(true, isWin(field, 2));
        field = new int[][]{
                new int[]{2, 0, 2},
                new int[]{0, 2, 0},
                new int[]{1, 1, 1}
        };
        check(false, isWin(field, 2));
        field = new int[][]{
                new int[]{2, 0, 1},
                new int[]{0, 2, 1},
                new int[]{2, 0, 1}
        };
        check(false, isWin(field, 2));

        System.out.println("All tests passed");
    }

    static void check(Object expected, Object actual) {
        if (!Objects.deepEquals(expected, actual)) {
            System.out.println("[FAIL]");
            throw new AssertionError("Expected " + expected + ", but found " + actual);
        }
    }
}
