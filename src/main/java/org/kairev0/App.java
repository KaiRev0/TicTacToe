package org.kairev0;

import org.kairev0.Client.ClientMain;
import org.kairev0.Server.ServerMain;
import org.kairev0.Utils.Utils;

import java.io.*;
import java.util.*;

/*
TODO:
4. Настроить обмен между сокетами
5. Проверить классы и инкапсуляцию
6. Сделать код более читаемым
7. Проверить работу сервер, клиент-клиент
8. Исправить обнаруженные ошибки
9. Написать Readme
 */

public class App {
    public static void main( String[] args ) throws IOException, ClassNotFoundException {
        ServerMain serverMain = new ServerMain();
        ClientMain clientMain = new ClientMain(serverMain);
        serverMain.run();
        clientMain.run();

        Utils.scanner.close();
    }
}
