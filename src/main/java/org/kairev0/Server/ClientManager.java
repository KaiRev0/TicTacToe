package org.kairev0.Server;

import io.netty.channel.Channel;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ClientManager {

    private final Map<String, Channel> clients = new ConcurrentHashMap<>();

    public void register(String id, Channel channel) {
        clients.put(id, channel);
    }

    public Channel get(String id) {
        return clients.get(id);
    }

    public void remove(Channel channel) {
        clients.values().removeIf(ch -> ch == channel);
    }

    public void connectClients(String firstId, String secondId) {
        Channel first = clients.get(firstId);
        Channel second = clients.get(secondId);

        if (first != null && second != null) {
            System.out.println(firstId + " " + secondId);
        }
    }
}