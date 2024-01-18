package dev.aredblock.minestom.virtual.minecraft.event;

import net.minestom.server.event.EventNode;
import net.minestom.server.event.trait.EntityEvent;
import net.minestom.server.event.trait.InstanceEvent;
import org.jetbrains.annotations.NotNull;

public abstract class PrivateEventBuilder {
    public abstract @NotNull EventNode<EntityEvent> getEntityEvents(EventNode<EntityEvent> node);
    public abstract @NotNull EventNode<InstanceEvent> getInstanceEvents(EventNode<InstanceEvent> node);
}
