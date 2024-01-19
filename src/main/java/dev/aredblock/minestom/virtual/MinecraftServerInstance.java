package dev.aredblock.minestom.virtual;

import dev.aredblock.minestom.virtual.events.PlayerDisconnectServerInstanceEvent;
import dev.aredblock.minestom.virtual.events.PlayerJoinedServerInstanceEvent;
import dev.aredblock.minestom.virtual.minecraft.InstanceHandler;
import dev.aredblock.minestom.virtual.minecraft.event.PrivateEventBuilder;
import dev.aredblock.minestom.virtual.minecraft.event.PrivateEventHandler;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.Instance;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class MinecraftServerInstance {
    @Getter
    private final UUID uuid;
    @Getter
    private final List<Player> onlinePlayers = new ArrayList<>();
    @Getter
    private final InstanceHandler instanceHandler = new InstanceHandler(this);
    @Getter
    private final PrivateEventHandler privateEventHandler;
    private final HashMap<Player,Instance> fallbackInstances = new HashMap<>();

    public static @NotNull MinecraftServerInstance builder(@NotNull UUID uuid,@NotNull PrivateEventBuilder builder){
        return new MinecraftServerInstance(uuid,new PrivateEventHandler(builder));
    }

    public void join(@NotNull Player player, @NotNull Instance fallbackInstance){
        onlinePlayers.add(player);

        if(!player.getInstance().equals(instanceHandler.getInstances().get(0)))player.setInstance(instanceHandler.getInstances().get(0));

        privateEventHandler.addPlayer(player,uuid);

        fallbackInstances.put(player,fallbackInstance);

        MinecraftServer.getGlobalEventHandler().call(new PlayerJoinedServerInstanceEvent(player,this));
        Gate.addPlayer(player,this);
    }

    public void disconnect(@NotNull Player player){
        onlinePlayers.remove(player);

        privateEventHandler.removePlayer(player);

        player.setInstance(fallbackInstances.get(player));
        fallbackInstances.remove(player);

        MinecraftServer.getGlobalEventHandler().call(new PlayerDisconnectServerInstanceEvent(player,this));
        Gate.removePlayer(player);
    }

    public boolean isPlayerOnline(@NotNull Player player){
        return onlinePlayers.contains(player);
    }
}
