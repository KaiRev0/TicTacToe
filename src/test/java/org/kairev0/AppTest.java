package org.kairev0;

import org.kairev0.Services.GameServiceOffline;

import java.util.Objects;

public class AppTest {
    public static void isWinTest() {
        /* --- EMPTY --- */
        int[][] field = new int[3][3];
        check(false, GameServiceOffline.isWin(field, 1));

        /* --- WIN TEAM 1 --- */
        field = new int[][]{
                new int[]{1, 0, 0},
                new int[]{1, 0, 0},
                new int[]{1, 0, 0}
        };
        check(true, GameServiceOffline.isWin(field, 1));
        field = new int[][]{
                new int[]{0, 1, 0},
                new int[]{0, 1, 0},
                new int[]{0, 1, 0}
        };
        check(true, GameServiceOffline.isWin(field, 1));
        field = new int[][]{
                new int[]{0, 0, 1},
                new int[]{0, 0, 1},
                new int[]{0, 0, 1}
        };
        check(true, GameServiceOffline.isWin(field, 1));
        field = new int[][]{
                new int[]{1, 1, 1},
                new int[]{0, 0, 0},
                new int[]{0, 0, 0}
        };
        check(true, GameServiceOffline.isWin(field, 1));
        field = new int[][]{
                new int[]{0, 0, 0},
                new int[]{1, 1, 1},
                new int[]{0, 0, 0}
        };
        check(true, GameServiceOffline.isWin(field, 1));
        field = new int[][]{
                new int[]{0, 0, 0},
                new int[]{0, 0, 0},
                new int[]{1, 1, 1}
        };
        check(true, GameServiceOffline.isWin(field, 1));
        field = new int[][]{
                new int[]{0, 0, 1},
                new int[]{0, 1, 0},
                new int[]{1, 0, 0}
        };
        check(true, GameServiceOffline.isWin(field, 1));
        field = new int[][]{
                new int[]{1, 0, 0},
                new int[]{0, 1, 0},
                new int[]{0, 0, 1}
        };
        check(true, GameServiceOffline.isWin(field, 1));
        field = new int[][]{
                new int[]{1, 0, 1},
                new int[]{0, 1, 0},
                new int[]{2, 2, 2}
        };
        check(false, GameServiceOffline.isWin(field, 1));
        field = new int[][]{
                new int[]{1, 0, 2},
                new int[]{0, 1, 2},
                new int[]{1, 0, 2}
        };
        check(false, GameServiceOffline.isWin(field, 1));

        /* --- FAIL TEAM 1 --- */
        field = new int[][]{
                new int[]{2, 0, 0},
                new int[]{2, 0, 0},
                new int[]{2, 0, 0}
        };
        check(false, GameServiceOffline.isWin(field, 1));
        field = new int[][]{
                new int[]{0, 2, 0},
                new int[]{0, 2, 0},
                new int[]{0, 2, 0}
        };
        check(false, GameServiceOffline.isWin(field, 1));
        field = new int[][]{
                new int[]{0, 0, 2},
                new int[]{0, 0, 2},
                new int[]{0, 0, 2}
        };
        check(false, GameServiceOffline.isWin(field, 1));
        field = new int[][]{
                new int[]{2, 2, 2},
                new int[]{0, 0, 0},
                new int[]{0, 0, 0}
        };
        check(false, GameServiceOffline.isWin(field, 1));
        field = new int[][]{
                new int[]{0, 0, 0},
                new int[]{2, 2, 2},
                new int[]{0, 0, 0}
        };
        check(false, GameServiceOffline.isWin(field, 1));
        field = new int[][]{
                new int[]{0, 0, 0},
                new int[]{0, 0, 0},
                new int[]{2, 2, 2}
        };
        check(false, GameServiceOffline.isWin(field, 1));
        field = new int[][]{
                new int[]{0, 0, 2},
                new int[]{0, 2, 0},
                new int[]{2, 0, 0}
        };
        check(false, GameServiceOffline.isWin(field, 1));
        field = new int[][]{
                new int[]{2, 0, 0},
                new int[]{0, 2, 0},
                new int[]{0, 0, 2}
        };
        check(false, GameServiceOffline.isWin(field, 1));
        field = new int[][]{
                new int[]{2, 0, 2},
                new int[]{0, 2, 0},
                new int[]{1, 1, 1}
        };
        check(true, GameServiceOffline.isWin(field, 1));
        field = new int[][]{
                new int[]{2, 0, 1},
                new int[]{0, 2, 1},
                new int[]{2, 0, 1}
        };
        check(true, GameServiceOffline.isWin(field, 1));

        /* --- FAIL TEAM 2 --- */
        field = new int[][]{
                new int[]{1, 0, 0},
                new int[]{1, 0, 0},
                new int[]{1, 0, 0}
        };
        check(false, GameServiceOffline.isWin(field, 2));
        field = new int[][]{
                new int[]{0, 1, 0},
                new int[]{0, 1, 0},
                new int[]{0, 1, 0}
        };
        check(false, GameServiceOffline.isWin(field, 2));
        field = new int[][]{
                new int[]{0, 0, 1},
                new int[]{0, 0, 1},
                new int[]{0, 0, 1}
        };
        check(false, GameServiceOffline.isWin(field, 2));
        field = new int[][]{
                new int[]{1, 1, 1},
                new int[]{0, 0, 0},
                new int[]{0, 0, 0}
        };
        check(false, GameServiceOffline.isWin(field, 2));
        field = new int[][]{
                new int[]{0, 0, 0},
                new int[]{1, 1, 1},
                new int[]{0, 0, 0}
        };
        check(false, GameServiceOffline.isWin(field, 2));
        field = new int[][]{
                new int[]{0, 0, 0},
                new int[]{0, 0, 0},
                new int[]{1, 1, 1}
        };
        check(false, GameServiceOffline.isWin(field, 2));
        field = new int[][]{
                new int[]{0, 0, 1},
                new int[]{0, 1, 0},
                new int[]{1, 0, 0}
        };
        check(false, GameServiceOffline.isWin(field, 2));
        field = new int[][]{
                new int[]{1, 0, 0},
                new int[]{0, 1, 0},
                new int[]{0, 0, 1}
        };
        check(false, GameServiceOffline.isWin(field, 2));
        field = new int[][]{
                new int[]{1, 0, 1},
                new int[]{0, 1, 0},
                new int[]{2, 2, 2}
        };
        check(true, GameServiceOffline.isWin(field, 2));
        field = new int[][]{
                new int[]{1, 0, 2},
                new int[]{0, 1, 2},
                new int[]{1, 0, 2}
        };
        check(true, GameServiceOffline.isWin(field, 2));

        /* --- WIN TEAM 2 --- */
        field = new int[][]{
                new int[]{2, 0, 0},
                new int[]{2, 0, 0},
                new int[]{2, 0, 0}
        };
        check(true, GameServiceOffline.isWin(field, 2));
        field = new int[][]{
                new int[]{0, 2, 0},
                new int[]{0, 2, 0},
                new int[]{0, 2, 0}
        };
        check(true, GameServiceOffline.isWin(field, 2));
        field = new int[][]{
                new int[]{0, 0, 2},
                new int[]{0, 0, 2},
                new int[]{0, 0, 2}
        };
        check(true, GameServiceOffline.isWin(field, 2));
        field = new int[][]{
                new int[]{2, 2, 2},
                new int[]{0, 0, 0},
                new int[]{0, 0, 0}
        };
        check(true, GameServiceOffline.isWin(field, 2));
        field = new int[][]{
                new int[]{0, 0, 0},
                new int[]{2, 2, 2},
                new int[]{0, 0, 0}
        };
        check(true, GameServiceOffline.isWin(field, 2));
        field = new int[][]{
                new int[]{0, 0, 0},
                new int[]{0, 0, 0},
                new int[]{2, 2, 2}
        };
        check(true, GameServiceOffline.isWin(field, 2));
        field = new int[][]{
                new int[]{0, 0, 2},
                new int[]{0, 2, 0},
                new int[]{2, 0, 0}
        };
        check(true, GameServiceOffline.isWin(field, 2));
        field = new int[][]{
                new int[]{2, 0, 0},
                new int[]{0, 2, 0},
                new int[]{0, 0, 2}
        };
        check(true, GameServiceOffline.isWin(field, 2));
        field = new int[][]{
                new int[]{2, 0, 2},
                new int[]{0, 2, 0},
                new int[]{1, 1, 1}
        };
        check(false, GameServiceOffline.isWin(field, 2));
        field = new int[][]{
                new int[]{2, 0, 1},
                new int[]{0, 2, 1},
                new int[]{2, 0, 1}
        };
        check(false, GameServiceOffline.isWin(field, 2));

        System.out.println("All tests passed");
    }

    private static void check(Object expected, Object actual) {
        if (!Objects.deepEquals(expected, actual)) {
            System.out.println("[FAIL]");
            throw new AssertionError("Expected " + expected + ", but found " + actual);
        }
    }
}
