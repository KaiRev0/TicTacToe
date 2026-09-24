package org.kairev0.Services;

import org.kairev0.Game;
import org.kairev0.Id;
import org.kairev0.Models.Player;
import org.kairev0.Utils.Utils;

import java.util.*;

import static org.kairev0.Utils.Utils.scanner;

public class GameService {
    private Random rand = new Random();
    private Game game;
    private List<Player> round;

    public GameService(Player thisPlayer, Player opponentPlayer) {
        this.game = new Game(new Id(Math.abs(rand.nextInt())), thisPlayer, opponentPlayer);
        round = new ArrayList<>(Arrays.asList(thisPlayer, opponentPlayer));
        Collections.shuffle(round);
    }

    // 1. Соединить двух игроков вместе
    // 2. Случайным образом определить первого игрока
    // 3. Сыграть один раунд
    // 4. Определить победителя раунда
    // 5. Поменять первого игрока со вторым местами
    // 6. Провести второй раунд

    public Player startGame() {
        Player first = round.get(0);
        Player second = round.get(1);
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
            /*round.set(0, first);
            round.set(1, second);
            Collections.shuffle(round);*/
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
            return first;
        } else {
            return second;
        }
    }

    private int gameCycle(int[][] field, Player first, Player second) {
        Player currentPlayer = first;
        boolean firstPlayerStatus = false;
        boolean secondPlayerStatus = false;
        while (true) {
            System.out.println("Current player is " + currentPlayer.getName());
            Utils.printField(field);
            String[] coords = scanner.nextLine().split(" ");
            int x = Integer.parseInt(coords[0]);
            int y = Integer.parseInt(coords[1]);
            if (x < 0 || x > 2 || y < 0 || y > 2 || field[x][y] == 1 || field[x][y] == 2) {
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

    private boolean isWin(int[][] field, int team) {
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

    private void check(Object expected, Object actual) {
        if (!Objects.deepEquals(expected, actual)) {
            System.out.println("[FAIL]");
            throw new AssertionError("Expected " + expected + ", but found " + actual);
        }
    }

    public void isWinTest() {
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
}
