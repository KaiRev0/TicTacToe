package org.kairev0;

import org.kairev0.Client.ClientMain;
import org.kairev0.Server.ServerMain;
import org.kairev0.Utils.Utils;

import java.io.*;
import java.util.*;

/*
TODO:
4. Настроить обмен между сокетами [НЕ РЕШЁН]
5. Проверить классы и инкапсуляцию [НЕ РЕШЁН]
6. Сделать код более читаемым [ЧАСТИЧНО РЕШЁН]
7. Проверить работу сервер, клиент-клиент[РЕШЁН]
8. Исправить обнаруженные ошибки [ЧАСТИЧНО РЕШЁН]
9. Написать Readme [РЕШЁН]
 */

public class App {
    public static void main( String[] args ) throws IOException {
        ServerMain serverMain = new ServerMain();
        ClientMain clientMain = new ClientMain(serverMain);
        serverMain.run();
        clientMain.run();

        Utils.scanner.close();
    }
}
