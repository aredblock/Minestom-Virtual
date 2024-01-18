package dev.aredblock.test.builder;

import dev.aredblock.minestom.virtual.minecraft.event.PrivateEventBuilder;
import net.minestom.server.event.EventNode;
import net.minestom.server.event.player.PlayerMoveEvent;
import net.minestom.server.event.trait.EntityEvent;
import net.minestom.server.event.trait.InstanceEvent;
import org.jetbrains.annotations.NotNull;

public class DemoEventBuilder extends PrivateEventBuilder {

    @Override
    public @NotNull EventNode<EntityEvent> getEntityEvents(EventNode<EntityEvent> node) {
        node.addListener(PlayerMoveEvent.class,event -> {
            event.getPlayer().sendMessage("Moved!");
        });
        return node;
    }

    @Override
    public @NotNull EventNode<InstanceEvent> getInstanceEvents(EventNode<InstanceEvent> node) {
        return node;
    }

}
