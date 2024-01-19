package dev.aredblock.test;

import dev.aredblock.minestom.virtual.MinecraftServerInstance;
import dev.aredblock.test.builder.DemoEventBuilder;
import net.kyori.adventure.text.Component;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.event.player.PlayerSpawnEvent;
import net.minestom.server.event.player.PlayerUseItemEvent;
import net.minestom.server.instance.*;
import net.minestom.server.instance.block.Block;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;

import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        MinecraftServer minecraftServer = MinecraftServer.init();
        InstanceManager instanceManager = MinecraftServer.getInstanceManager();

        var instanceContainer = instanceManager.createInstanceContainer();
        var instanceContainer2 = instanceManager.createInstanceContainer();

        instanceContainer.setGenerator(unit ->
                unit.modifier().fillHeight(0, 40, Block.GRASS_BLOCK));

        instanceContainer2.setGenerator(unit ->
                unit.modifier().fillHeight(0, 40, Block.GOLD_BLOCK));


        MinecraftServerInstance minecraftServerInstance = MinecraftServerInstance.builder(UUID.randomUUID(),new DemoEventBuilder());
        minecraftServerInstance.getInstanceHandler().registerInstance(instanceContainer2);

        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
        globalEventHandler.addListener(AsyncPlayerConfigurationEvent.class,event -> {
            final Player player = event.getPlayer();
            event.setSpawningInstance(instanceContainer);
            player.setRespawnPoint(new Pos(0, 42, 0));
        });
        globalEventHandler.addListener(PlayerSpawnEvent.class,event -> {
            final var player = event.getPlayer();

            player.getInventory().setItemStack(0, ItemStack.of(Material.APPLE).withDisplayName(Component.text("Join")));
            player.getInventory().setItemStack(1, ItemStack.of(Material.DIAMOND).withDisplayName(Component.text("Disconnect")));
        });
        globalEventHandler.addListener(PlayerUseItemEvent.class,event -> {
            final Player player = event.getPlayer();
            if(event.getItemStack().material() == Material.DIAMOND && minecraftServerInstance.isPlayerOnline(player)){
                minecraftServerInstance.disconnect(player);
            } else if (event.getItemStack().material() == Material.APPLE && !minecraftServerInstance.isPlayerOnline(player)) {
                minecraftServerInstance.join(player,instanceContainer);
            }
        });

        minecraftServer.start("0.0.0.0", 25565);
    }
}