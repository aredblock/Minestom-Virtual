package dev.aredblock.minestom.virtual;

import net.minestom.server.entity.Player;

import java.util.HashMap;
import java.util.Map;

public final class Gate {
    private static final Map<Player, MinecraftServerInstance> players = new HashMap<>();

    public static void disconnectFromAll(Player player){
        players.get(player).disconnect(player);
        players.remove(player);
    }
    public static void move(Player player, MinecraftServerInstance instance){
        disconnectFromAll(player);
        instance.join(player,player.getInstance());
    }

    protected static void addPlayer(Player player,MinecraftServerInstance instance){
        players.put(player,instance);
    }
    protected static void removePlayer(Player player){
        players.remove(player);
    }
}
