package dev.aredblock.minestom.virtual.events;

import dev.aredblock.minestom.virtual.MinecraftServerInstance;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minestom.server.entity.Player;
import net.minestom.server.event.trait.PlayerEvent;

@AllArgsConstructor
@Getter
public class PlayerJoinedServerInstanceEvent implements PlayerEvent {

    private final Player player;
    private final MinecraftServerInstance serverInstance;
}
