package org.kairev0;

import org.kairev0.Client.ClientMain;
import org.kairev0.Server.ServerMain;
import org.kairev0.Services.DataService;
import org.kairev0.Models.Player;

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

        ServerMain serverMain = new ServerMain();
        ClientMain clientMain = new ClientMain(serverMain);
        serverMain.run();
        clientMain.run();

        /*
         --- game cycle ---
        System.out.println("You is " + currentPlayer.getName());
        System.out.println("Your opponent is " + opponent.getName());
        gameRoom(game, player, opponent);
         --- game cycle ---

         --- result ---
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
        System.out.println(playerRoundWins + "/" + opponentRoundWins)
        scanner.close();
        --- result ---
        */
    }

    static Player readData(Map<String, Player> accounts) throws IOException, ClassNotFoundException {

        return null;
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
