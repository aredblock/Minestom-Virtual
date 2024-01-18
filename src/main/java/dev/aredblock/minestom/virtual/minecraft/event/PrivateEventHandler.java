package dev.aredblock.minestom.virtual.minecraft.event;

import lombok.AllArgsConstructor;
import net.minestom.server.entity.Player;
import net.minestom.server.event.EventFilter;
import net.minestom.server.event.EventNode;
import net.minestom.server.event.trait.EntityEvent;
import net.minestom.server.event.trait.InstanceEvent;
import net.minestom.server.instance.Instance;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
public final class PrivateEventHandler {
    private final Map<Player, EventNode<EntityEvent>> entityNodes = new HashMap<>();
    private final PrivateEventBuilder builder;
    public void addPlayer(Player player, UUID uuid){
        var node = builder.getEntityEvents(EventNode.type(uuid.toString() + "-entityEvents", EventFilter.ENTITY));
        player.eventNode().addChild(node);
        entityNodes.put(player,node);
    }
    public void removePlayer(Player player){
        var node = entityNodes.get(player);
        player.eventNode().removeChild(node);
        entityNodes.remove(player);
    }

    private final Map<Instance,EventNode<InstanceEvent>> instanceNodes = new HashMap<>();
    public void addInstance(Instance instance, UUID uuid){
        var node = builder.getInstanceEvents(EventNode.type(uuid.toString() + "-instanceEvents", EventFilter.INSTANCE));
        instance.eventNode().addChild(node);
        instanceNodes.put(instance,node);
    }
    public void removeInstance(Instance instance){
        var node = instanceNodes.get(instance);
        instance.eventNode().removeChild(node);
        instanceNodes.remove(instance);
    }
}
