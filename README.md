<!--suppress HtmlDeprecatedAttribute -->
<div align="center">


<div>
    <h1>Minestom-Virtual</h1>
    <a href="https://discord.gg/YZ45WMJtuY">SUPPORT DISCORD</a>
</div>
</div>

****

Gradle Dependency

````groovy
implementation 'com.github.aredblock:minestom-virtual:Tag'
````
****

Maven Dependency
````xml
<dependency>
    <groupId>com.github.aredblock</groupId>
    <artifactId>minestom-virtual</artifactId>
    <version>Tag</version>
</dependency>
````

The newest version:

[![](https://jitpack.io/v/aredblock/Minestom-virtual.svg)](https://jitpack.io/#aredblock/Minestom-virtual)

***

Simple Main.class

````java

public class Main{

    public static void main(String[] args){
        MinecraftServer minecraftServer = MinecraftServer.init();
        InstanceManager instanceManager = MinecraftServer.getInstanceManager();

        InstanceContainer defaultInstanceContainer = instanceManager.createInstanceContainer();

        defaultInstanceContainer.setGenerator(unit ->
                unit.modifier().fillHeight(0, 40, Block.GRASS_BLOCK));

        InstanceContainer virtualInstanceContainer = instanceManager.createInstanceContainer();

        virtualInstanceContainer.setGenerator(unit ->
                unit.modifier().fillHeight(0, 40, Block.GOLD_BLOCK));

        
        MinecraftServerInstance minecraftServerInstance = MinecraftServerInstance.builder(UUID.randomUUID(),new DemoEventBuilder());
        minecraftServerInstance.getInstanceHandler().registerInstance(virtualInstanceContainer);
        
        
        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
        globalEventHandler.addListener(AsyncPlayerConfigurationEvent.class, event -> {
            final var player = event.getPlayer();
            
            event.setSpawningInstance(defaultInstanceContainer);
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
                minecraftServerInstance.remove(player);
            } else if (event.getItemStack().material() == Material.APPLE && !minecraftServerInstance.isPlayerOnline(player)) {
                minecraftServerInstance.join(player,defaultInstanceContainer);
            }
        });
        
        minecraftServer.start("0.0.0.0", 25565);
    }

}
````


Simple PrivateEventBuilder.class

````java

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
````

Simple CustomEvents sample

````java

public static void main(String[] args){
    [...]
        
    globalEventHandler.addListener(PlayerJoinedServerInstanceEvent.class,event -> {
        event.getPlayer().sendMessage(event.getPlayer().getUsername() + " joined!");
    });
    globalEventHandler.addListener(PlayerDisconnectServerInstanceEvent.class,event -> {
        event.getPlayer().sendMessage(event.getPlayer().getUsername() + " disconnected!");
    });
    
    [...]
}
````

****


Todo:

- [ ] Add custom JavaPlugin.class
- [ ] Make code clean


> [!NOTE]
> This code is just a first version and not really well written! (I will change this later if possible :])

