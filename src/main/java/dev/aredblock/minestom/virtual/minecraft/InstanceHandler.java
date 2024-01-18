package dev.aredblock.minestom.virtual.minecraft;

import dev.aredblock.minestom.virtual.MinecraftServerInstance;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minestom.server.instance.Instance;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public final class InstanceHandler {
    @Getter
    private final List<Instance> instances = new ArrayList<>();
    private final MinecraftServerInstance serverInstance;
    public void registerInstance(@NotNull Instance instance){
        serverInstance.getPrivateEventHandler().addInstance(instance,serverInstance.getUuid());
        instances.add(instance);
    }

    public void unregisterInstance(@NotNull Instance instance){
        serverInstance.getPrivateEventHandler().removeInstance(instance);
        instances.remove(instance);
    }

    public @Nullable Instance getInstance(@NotNull UUID uuid) {
        Optional<Instance> instance = this.getInstances().stream().filter((someInstance) -> {
            return someInstance.getUniqueId().equals(uuid);
        }).findFirst();
        return (Instance)instance.orElse((Instance) null);
    }
}
